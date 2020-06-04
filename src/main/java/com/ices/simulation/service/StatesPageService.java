package com.ices.simulation.service;

import com.ices.simulation.dao.mapper.federateMapper;
import com.ices.simulation.dao.mapper.interactionMapper;
import com.ices.simulation.dao.mapper.parameterMapper;
import com.ices.simulation.dao.model.interaction;
import com.ices.simulation.dao.model.parameter;
import com.ices.simulation.dto.bpmnDTO;
import com.ices.simulation.dto.interactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatesPageService {
    @Resource
    parameterMapper parameterMapper;

    @Resource
    interactionMapper interactionMapper;

    public List<bpmnDTO> getBpmnInfos(String path){
        List<bpmnDTO> bpmnDTOs = new ArrayList<>();
        File dictionary = new File(path);
        File[] bpmns = dictionary.listFiles();
        for(int i=0;i<bpmns.length;i++){
            String bpmnfileName = bpmns[i].getName();
            if(bpmnfileName.matches(".*.bpmn")){
                bpmnDTO bpmndto = new bpmnDTO();
                bpmndto.setBpmnFileName(bpmnfileName);
                bpmndto.setBpmnName(bpmnfileName.split("\\.")[0]);
                bpmnDTOs.add(bpmndto);
            }
        }
        return bpmnDTOs;
    }

    public List<parameter> getParameterInfos(){
        List<parameter> parameters = parameterMapper.getAllParameter();
        return  parameters;
    }

    public List<interaction> getInteractions(){
        List<interaction> allInteractions = interactionMapper.getAllInteractions();
        return allInteractions;
    }

}
