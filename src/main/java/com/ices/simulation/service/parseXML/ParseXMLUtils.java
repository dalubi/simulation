package com.ices.simulation.service.parseXML;

import com.ices.simulation.dao.mapper.federateMapper;
import com.ices.simulation.dao.mapper.interactionMapper;
import com.ices.simulation.dao.model.federate;
import com.ices.simulation.service.parseXML.pojo.FederatePS;
import com.ices.simulation.service.parseXML.pojo.Interaction;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

@Component
public class ParseXMLUtils {

    private static Document document;
    private static List<Interaction> interactions = new ArrayList<>();

    @Autowired
    interactionMapper interactionMapper;

    @Autowired
    federateMapper federateMapper;

    public static ParseXMLUtils parseXMLUtils;

    @PostConstruct
    public void init() {
        parseXMLUtils = this;
        parseXMLUtils.interactionMapper = this.interactionMapper;
        parseXMLUtils.federateMapper=this.federateMapper;
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

    public static void parseProcess(String path) throws DocumentException {
        readXML(new File(path));

        ParseXMLUtils parseXMLUtils = new ParseXMLUtils();

        parseXMLUtils.getAllInteractions();

        //交互类名字，放入数据库
        parseXMLUtils.insertInteractions();//注释掉避免数据重复插入

        Map<String, FederatePS> ps = parseXMLUtils.createPS();

        parseXMLUtils.insertPS(ps);
    }

    //userTask id="Activity_1dolpbh" 属于哪个 process id="Process_0vjcn7m"
    //看看任务属于哪个pool
    private HashMap<String,String> TaskParticipantMap() throws DocumentException {
        HashMap<String,String> TFMap=new HashMap();
        String TaskId;

        List<Node> userTasknodes = document.selectNodes("//userTask");

        for(Iterator<Node> iterator=userTasknodes.iterator();iterator.hasNext();){
            Node next = iterator.next();
            String[] split = next.asXML().split("\"");
            TaskId=split[1];

            Element parent = next.getParent();

            //每个userTask的父亲节点的名字
            Attribute parentAttribute = parent.attribute(0);
            String processId = parentAttribute.getValue();

            TFMap.put(TaskId,processId);
        }
        return TFMap;
    }

    // id="Activity_1dolpbh" name="发布作业"
    private HashMap<String,String> taskIdNameMap(){
        HashMap<String,String> INMap=new HashMap<>();

        List<Node> userTasknodes = document.selectNodes("//userTask");
        for(Iterator<Node> iterator=userTasknodes.iterator();iterator.hasNext();){
            Node next = iterator.next();
            String[] split = next.asXML().split("\"");
            //taskId和taskName的映射
            String taskId=split[1];
            String taskName=split[3];
            INMap.put(taskId,taskName);
        }
        return INMap;
    }

    //participant id="Participant_1vc9bi4" name="teacher" processRef="Process_0vjcn7m"
    //<process id="Process_0vjcn7m" isExecutable="false">
    //  process的id适合participant中的processRef相对应的
    private HashMap<String,String> participantIdFederanteNameMap(){
        HashMap<String,String> FederateMap = new HashMap<>();
        List<Node> list = document.selectNodes("//participant");
        for(Iterator iterator=list.iterator();iterator.hasNext();){
            Object next = iterator.next();
            Element participant = (Element)next;
            Attribute name = participant.attribute("name");
            Attribute processRef = participant.attribute("processRef");
            FederateMap.put(processRef.getValue(),name.getValue());
        }
        return FederateMap;
    }

    //得到所有Interaction的信息
    //messageFlow id="Flow_0b3wb8i" name="publish_homework" sourceRef="Activity_1dolpbh" targetRef="Activity_0szfi63"
    private void getAllInteractions() throws DocumentException {
        List<Interaction> result=new ArrayList<>();
        List<Node> InteractionNames = document.selectNodes("//messageFlow/@name");
        List<Node> sourceID = document.selectNodes("//messageFlow/@sourceRef");
        List<Node> targetID = document.selectNodes("//messageFlow/@targetRef");

        //usertaskId 属于 processid : Activity_0akc5b8属于Process_1qxvosr
        HashMap<String, String> TaskParticipantMap = TaskParticipantMap();

        //processid 和 FederateName的映射 : Process_1qxvosr -> customer
        HashMap<String, String> participantIdFederanteNameMap = participantIdFederanteNameMap();

        Iterator<Node> iter1=InteractionNames.iterator();
        Iterator<Node> iter2=sourceID.iterator();
        Iterator<Node> iter3=targetID.iterator();

        for(;iter1.hasNext();){
            Attribute interactionNameAttribute = (Attribute) iter1.next();
            Attribute sourceIdAttribute = (Attribute) iter2.next();
            Attribute targetIdAttribute =(Attribute) iter3.next();

            String interactionName = interactionNameAttribute.getValue();
            String sourceId = sourceIdAttribute.getValue();
            String targetId = targetIdAttribute.getValue();

            String publishFederate = participantIdFederanteNameMap.get(TaskParticipantMap.get(sourceId));
            String subscribeFederate = participantIdFederanteNameMap.get(TaskParticipantMap.get(targetId));

            Interaction interaction = new Interaction();
            interaction.setInteractionName(interactionName);
            interaction.setPublish(publishFederate);
            interaction.setSubscribe(subscribeFederate);

            parseXMLUtils.interactions.add(interaction);
        }
    }

    private void insertInteractions(){
        for (Interaction interaction:interactions){
            parseXMLUtils.interactionMapper.insertByName(interaction.getInteractionName());
        }
    }

    //建立订阅关系
    private Map<String,FederatePS> createPS(){

        HashMap<String, String> participantIdFederanteNameMap = participantIdFederanteNameMap();
        Collection<String> FederateNames = participantIdFederanteNameMap.values();

        Map<String,FederatePS> FederatePSMap=new HashMap<>();

        for(Iterator<String> iterator=FederateNames.iterator();iterator.hasNext();){
            String FederateName = iterator.next();
            FederatePSMap.put(FederateName,new FederatePS(new ArrayList<>(),new ArrayList<>()));
        }
        for(Interaction interaction:interactions){
            String publish = interaction.getPublish();
            String subscribe = interaction.getSubscribe();
            int interactionId = parseXMLUtils.interactionMapper.getInteractionIdByName(interaction.getInteractionName());
            FederatePSMap.get(publish).getPublishes().add(interactionId);
            FederatePSMap.get(subscribe).getSubscribes().add(interactionId);
        }
        return FederatePSMap;
    }

    private void insertPS(Map<String,FederatePS> ps){
        Set<String> federateNameSet = ps.keySet();
        for(String federate:federateNameSet){
            List<Integer> publishes = ps.get(federate).getPublishes();
            List<Integer> subscribes = ps.get(federate).getSubscribes();
            String strPub = ListToString(publishes);
            String strSub = ListToString(subscribes);
            federate federate1 = new federate();
            federate1.setFederateName(federate);
            federate1.setPublishInteractionIds(strPub);
            federate1.setSubscribeInteractionIds(strSub);
            parseXMLUtils.federateMapper.insert(federate1);
        }
    }

    private static String ListToString(List<Integer> list){
        StringBuilder sb=new StringBuilder();
        Iterator iterator = list.iterator();
        for(;iterator.hasNext();){
            int element = (int)iterator.next();
            sb.append(element);
            if (iterator.hasNext()){
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
