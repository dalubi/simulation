package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionUpdateTimePeriodMapper;
import com.ices.simulation.dao.model.instructionUpdateTimePeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class updatetimeperiodController {
    @Autowired
    instructionUpdateTimePeriodMapper instructionMapper;


    @RequestMapping(value = "/post/updatetimeperiod",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> updatetimeperiodInstruction(@RequestParam("instanceName")String instanceName,
                                                     @RequestParam("activeListName")String activeListName,
                                                     @RequestParam("dormantListName")String dormantListName)
    {
        instructionUpdateTimePeriod instruction = new instructionUpdateTimePeriod();
        instruction.setInstanceName(instanceName);
        instruction.setActiveListName(activeListName);
        instruction.setDormantListName(dormantListName);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
