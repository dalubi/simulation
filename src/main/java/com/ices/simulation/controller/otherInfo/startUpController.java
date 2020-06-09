package com.ices.simulation.controller.otherInfo;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Controller
public class startUpController {

    @GetMapping("/createSimulation")
    @ResponseBody
    public String generateAll()  throws IOException {
        return "所有联邦成员代码生成完成";
    }

    //然后联邦必须一个个启动，sleep时间现在是30s
    @GetMapping("/startup")
    @ResponseBody
    public String startup(@RequestParam(name = "federateName")String federateName) {
        return "联邦成员:"+federateName+",已运行";
    }

}
