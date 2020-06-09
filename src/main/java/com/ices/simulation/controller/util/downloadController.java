package com.ices.simulation.controller.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class downloadController {
    @RequestMapping("/downLog")
    @ResponseBody
    public String downLog(@RequestParam(name = "federateName")String federateName,HttpServletResponse response) throws UnsupportedEncodingException {
        return "收到下载"+federateName+"日志信息的请求";
    }
}
