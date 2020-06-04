package com.ices.simulation.controller;
;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class taskInfoController {
    
    @GetMapping("/taskInfo/{taskId}")
    public String getTaskInfo(@PathVariable("taskId")String taskId, Model model){

        return "taskInfo";
    }
}
