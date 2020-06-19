package com.ices.simulation.controller;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.insidetaskMapper;
import com.ices.simulation.dao.mapper.taskMapper;
import com.ices.simulation.dao.model.insideTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class insideTaskController {

    @Autowired
    insidetaskMapper insidetaskMapper;

    @Autowired
    taskMapper taskMapper;

    @GetMapping("/insideTask")
    public String getInsideTask(){
        //返回所有内部id的详细信息

        return "insideTask";
    }

    @RequestMapping(value = "/post/insidetask", method = RequestMethod.POST)
    @ResponseBody
    public String postinsidetask(@RequestParam("insidetaskId") String insidetaskId,
                                 @RequestParam("information") String information,
                                 @RequestParam("iscomplete") String iscomplete){
        String[] insidetaskIds = insidetaskId.split(",");
        String[] informations = information.split(",");
        String[] iscompletes = iscomplete.split(",");
        int count=insidetaskIds.length;
        for(int i=0;i<count;++i){
            insideTask insidetask=new insideTask();
            insidetask.setInsidetaskId(Integer.parseInt(insidetaskIds[i]));
            insidetask.setInformation(informations[i]);
            insidetask.setIscomplete("未完成");
            insidetaskMapper.insert(insidetask);
        }
        taskMapper.insertTaskId(insidetaskId);
        return "success";
    }

    @PostMapping("/post/insideTaskID")
    @ResponseBody
    public String postInsideTask(@RequestParam("insidetaskId")String insidetaskId){
        curTaskId.setTaskId(insidetaskId);
        StringBuilder sb = new StringBuilder();
        sb.append("现在可以插入编号为")
                .append(insidetaskId)
                .append("的内部任务的指令");
        return sb.toString();
    }
}
