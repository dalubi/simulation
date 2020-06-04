package com.ices.simulation.service.parseXML;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import com.ices.simulation.cyf.pathVariable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class myParse {
    public static Document document;
    //整个的开始节点 唯一
    public static GraphNode startEventNode;
    public static int poolNumber;
    //所有节点信息
    public static Map<String,GraphNode> allNodes=new HashMap<>();
    //每个联邦信息
    public static Map<String,PoolNode> allPools=new HashMap<>();
    //所有启动节点
    public static Map<String,GraphNode> startNodesMap=new HashMap<>();
    //名称和pool
    public static Map<String,String> poolNameToFederateName=new HashMap<>();
    //sequenceFlow
    public static Map<String,String> targetToSource=new HashMap<>();
    public static Map<String,String> sourceToTarget=new HashMap<>();
    //target+source 是否使用
    public static Map<String,Boolean> sequenceFlowUse=new HashMap<>();

    @Autowired
    pathVariable pathVariable;

    public static myParse myparse;

    @PostConstruct
    public void init() {
        myparse = this;
        myparse.pathVariable = this.pathVariable;
    }

    public static void parse(String path) throws DocumentException, IOException {

        readXML(new File(path));
        //构建所有节点
        constructAllPoolNodes();
        //修改endEvent位置错误的bug
        modifyEndEvent();
        //构建完整的有向图
        constructAllGraph();
        //将所有访问标记置false
        changeVisited();
        //构建每个pool的孤立节点list
        BFS(startEventNode);
        findUnVisitedNodes();
        //将所有访问标记置false
        changeVisited();
        //BFS找到每个pool的首个节点
        for(Map.Entry<String, PoolNode> pool : allPools.entrySet()){
            String poolName = pool.getValue().getProcessId();
            searchBFS(startEventNode,poolName);
        }
        //将所有访问标记置false
        changeVisited();

        //构建状态图
        constructStateGraph();
        changeVisited();
//        GraphNode graphNode = allNodes.get("Activity_0moo2g2");
//        List<GraphNode> graphNodes = graphNode.getStateNeighborList();
//        System.out.println(graphNode.getParent().getGraphId());
//        for(GraphNode node:graphNodes){
//            System.out.println(node.getGraphId());
//        }
        //得到联邦真实名称
        getRealFederateNamae();
        //得到交互类信息
        getInteractionInformation();
        //将所有访问标记置false
        changeVisited();
        //生成xml文件
        for(Map.Entry<String, PoolNode> pool : allPools.entrySet()){
            PoolNode poolNode = pool.getValue();
            completeXml(poolNode);
            completeXml2(poolNode);
        }
    }

    private static void modifyEndEvent() {
        List<Node> sequenceFlowNodes = document.selectNodes("//sequenceFlow");
        for(int i=0;i<sequenceFlowNodes.size();++i){
            Element sequenceFlowNode=(Element)sequenceFlowNodes.get(i);
            String sourceRef = sequenceFlowNode.attributeValue("sourceRef");
            String targetRef = sequenceFlowNode.attributeValue("targetRef");
            sourceToTarget.put(sourceRef,targetRef);
            targetToSource.put(targetRef,sourceRef);
            sequenceFlowUse.put(targetRef+sourceRef,false);
        }
        List<Node> endEventNodes = document.selectNodes("//endEvent");
        for(int i=0;i<endEventNodes.size();++i){
            Element endEventNode=(Element)endEventNodes.get(i);
            String eventId = endEventNode.attributeValue("id");
            String needId = targetToSource.get(eventId);
            String eventBelongProcess = allNodes.get(needId).getBelongProcess();
            allNodes.get(eventId).setBelongProcess(eventBelongProcess);
        }

    }

    private static void getInteractionInformation() {
        List<Node> messageNodes = document.selectNodes("//messageFlow");
        for(int i=0;i<messageNodes.size();++i){
            Element messageNode=(Element)messageNodes.get(i);
            String interactionName = messageNode.attributeValue("name");
            String graphNodeId = messageNode.attributeValue("targetRef");
            allNodes.get(graphNodeId).setInteractionInformation(interactionName);
        }

    }

    private static void findUnVisitedNodes() {
        for(Map.Entry<String, GraphNode> nodeEntry : allNodes.entrySet()){
            GraphNode node = nodeEntry.getValue();
            if(!node.isVisited()){
                String belongProcess = node.getBelongProcess();
                PoolNode poolNode = allPools.get(belongProcess);
                poolNode.getPoolUnVisitedNodes().add(node);
            }
        }
    }

    private static void BFS(GraphNode root) {
        Queue<GraphNode> queue = new LinkedList<>();
        root.setVisited(true);
        // 加到队列队尾
        queue.add(root);
        while (!queue.isEmpty()) {
            GraphNode r = queue.poll();
            for (GraphNode node : r.getNeighborList()) {
                if (!node.isVisited()) {
                    node.setVisited(true);
                    queue.add(node);
                }
            }
        }
    }

    private static void completeXml(PoolNode poolNode) {

        StringBuilder content = new StringBuilder();
        List<GraphNode> poolUnVisitedNodes = poolNode.getPoolUnVisitedNodes();
        String federateName = poolNameToFederateName.get(poolNode.getProcessId());
        content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:tns=\"http://www.activiti.org/test\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://www.activiti.org/test\" id=\"m1586786943177\" name=\"\">\n");
        content.append(" <process id=\""+federateName+"\" name=\""+federateName+"\" isExecutable=\"true\" isClosed=\"false\" processType=\"None\">\n");

        //存在孤立节点
        if(poolUnVisitedNodes.size()>0){
            GraphNode poolFirstNode = startNodesMap.get(poolNode.getProcessId());
            //防止孤立没有头节点
            if(poolFirstNode==null){
                poolFirstNode=poolUnVisitedNodes.get(0);
                startNodesMap.put(poolNode.getProcessId(),poolFirstNode);
            }
            //初始节点为开始节点 把孤立节点放在开始节点之后,并加入一个gate节点
            if (poolFirstNode.getNodeState()==0){
                GraphNode aNode=new GraphNode("s"+UUID.randomUUID().toString());
                aNode.setNodeState(2);
                List<GraphNode> firstNeighbors=poolFirstNode.getStateNeighborList();
                aNode.getStateNeighborList().addAll(firstNeighbors);
                aNode.getStateNeighborList().addAll(poolUnVisitedNodes);
                poolFirstNode.getStateNeighborList().clear();
                poolFirstNode.getStateNeighborList().add(aNode);
                poolNode.getPoolGraphNodes().add(aNode);

            }
            //初始节点非开始节点 创建一个gate节点
            else {
                GraphNode aNode=new GraphNode("s"+UUID.randomUUID().toString());
                aNode.setNodeState(2);
                aNode.getStateNeighborList().addAll(poolUnVisitedNodes);
                if(!poolUnVisitedNodes.contains(poolFirstNode)){
                    aNode.getStateNeighborList().add(poolFirstNode);
                }
                startNodesMap.put(poolNode.getProcessId(),aNode);
                poolNode.getPoolGraphNodes().add(aNode);
            }
        }
        //非开始节点,创建一个开始节点做新的联邦开始节点 firstNode有可能改变重新获取
        GraphNode newFirstNode=startNodesMap.get(poolNode.getProcessId());
        if(newFirstNode.getNodeState()!=0){
            GraphNode startNode=new GraphNode("s"+UUID.randomUUID().toString());
            startNode.setNodeState(0);
            startNode.getStateNeighborList().add(newFirstNode);
            startNodesMap.put(poolNode.getProcessId(),startNode);
            poolNode.getPoolGraphNodes().add(startNode);
        }
        //对pool的每一个节点操作
        List<GraphNode> graphNodes=poolNode.getPoolGraphNodes();
        for (GraphNode node:graphNodes){
            if(node.getNodeState()==3){
                content.append("<endEvent id=\""+node.getGraphId()+"\" />\n");
            }//exclusiveGateway
            else if(node.getNodeState()==2){
                content.append("<parallelGateway gatewayDirection=\"Unspecified\" id=\""+node.getGraphId()+"\" />\n");
                List<GraphNode> gatewayNeighbors = node.getStateNeighborList();
                for(GraphNode graphNode:gatewayNeighbors){
                    String randomId="s"+UUID.randomUUID().toString();
                    content.append("<sequenceFlow id=\""+randomId+"\" sourceRef=\""+node.getGraphId()+"\" targetRef=\""+graphNode.getGraphId()+"\"/>\n");

                }
            }//userTask
            else if(node.getNodeState()==1){
                content.append("<userTask id=\""+node.getGraphId()+"\" name=\""+node.getGraphName()+"\"><documentation id=\""+"s"+UUID.randomUUID().toString()+"\">"+node.getInteractionInformation()+"</documentation></userTask>\n");
                //节点后无内容加一个endEvent
                if(node.getStateNeighborList()==null || node.getStateNeighborList().size()==0){
                    String source=node.getGraphId();
                    if(sourceToTarget.containsKey(source)){
                        String target = sourceToTarget.get(source);
                        content.append("<sequenceFlow id=\""+"s"+UUID.randomUUID().toString()+"\" sourceRef=\""+source+"\" targetRef=\""+target+"\"/>\n");

                    }else {
                        String randomEnd="s"+UUID.randomUUID().toString();
                        content.append("<endEvent id=\""+randomEnd+"\" />\n");
                        content.append("<sequenceFlow id=\""+"s"+UUID.randomUUID().toString()+"\" sourceRef=\""+node.getGraphId()+"\" targetRef=\""+randomEnd+"\"/>\n");

                    }
                }else {
                    List<GraphNode> userNeighbors = node.getStateNeighborList();
                    if(userNeighbors.size()==1){
                        content.append("<sequenceFlow id=\""+"s"+UUID.randomUUID().toString()+"\" sourceRef=\""+node.getGraphId()+"\" targetRef=\""+userNeighbors.get(0).getGraphId()+"\"/>\n");

                    }else {
                        //加一个gate
                        String randomGate="s"+UUID.randomUUID().toString();
                        content.append("<parallelGateway gatewayDirection=\"Unspecified\" id=\""+randomGate+"\" />\n");
                        content.append("<sequenceFlow id=\""+"s"+UUID.randomUUID().toString()+"\" sourceRef=\""+node.getGraphId()+"\" targetRef=\""+randomGate+"\"/>\n");
                        for(GraphNode graphNode:userNeighbors){
                            String randomId="s"+UUID.randomUUID().toString();
                            content.append("<sequenceFlow id=\""+randomId+"\" sourceRef=\""+randomGate+"\" targetRef=\""+graphNode.getGraphId()+"\"/>\n");
                        }
                    }
                }
            }
            //startEvent
            else {
                content.append("<startEvent id=\""+node.getGraphId()+"\" />\n");
                for(GraphNode graphNode:node.getStateNeighborList()){
                    String randomId="s"+UUID.randomUUID().toString();
                    content.append("<sequenceFlow id=\""+randomId+"\" sourceRef=\""+node.getGraphId()+"\" targetRef=\""+graphNode.getGraphId()+"\"/>\n");
                }
            }
        }
        //修复-个节点在pool中收到多个只显示一个的bug

        content.append("\t</process>\n" +
                "</definitions>\n");
        WriteIntoFile(poolNode.getProcessId(),content.toString());
        //WriteIntoFile2(poolNode.getProcessId(),content.toString());

    }

    private static void completeXml2(PoolNode poolNode) {
        StringBuilder content = new StringBuilder();
        String federateName = poolNameToFederateName.get(poolNode.getProcessId());
        content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:tns=\"http://www.activiti.org/test\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://www.activiti.org/test\" id=\"m1586786943177\" name=\"\">\n");
        content.append(" <process id=\""+federateName+"\" name=\""+federateName+"\" isExecutable=\"true\" isClosed=\"false\" processType=\"None\">\n");
        List<GraphNode> poolAllNodes=poolNode.getPoolGraphNodes();
        String randomStart="s"+UUID.randomUUID().toString();
        String randomGate="s"+UUID.randomUUID().toString();
        content.append("<startEvent id=\""+randomStart+"\" />\n");
        content.append("<sequenceFlow id=\""+"s"+UUID.randomUUID().toString()+"\" sourceRef=\""+randomStart+"\" targetRef=\""+randomGate+"\"/>\n");
        content.append("<parallelGateway gatewayDirection=\"Unspecified\" id=\""+randomGate+"\" />\n");
        for(GraphNode graphNode:poolAllNodes){
            if(graphNode.getNodeState()==1){
                String randomEnd="s"+UUID.randomUUID().toString();
                content.append("<userTask id=\""+graphNode.getGraphId()+"\" name=\""+graphNode.getGraphName()+"\"><documentation id=\""+"s"+UUID.randomUUID().toString()+"\">"+graphNode.getInteractionInformation()+"</documentation></userTask>\n");
                content.append("<sequenceFlow id=\""+"s"+UUID.randomUUID().toString()+"\" sourceRef=\""+randomGate+"\" targetRef=\""+graphNode.getGraphId()+"\"/>\n");
                content.append("<sequenceFlow id=\""+"s"+UUID.randomUUID().toString()+"\" sourceRef=\""+graphNode.getGraphId()+"\" targetRef=\""+randomEnd+"\"/>\n");
                content.append("<endEvent id=\""+randomEnd+"\" />\n");
            }
        }
        content.append("\t</process>\n" +
                "</definitions>\n");
        WriteIntoFile3(poolNode.getProcessId(),content.toString());
    }

    private static void WriteIntoFile3(String processId,String xmlStr){
        String poolName = poolNameToFederateName.get(processId);
        File file = null;
        FileWriter fw =null;
        try{
            file = new File(myparse.pathVariable.getShowbpmnpath()+poolName+".bpmn");
            if(!file.exists()){
                file.createNewFile();
            }
            fw = new FileWriter(file);
            fw.write(xmlStr);
            fw.flush();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void WriteIntoFile(String processId,String xmlStr){
        String poolName = poolNameToFederateName.get(processId);
        File file = null;
        FileWriter fw =null;
        try{
            ///Users/america/Mystudy/elema/src/main/resources/processes/
            String strFile = myparse.pathVariable.getProcessespath()+poolName+".bpmn";
            file = new File(strFile);
            if(!file.exists()){
                file.createNewFile();
            }
            fw = new FileWriter(file);
            fw.write(xmlStr);
            fw.flush();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private static void WriteIntoFile2(String processId,String xmlStr){
        String poolName = poolNameToFederateName.get(processId);
        File file = null;
        FileWriter fw =null;
        try{
            file = new File( myparse.pathVariable.getShowbpmnpath()+poolName+".xml");
            if(!file.exists()){
                file.createNewFile();
            }
            fw = new FileWriter(file);
            fw.write(xmlStr);
            fw.flush();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static String createXmlFile(String processId) throws IOException {
        String federateName=poolNameToFederateName.get(processId);
        String filePath="src\\parse\\diagram2\\"+federateName+".txt";
        File f = new File(filePath);
        if (f.exists()) {
            System.out.print("文件存在");
        } else {
            boolean bool = f.createNewFile();
            System.out.println(bool);
        }
        return filePath;
    }

    private static void getRealFederateNamae() {
        List<Node> participantNodes = document.selectNodes("//participant");
        for(int i=0;i<participantNodes.size();++i){
            Element federate=(Element)participantNodes.get(i);
            String processId=federate.attributeValue("processRef");
            String federateName=federate.attributeValue("name");
            poolNameToFederateName.put(processId,federateName);
        }
    }

    private static void changeVisited() {
        for(Map.Entry<String, GraphNode> node : allNodes.entrySet()){
            node.getValue().setVisited(false);
        }
    }

    private static void searchBFS(GraphNode root,String poolName) {
        if(root.getBelongProcess().equals(poolName)){
            startNodesMap.put(poolName,root);
        }else {
            Queue<GraphNode> queue = new LinkedList<>();
            root.setVisited(true);
            queue.add(root);
            while (!queue.isEmpty()) {
                GraphNode r = queue.poll();
                for (GraphNode node : r.getNeighborList()) {
                    if (!node.isVisited()) {
                        if(node.getBelongProcess().equals(poolName)){
                            startNodesMap.put(poolName,node);
                            break;
                        }else {
                            node.setVisited(true);
                            queue.add(node);
                        }

                    }
                }
            }
        }


    }

    private static void constructStateGraph() {
        for(Map.Entry<String, PoolNode> pool : allPools.entrySet()){
            List<GraphNode> poolGraphNodes = pool.getValue().getPoolGraphNodes();
            String processId = pool.getValue().getProcessId();
            GraphNode startNode = startNodesMap.get(processId);
            for (GraphNode graphNode:poolGraphNodes){
                if(!graphNode.equals(startNode)&&!pool.getValue().getPoolUnVisitedNodes().contains(graphNode)){
                    searchLatestNode(graphNode,processId);
                }
            }
        }
    }

    private static void searchLatestNode(GraphNode source,String federateName) {
        GraphNode p=new GraphNode(source.getGraphId());
        p.setParent(source.getParent());
        while (true){
            GraphNode parent=p.getParent();
            if(parent.getBelongProcess().equals(federateName)){
                parent.getStateNeighborList().add(source);
                source.setStateParent(parent);
                break;
            }else {
                if(parent.getParent()!=null){
                    p.setParent(parent.getParent());
                }else {
                    break;
                }
            }
        }
    }


    private static void constructAllPoolNodes() {
        List<Node> poolNodes = document.selectNodes("//process");
        List<Node> idNodes = document.selectNodes("//process/@id");
        poolNumber=poolNodes.size();
        for(int i=0;i<poolNodes.size();i++){

            String federateName = idNodes.get(i).getStringValue();
            PoolNode newNode=new PoolNode(federateName);

            Element poolElement = (Element)poolNodes.get(i);
            Iterator<Element> children = poolElement.elementIterator();
            int k=0;
            for(;children.hasNext();){

                Element childNode = children.next();
                String childNodeId = childNode.attributeValue("id");
                // GraphNode curNode=new GraphNode();
                ++k;
                //getName，返回这是什么类型的节点
                if (childNode.getName().equals("startEvent")){
                    newNode.setHasStartEvent(true);

                    GraphNode curNode=new GraphNode(childNodeId);
                    curNode.setNodeState(0);
                    curNode.setBelongProcess(federateName);
                    allNodes.put(childNodeId,curNode);
                    startEventNode=curNode;
                    newNode.getPoolGraphNodes().add(curNode);
                }
                if (childNode.getName().equals("sequenceFlow")){
                    --k;

                }
                if (childNode.getName().equals("endEvent")){
                    GraphNode curNode=new GraphNode(childNodeId);
                    curNode.setNodeState(3);
                    curNode.setBelongProcess(federateName);
                    allNodes.put(childNodeId,curNode);
                    newNode.getPoolGraphNodes().add(curNode);
                }
                if (childNode.getName().equals("userTask")){
                    GraphNode curNode=new GraphNode(childNodeId);
                    curNode.setNodeState(1);
                    curNode.setBelongProcess(federateName);
                    curNode.setGraphName(childNode.attributeValue("name"));
                    allNodes.put(childNodeId,curNode);
                    newNode.getPoolGraphNodes().add(curNode);
                }
                if (childNode.getName().equals("exclusiveGateway")){
                    GraphNode curNode=new GraphNode(childNodeId);
                    curNode.setNodeState(2);
                    curNode.setBelongProcess(federateName);
                    curNode.setGraphName(childNode.attributeValue("name"));
                    allNodes.put(childNodeId,curNode);
                    newNode.getPoolGraphNodes().add(curNode);
                }
            }
            newNode.setPoolNodeNumber(k);
            allPools.put(federateName,newNode);
        }

    }

    private static void constructAllGraph() {
        List<Node> LineNodes = new ArrayList<>();
        List<Node> messageFlowNodes = document.selectNodes("//messageFlow");
        List<Node> sequenceFlowNodes = document.selectNodes("//sequenceFlow");
        LineNodes.addAll(messageFlowNodes);
        LineNodes.addAll(sequenceFlowNodes);
        for(Node flowNode:LineNodes){
            Element flowElement=(Element)flowNode;
            String sourceRefId = flowElement.attributeValue("sourceRef");
            String targetRefId = flowElement.attributeValue("targetRef");
            GraphNode sourceNode=allNodes.get(sourceRefId);
            GraphNode targetNode=allNodes.get(targetRefId);
            sourceNode.getNeighborList().add(targetNode);
            targetNode.setParent(sourceNode);

        }


    }


    private static void readXML(File file) throws DocumentException {
        SAXReader reader=new SAXReader();
        document=reader.read(file);
        String xmlStr = document.asXML();
        xmlStr = xmlStr.replaceFirst("<definitions.*>", "<definitions>");
        xmlStr = xmlStr.replaceAll("<bpmn.*:.*", "");
        xmlStr = xmlStr.replaceAll("</bpmndi:.*>", "");
        xmlStr = xmlStr.replaceAll("<omg.*:.*", "");
        document= DocumentHelper.parseText(xmlStr);
    }

}
