package com.ices.simulation.controller;

import com.ices.simulation.cyf.pathVariable;
import com.ices.simulation.dao.mapper.federateListMapper;
import com.ices.simulation.dao.mapper.interactionMapper;
import com.ices.simulation.dao.mapper.parameterMapper;
import com.ices.simulation.dao.mapper.startInformationMapper;
import com.ices.simulation.dto.federateDTO;
import com.ices.simulation.service.DeployPageService;
import com.ices.simulation.service.process.activitiProcess;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class deployController {

    @Autowired
    pathVariable pathVariable;

    @Autowired
    DeployPageService thisPageService;

    private int curFederateId;

    @Autowired
    interactionMapper interactionMapper;

    @Autowired
    parameterMapper parameterMapper;

    @Autowired
    startInformationMapper startInformationMapper;

    @Autowired
    federateListMapper federateListMapper;


    @GetMapping("/deploy")
    public String deploy(@RequestParam(name = "processDefinitionKey")String processDefinitionKey,
                         @RequestParam(name = "pathName")String pathName,
                         Model model) throws DocumentException {

        //activiti引擎加载图
        activitiProcess proc = thisPageService.LoadBPMNFile(processDefinitionKey, pathName);

        //别忘了打开
        currentTaskController.setProc(proc);
        publishController.setProc(proc);

        //将联邦成员的名字输出
        model.addAttribute("processDefineKey",proc.getProcessDefinitionKey());

        //在页面中找到bpmn图，放到页面上
        String xmlstr = thisPageService.BPMNXMLStr(pathVariable.getShowbpmnpath(), pathName);
        model.addAttribute("xmlstr",xmlstr);

        List<federateDTO> federateInfos = thisPageService.getFederateInfos();
        model.addAttribute("federateInfos",federateInfos);

        this.curFederateId = thisPageService.getCurFederateIdByName(processDefinitionKey);
        return "deploy";
    }

    @RequestMapping(value = "/post/federatelist", method = RequestMethod.POST)
    @ResponseBody
    public String postFederatelist(@RequestParam("objectName") String objectName,
                                   @RequestParam("listName") String listName){
        String[] objectNames = objectName.split(",");
        String[] listNames = listName.split(",");
        thisPageService.federateListProcess(objectNames,listNames,this.curFederateId);
        return "队列信息插入成功";
    }

    @RequestMapping(value = "/post/federateVariable",method = RequestMethod.POST)
    @ResponseBody
    public String postFederateVariable(@RequestParam("variableType")String variableType,
                                       @RequestParam("variableName")String variableNames,
                                       @RequestParam("isStatic")String isStatics,
                                       @RequestParam("InitialValue")String InitialValues){
        String[] typeArray = variableType.split(",");
        String[] nameArray = variableNames.split(",");
        String[] isStaticArray = isStatics.split(",");
        String[] initialValueArray = InitialValues.split(",");
        thisPageService.federateVariableProcess(typeArray,nameArray,isStaticArray,initialValueArray,this.curFederateId);
        return "联邦设计中所需的全局变量上传完成";
    }


    @RequestMapping(value = "/post/federateobject",method = RequestMethod.POST)
    @ResponseBody
    public String postFederateObjct(@RequestParam("objectName")String objectName,
                                    @RequestParam("InitialId")String InitialId,
                                    @RequestParam("parameterNameList")String parameterNames,
                                    @RequestParam("parameterTypeList")String parameterTypes){

        String[] objectNames = objectName.split(",");
        String[] InitialIds = InitialId.split(",");
        // 33,55,;,33,55,;,
        String[] nameLevel1 = parameterNames.split(";");
        String[] typeLevel1 = parameterTypes.split(";");

        int Level = nameLevel1.length-1;//用';'分出3层，for是从0到1，level等于2

        String[] nameLevel2 = new String[Level];
        String[] typeLevel2 = new String[Level];

        for(int i=0;i<Level;i++){
            if(nameLevel1[i].charAt(nameLevel1[i].length()-1)==','){
                nameLevel1[i]=nameLevel1[i].substring(0,nameLevel1[i].length()-1);
            }
            if(nameLevel1[i].charAt(0)==','){
                nameLevel1[i]=nameLevel1[i].substring(1);
            }
            nameLevel2[i]=nameLevel1[i];

            if(typeLevel1[i].charAt(typeLevel1[i].length()-1)==','){
                typeLevel1[i]=typeLevel1[i].substring(0,typeLevel1[i].length()-1);
            }
            if(typeLevel1[i].charAt(0)==','){
                typeLevel1[i]=typeLevel1[i].substring(1);
            }
            typeLevel2[i]=typeLevel1[i];
        }

        thisPageService.federateObjectProcess(objectNames,InitialIds,nameLevel2,typeLevel2,this.curFederateId);
        return "本联邦所需对象的设计信息已提交";
    }

}
