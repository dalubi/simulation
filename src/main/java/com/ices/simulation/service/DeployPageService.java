package com.ices.simulation.service;

import com.ices.simulation.cyf.utils.generateUtils;
import com.ices.simulation.dao.mapper.*;
import com.ices.simulation.dao.model.federateList;
import com.ices.simulation.dao.model.federateObject;
import com.ices.simulation.dao.model.federateVariable;
import com.ices.simulation.dto.federateDTO;
import com.ices.simulation.service.parseXML.readXML;
import com.ices.simulation.service.process.activitiProcess;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.el.VariableMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeployPageService {
    @Autowired
    activitiProcess proc;

    @Autowired
    federateMapper federateMapper;

    @Autowired
    startInformationMapper startInformationMapper;

    @Autowired
    federateListMapper federateListMapper;

    @Autowired
    federateVariableMapper federateVariableMapper;

    @Autowired
    federateObjectMapper federateObjectMapper;

    public void LoadBPMNFile(String processDefinitionKey,String pathName){
        //proc.init("platform", "processes/platform.bpmn");
        proc.init(processDefinitionKey,pathName);
        //加载文件
        proc.process();
    }

    public String BPMNXMLStr (String showbpmnpath,String pathName) throws DocumentException {
        //在页面中找到bpmn图
        StringBuilder xmlpath = new StringBuilder();
        xmlpath.append(showbpmnpath)
                .append(pathName.split("/")[1].split(".")[0])
                .append(".xml");
        String xmlstr = readXML.read(xmlpath.toString());
        return xmlstr;
    }

    public int getCurFederateIdByName(String federateName){
        int federateIdByName = federateMapper.findFederateIdByName(federateName);
        return federateIdByName;
    }

    public List<federateDTO> getFederateInfos(){
        List<federateDTO> federateDTOS = federateMapper.allFederateInfo();
        return federateDTOS;
    }

    public void federateListProcess(String[] objectNames,String[] listNames,int curFederateId){
        int count=objectNames.length;
        //先拿到原有的list 再和新的合并
        List<Integer> curListId=new ArrayList<>();
        String findObjectsById = startInformationMapper.queryFederateObjectsById(curFederateId);
        if(findObjectsById!=null){
            curListId.addAll(generateUtils.extract(findObjectsById));
        }

        for(int i=0;i<count;++i){
            federateList federatelist=new federateList();
            federatelist.setObjectName(objectNames[i]);
            federatelist.setListName(listNames[i]);
            federateListMapper.insert(federatelist);
            int maxId = federateListMapper.maxId();
            curListId.add(maxId);
        }

        StringBuilder sb=new StringBuilder();
        for(Integer id:curListId){
            sb.append(id);
            sb.append(",");
        }
        startInformationMapper.updateFederateListByFederateId(curFederateId,sb.toString());
    }

    public void federateVariableProcess(String[] typeArray,String[] nameArray,String[] isStaticArray,
                                        String[] initialValueArray,int curFederateId){
        int count = typeArray.length;
        //先拿到原有的list 再和新的合并
        List<Integer> curlistId=new ArrayList<>();
        String findObjectsById = startInformationMapper.queryFederateObjectsById(curFederateId);
        if(findObjectsById!=null){
            curlistId.addAll(generateUtils.extract(findObjectsById));
        }
        for(int i=0;i<count;i++){
            federateVariable federateVariable=new federateVariable();
            federateVariable.setVariableType(typeArray[i]);
            federateVariable.setVariableName(nameArray[i]);
            federateVariable.setIsStatic(Integer.parseInt(isStaticArray[i]));
            federateVariable.setInitialValue(initialValueArray[i]);
            federateVariableMapper.insert(federateVariable);
            int maxId = federateVariableMapper.maxId();
            curlistId.add(maxId);
        }
        StringBuilder sb=new StringBuilder();
        for(Integer id:curlistId){
            sb.append(id);
            sb.append(",");
        }
        startInformationMapper.updateFederateVariableByFederateId(curFederateId,sb.toString());
    }

    public void federateObjectProcess( String[] objectNames,String[] InitialIds,String[] parameterNamesArray, String[] paremeterNamesFinals,
                                       String[] parameterTypesArray,String[] paremeterTypesFinals,int curFederateId){
        int parameterNamesCount = 0 ;
        for(String parameterNamesElem:parameterNamesArray){
            //将他们用逗号连接起来
            String[] split1 = parameterNamesElem.split(" ");
            StringBuilder sb = new StringBuilder();
            for(String elem1:split1){
                sb.append(elem1);
                sb.append(",");
            }
            paremeterNamesFinals[parameterNamesCount++]=sb.toString();
        }

        int parameterTypesCount = 0 ;
        for(String parameterTypesElem:parameterTypesArray){
            //fwe ewfw 将他们用逗号连接起来
            String[] split1 = parameterTypesElem.split(" ");
            StringBuilder sb = new StringBuilder();
            for(String elem1:split1){
                sb.append(elem1);
                sb.append(",");
            }
            paremeterTypesFinals[parameterTypesCount++]=sb.toString();
        }
        int objectCount=objectNames.length;

        List<Integer> curlistId=new ArrayList<>();
        String s = startInformationMapper.queryFederateObjectsById(curFederateId);
        if(s!=null){
            curlistId.addAll(generateUtils.extract(s));
        }

        for(int i=0;i<objectCount;i++){
            federateObject federateobject = new federateObject();
            federateobject.setObjectName(objectNames[i]);
            federateobject.setInitialId(InitialIds[i]);
            federateobject.setParameterTypes(paremeterTypesFinals[i]);
            federateobject.setParameterNames(paremeterNamesFinals[i]);
            federateObjectMapper.insert(federateobject);
            int maxId = federateObjectMapper.maxId();
            curlistId.add(maxId);
        }
        StringBuilder sb=new StringBuilder();
        for(Integer id:curlistId){
            sb.append(id);
            sb.append(",");
        }
        startInformationMapper.updateFederateObjectByFederateId(curFederateId,sb.toString());
    }
}
