package com.ices.simulation.controller;

import com.ices.simulation.dto.taskDTO;
import com.ices.simulation.service.process.activitiProcess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class publishController {

    private static List<taskDTO> taskDTOS;
    private static activitiProcess proc;

    private static taskDTO taskdto;

    public static taskDTO getTaskdto() {
        return taskdto;
    }

    public static void setTaskdto(taskDTO taskdto) {
        publishController.taskdto = taskdto;
    }

    public static List<taskDTO> getTaskDTOS() {
        return taskDTOS;
    }

    public static void setTaskDTOS(List<taskDTO> taskDTOS) {
        publishController.taskDTOS = taskDTOS;
    }

    public static activitiProcess getProc() {
        return proc;
    }

    public static void setProc(activitiProcess proc) {
        publishController.proc = proc;
    }

    @GetMapping("/publish")
    public String publish(){
        //将输入的指令通过post放到此处
        proc.completeTask(taskdto.getTaskId());
        return "redirect:/currentTask";
    }
}
