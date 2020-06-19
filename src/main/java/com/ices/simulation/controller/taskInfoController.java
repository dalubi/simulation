package com.ices.simulation.controller;
;
import com.ices.simulation.service.taskInfoService.pojo.taskInfo;
import com.ices.simulation.service.taskInfoService.taskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class taskInfoController {
    @Autowired
    taskInfoService thisPageService;
    
    @GetMapping("/taskInfo/{taskId}")
    //得到某个taskId下所有的任务以及描述信息
    public String getTaskInfo(@PathVariable("taskId")String taskId, Model model){
        List<taskInfo> taskDetailsByTaskId = thisPageService.getTaskDetailsByTaskId(Integer.parseInt(taskId));
        model.addAttribute("taskDetails",taskDetailsByTaskId);
        return "taskInfo";
    }
}
