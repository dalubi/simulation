package com.ices.simulation.controller;

import com.ices.simulation.dao.mapper.editInformationMapper;
import com.ices.simulation.dao.mapper.taskMapper;
import com.ices.simulation.dao.model.editInformation;
import com.ices.simulation.dto.taskDTO;
import com.ices.simulation.service.CurrentTaskPageService;
import com.ices.simulation.service.parseXML.readXML;
import com.ices.simulation.service.process.activitiProcess;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class currentTaskController {

    @Autowired
    CurrentTaskPageService thisPageService;

    private static activitiProcess proc;

    public static activitiProcess getProc() {
        return proc;
    }

    public static void setProc(activitiProcess proc) {
        currentTaskController.proc = proc;
    }


    //再次刷新页面，任务会更新的
    @GetMapping("/currentTask")
    public String CurrentTask(Model model) throws DocumentException {

        taskDTO taskdto = proc.queryTask();
        publishController.setTaskdto(taskdto);

        String pathName = proc.getPathName();

        //将xml展示到图片上
        model.addAttribute("xmlstr",thisPageService.showBPMN(pathName));

        thisPageService.CurrentTaskProcess(taskdto,proc);

        //把当前的任务取出来,都完成它
        if (taskdto!=null){
            model.addAttribute("taskdto",taskdto);
        }else {
            model.addAttribute("message","finish");
        }
        return "currentTask";
    }

}
