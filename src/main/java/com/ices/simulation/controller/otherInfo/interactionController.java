package com.ices.simulation.controller.otherInfo;

import com.ices.simulation.dao.mapper.interactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class interactionController {
    @Autowired
    interactionMapper interactionMapper;

    @RequestMapping(value = "/post/fillInteractionWithParams",method = RequestMethod.POST)
    @ResponseBody
    public String fillInteraction(@RequestParam("interactionId") String interactionId,
                                  @RequestParam("parameters") String parameters){
        String[] interactionIdList = interactionId.split(",");
        String[] parameterList = parameters.split(",");
        //parameterList  17;19;20
        int count=interactionIdList.length;
        for (int i=0;i<count;++i){
            String containParameter=parameterList[i];
            String realContainParameter = containParameter.replaceAll(";", ",");
            int interId=Integer.parseInt(interactionIdList[i]);
            interactionMapper.giveParamsToInteractionById(interId,realContainParameter);
        }
        return "交互类参数信息填充完毕";
    }
}
