package com.ices.simulation.controller.otherInfo;

import com.ices.simulation.dao.mapper.parameterMapper;
import com.ices.simulation.dao.model.parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class federateParameterController {
    @Autowired
    parameterMapper parameterMapper;

    @GetMapping("/post/parameter")
    public String listgetInstruction(@RequestParam("parameterName")String parameterNames,
                                     @RequestParam("parameterType")String parameterTypes)
    {
        String[] parameterNameArray = parameterNames.split(",");
        String[] parameterTypeArray = parameterTypes.split(",");

        int count=parameterNameArray.length;

        for(int i=0;i<count;++i){
            parameter para = new parameter();
            para.setParameterName(parameterNameArray[i]);
            para.setParameterType(parameterTypeArray[i]);
            parameterMapper.insert(para);
        }

        return "redirect:/states";
    }

}
