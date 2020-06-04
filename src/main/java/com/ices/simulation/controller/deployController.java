package com.ices.simulation.controller;

import com.ices.simulation.config.pathVariable;
import com.ices.simulation.dao.mapper.federateMapper;
import com.ices.simulation.dao.mapper.interactionMapper;
import com.ices.simulation.dao.mapper.parameterMapper;
import com.ices.simulation.config.pathVariable;
import com.ices.simulation.service.parseXML.readXML;
import com.ices.simulation.service.process.activitiProcess;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class deployController {

    @Autowired
    activitiProcess proc;

    @Autowired
    pathVariable pathVariable;

    @Autowired
    federateMapper federateMapper;

    @Autowired
    interactionMapper interactionMapper;

    @Autowired
    parameterMapper parameterMapper;


    @GetMapping("/deploy")
    public String deploy(@RequestParam(name = "processDefinitionKey")String processDefinitionKey,
                         @RequestParam(name = "pathName")String pathName,
                         Model model) throws DocumentException {

        //proc.init("platform", "processes/platform.bpmn");
        proc.init(processDefinitionKey,pathName);
        //加载文件
        proc.process();
//        currentTaskController.setProc(proc);
//        publishController.setProc(proc);

        //在页面中找到bpmn图
        StringBuilder xmlpath = new StringBuilder();
        xmlpath.append(pathVariable.getShowbpmnpath())
                .append(pathName.split("/")[1].split(".")[0])
                .append(".xml");
        //读bpmn文件，读到XMLSTR
        String xmlstr = readXML.read(xmlpath.toString());
        model.addAttribute("xmlstr",xmlstr);

        return "deploy";
    }
}
