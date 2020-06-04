package com.ices.simulation.service;

import com.ices.simulation.cyf.pathVariable;
import com.ices.simulation.dao.mapper.editInformationMapper;
import com.ices.simulation.dao.mapper.taskMapper;
import com.ices.simulation.dao.model.editInformation;
import com.ices.simulation.dto.taskDTO;
import com.ices.simulation.service.parseXML.readXML;
import com.ices.simulation.service.process.activitiProcess;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentTaskPageService {

    @Autowired
    pathVariable pathVariable;

    @Autowired
    editInformationMapper editInformationMapper;

    @Autowired
    taskMapper taskMapper;

    public String showBPMN(String pathName) throws DocumentException {
        StringBuilder xmlpath = new StringBuilder();

        xmlpath.append(pathVariable.getShowbpmnpath())
                .append(pathName.split("/")[1].split("\\.")[0])
                .append(".xml");

        //读bpmn文件，读到XMLSTR
        String xmlstr = readXML.read(xmlpath.toString());
        return xmlstr;
    }

    public void CurrentTaskProcess(taskDTO taskdto, activitiProcess proc){
        //有交互类的task,没有交互类的task不会插入editInformation
        if (taskdto.getInteraction()!=null && !taskdto.getInteraction().equals("null")){
            Integer federateId = editInformationMapper.findFederateIdByFederateName(proc.getProcessDefinitionKey());
            Integer interactionId = editInformationMapper.findInteractionIdByInteractionName(taskdto.getInteraction());

            editInformation editInformation = new editInformation();
            editInformation.setFromFederateId(federateId);
            editInformation.setFromInteractionId(interactionId);
            editInformation.setTaskId(Integer.parseInt(taskdto.getTaskId()));
            editInformationMapper.insert(editInformation);
        }

        //任务插入数据库，并返回taskId，将taskId传入parse
        taskMapper.insertTaskId(taskdto.getTaskId());
    }
}
