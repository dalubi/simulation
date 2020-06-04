package com.ices.simulation.controller.otherInfo;

import com.ices.simulation.dao.mapper.federateMapper;
import com.ices.simulation.dao.mapper.startInformationMapper;
import com.ices.simulation.dao.model.startInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class startinformationController {

    @Autowired
    startInformationMapper startinformationMapper;

    @Autowired
    federateMapper federateMapper;

    @RequestMapping(value = "/post/startinformation",method = RequestMethod.POST)
    @ResponseBody
    public String postFederateVariable(@RequestParam("federateId")String federateId,
                                       @RequestParam("isFirst")String isFirst,
                                       @RequestParam("firstTask")String firstTask,
                                       @RequestParam("initialInstanceTask")String initialInstanceTask,
                                       @RequestParam("updateInstanceTask")String updateInstanceTask){
        String[] federateIds = federateId.split(",");
        String[] isFirsts = isFirst.split(",");
        String[] firstTasks = firstTask.split(",");
        String[] initialInstanceTasks = initialInstanceTask.split(",");
        String[] updateInstanceTasks = updateInstanceTask.split(",");




        int finalCount=federateIds.length;
        for(int i=0;i<finalCount;i++){
            startInformation startinformation=new startInformation();
            startinformation.setFederateId(Integer.parseInt(federateIds[i]));
            startinformation.setIsFirst(Integer.parseInt(isFirsts[i]));
            startinformation.setFirstTask(Integer.parseInt(firstTasks[i]));
            startinformation.setInitialInstanceTask(Integer.parseInt(initialInstanceTasks[i]));
            startinformation.setUpdateInstanceTask(Integer.parseInt(updateInstanceTasks[i]));
            startinformationMapper.insert(startinformation);
        }
        return "联邦所有成员的初始化信息已记录！";
    }
}
