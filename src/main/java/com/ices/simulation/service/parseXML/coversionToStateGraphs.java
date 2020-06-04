package com.ices.simulation.service.parseXML;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//收到交互类的任务里，增加一个标签，保存交互类的名字
public class coversionToStateGraphs {

    public static Document document;
    private static HashMap<String, String> TargetSourceMap = new HashMap<>();
    private static HashMap<String,String> SourceTargetMap = new HashMap<>();

    //传入bpmnpath，将这个bpmn转化为多个状态图
    public static void conversion(String bpmnpath) throws DocumentException {
        //将bpmn文件转化为xml文件，改个名字就好了
        File file = new File(bpmnpath);
        String xmlpath = bpmnpath.split("\\.")[0]+".bpmn";

        file.renameTo(new File(xmlpath));

        readXML(new File(xmlpath));
        giveTaskInteraction();
        generateStatusGraphs();
    }

    public static void main(String[] args) throws DocumentException {
//        readXML(new File("ices.simulation/elema/src/main/resources/bpmnGraph/bpmntest.xml"));
//        giveTaskInteraction();
//        generateStatusGraphs();
        String s = "/Users/america/Mystudy/elema/src/main/resources/bpmnGraph/bpmntest.bpmn";
        String[] split = s.split("\\.");
        System.out.println(s);
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

    //让所有交互类的接收任务都绑定他们的接收类
    private static void giveTaskInteraction(){
        List<Node> nameNodes = document.selectNodes("//messageFlow/@name");
        List<Node> targetRefNodes = document.selectNodes("//messageFlow/@targetRef");
        Iterator iter1 = nameNodes.iterator();
        Iterator iter2 = targetRefNodes.iterator();

        for(;iter1.hasNext();){
            Attribute nameAttribute = (Attribute)iter1.next();
            Attribute targetRefAttribute = (Attribute)iter2.next();
            String name = nameAttribute.getValue();
            String targetRef = targetRefAttribute.getValue();

            StringBuilder sb = new StringBuilder();

            //找到targetRef对应的userTask
            sb.append("//userTask[@id=\'"+targetRef+"\']");

            Element task =(Element) document.selectSingleNode(sb.toString());
            Element documentation = task.addElement("documentation");
            documentation.addAttribute("id","interaction"+UUID.randomUUID().toString().substring(0,4));
            documentation.setText(name);
        }
    }

    //把所有pool的内容都单独放到不同的文件里，生成不同的文件
    private static void generateStatusGraphs(){
        List<Node> poolNodes = document.selectNodes("//process");
        List<Node> idNodes = document.selectNodes("//process/@id");
        List<Node> startEventNodes = document.selectNodes("//startEvent");

        //给sourceTarget和targetSource，这两个map赋值
        sequenceFlowMap();

        //找到所有的根节点，从endEvent走到最前头的节点，但是不包括开始节点
        List<Node> roots = FindRoot();

        //找到在当前pool中的根节点，放入List中
        List<Node> curPoolRoots = new ArrayList<>();

        for(int i=0;i<poolNodes.size();i++){
            //当前这个pool的id，用来作为这个状态图的名字
            String id = idNodes.get(i).getStringValue();
            //找到当前这个pool开始的节点
            Node startNode = startEventNodes.get(i);
            //将在这个pool中的根节点放入curPoolRoots中
            for(Node root : roots){
                Element parent = root.getParent();
                if (parent.attributeValue("id").equals(id)){
                    curPoolRoots.add(root);
                }
            }
            //构建一个xml的str
            String xml = constructionOneStatusGraph(id, startNode, curPoolRoots,poolNodes.get(i));

            //状态图保存的地址
            String stateGraphspath ="/Users/america/Mystudy/elema/src/main/resources/processes/";

            //将这个字符串写到文件中
            WriteIntoFile(id,xml,stateGraphspath);

            //当前pool中的root内容清空
            curPoolRoots.clear();
        }
    }

    //将内容写到文件中去
    private static void WriteIntoFile(String fileName,String xmlStr,String path){
        String poolName = fileName;
        File file = null;
        FileWriter fw =null;
        try{
            //将文件，写入processes中
            file = new File(path+poolName+".bpmn");
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

    //poolName，开始节点，在当前这个节点的所有root，每一个pool节点,构造一个状态图的字符串
    private static String constructionOneStatusGraph(String poolName, Node startNode,
                                                    List<Node> roots, Node processNode){
        StringBuilder sb = new StringBuilder();

        //某个节点的属性值的获取
        //String s = startNode.selectSingleNode("@id").getStringValue();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:tns=\"http://www.activiti.org/test\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" expressionLanguage=\"http://www.w3.org/1999/XPath\" id=\"m1586585566623\" name=\"\" targetNamespace=\"http://www.activiti.org/test\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\">"+
                "\n\t <process id=\""+poolName+"\" isClosed=\"false\" isExecutable=\"true\" name=\"My process\" processType=\"None\">");
        sb.append("\n\t\t");

        //添加开始节点，和开始节点后面的并行网关，用sequenceFlow将二者连接起来
        sb.append(startNode.asXML()+"\n\t\t");
        sb.append("<parallelGateway gatewayDirection=\"Unspecified\" id=\"rootBefore\" name=\"ParallelGateway\"/>"+"\n\t\t");
        sb.append("<sequenceFlow id=\"startEventToParallelGate\" sourceRef=\""+startNode.selectSingleNode("@id").getStringValue() +"\" targetRef=\"rootBefore\"/>"+"\n\t\t");

        //将所有在这个pool中的根都放到rootBefore这个并行网关的后面
        int count=0;
        for(Node root:roots){
            //sequenceFlow的命名
            String sequenceFlowId = "rootBeforeSequence"+count++;
            sb.append("<sequenceFlow id=\""+sequenceFlowId+"\" " +
                    "sourceRef=\"rootBefore\" "+
                    "targetRef=\""+root.selectSingleNode("@id").getStringValue()+"\" />");
            sb.append("\n\t\t");
        }

        //把endEvent和userTask这些任务归类为一般的任务，放入返回的字符串中
        List<Node> normailNodes = processNode.selectNodes("endEvent|userTask");
        for (Node normailNode : normailNodes){
            sb.append(normailNode.asXML()+"\n\t\t");
        }

        //将所有排他网关改成并行网关
        List<Node> exclusiveGatewayNodes = processNode.selectNodes("exclusiveGateway");
        for(Node exclusiveGatewayNode:exclusiveGatewayNodes){
            String exclusiveGateWayStr = exclusiveGatewayNode.asXML().replaceAll("ExclusiveGateway","ParallelGateway")
                    .replaceAll("exclusiveGateway","parallelGateway");
            sb.append(exclusiveGateWayStr);
            sb.append("\n\t\t");
        }

        //找到所有sequenceFlow
        List<Node> sequenceFlowNodes = processNode.selectNodes("sequenceFlow");
        for(Node node : sequenceFlowNodes){
            //只要不是直接和开头相连的sequenceflow，都直接拿来就可以
            if(!node.asXML().matches(".*"+startNode.selectSingleNode("@id").getStringValue()+".*")){
                sb.append(node.asXML()+"\n\t\t");
            }
        }
        sb.append("\n");
        sb.append("\t</process>\n</definitions>");

        return sb.toString();
    }

    //sequenceFlowMap,构建一个从target到source到map和从source到target到map
    private static void sequenceFlowMap(){
        List<Node> targetRefAttribute = document.selectNodes("//sequenceFlow/@targetRef");
        List<Node> sourceRefAttribuet = document.selectNodes("//sequenceFlow/@sourceRef");
        Iterator<Node> iterTarget=targetRefAttribute.iterator();
        Iterator<Node> iterSource=sourceRefAttribuet.iterator();
        for(;iterSource.hasNext()&&iterTarget.hasNext();){
            Attribute targetAttrbute = (Attribute) iterTarget.next();
            Attribute sourceAttribute = (Attribute) iterSource.next();
            String target = targetAttrbute.getValue();
            String source = sourceAttribute.getValue();

            //result是反向的map
            TargetSourceMap.put(target,source);
            SourceTargetMap.put(source,target);
        }

        //把startEvent去掉
        List<Node> startEventIdnodes = document.selectNodes("//startEvent/@id");
        for(Iterator iter3 = startEventIdnodes.iterator();iter3.hasNext();){
            Attribute startEventIdAttribute = (Attribute)iter3.next();
            String startEventId = startEventIdAttribute.getValue();
            String s = SourceTargetMap.get(startEventId);
            TargetSourceMap.remove(s);
            SourceTargetMap.remove(startEventId);
        }
    }

    //找到所有从endEvent回溯到的最上面的节点（usertask）
    private static List<Node> FindRoot(){
        List<Node> roots = new ArrayList<>();
        //找到结尾节点
        List<Node> endEventIdNodes = document.selectNodes("//endEvent/@id");
        List<String> strRootIds = new ArrayList<String>();

        //找到每个结尾节点的链表的头节点
        for(Node endNode : endEventIdNodes){
            Attribute endAttr = (Attribute) endNode;
            String endValue = endAttr.getValue();
            while (TargetSourceMap.containsKey(endValue)){
                endValue=TargetSourceMap.get(endValue);
            }
            strRootIds.add(endValue);
        }

        List<Node> userTaskNodes = document.selectNodes("//userTask");
        for(Node userTaskNode : userTaskNodes){
            Element usertTask = (Element) userTaskNode;
            String s = usertTask.attributeValue("id");
            if(strRootIds.contains(s)){
                roots.add(userTaskNode);
            }
        }
        return roots;
    }

    private static Node getStartEvent(){
        return document.selectSingleNode("//startEvent");
    }

}
