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
                                    @RequestParam("parameterTypes")String parameterTypes,
                                    @RequestParam("parameterNames")String parameterNames){

        String[] objectNames = objectName.split(",");
        String[] InitialIds = InitialId.split(",");
        String[] parameterNamesArray = parameterNames.split(",");
        String[] paremeterNamesFinals = new String[parameterNamesArray.length];
        String[] parameterTypesArray = parameterTypes.split(",");
        String[] paremeterTypesFinals = new String[parameterTypesArray.length];
        thisPageService.federateObjectProcess(objectNames,InitialIds,parameterNamesArray,paremeterNamesFinals,parameterTypesArray,paremeterTypesFinals,this.curFederateId);
        return "本联邦所需对象的设计信息已提交";
    }

}
