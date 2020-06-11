package com.ices.simulation.controller;

import com.ices.simulation.cyf.pathVariable;
import com.ices.simulation.dto.bpmnDTO;
import com.ices.simulation.service.StatesPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class statesController {

    @Autowired
    pathVariable pathVariable;

    @Autowired
    StatesPageService thisPageService;

    @GetMapping("/states")
    public String index(Model model){

        //找到processes下面所有bpmn文件
        //File dictionary = new File("/Users/america/Mystudy/elema/src/main/resources/processes");
        List<bpmnDTO> bpmnInfos = thisPageService.getBpmnInfos(pathVariable.getProcessesfilefolder());

        //将bpmn文件输入到页面上
        model.addAttribute("bpmnDTOs",bpmnInfos);

        //找到所有的参数类型
        model.addAttribute("parameters",thisPageService.getParameterInfos());

        //找到所有的交互类
        model.addAttribute("interactionDTOs",thisPageService.getInteractions());

        return "states";
    }
}
