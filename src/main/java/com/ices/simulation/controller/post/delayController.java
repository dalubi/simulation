package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionDelayMapper;
import com.ices.simulation.dao.model.instructionDelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class delayController {
    @Autowired
    instructionDelayMapper instructionDelayMapper;


    @RequestMapping(value = "/post/delay",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> delayInstruction(@RequestParam("delayTime")int delayTime,
                                                   @RequestParam("logInformation")String logInformation)
    {
        instructionDelay instruction = new instructionDelay();
        instruction.setDelayTime(delayTime);
        instruction.setLogInformation(logInformation);
        instruction.setIndent(5);
        instructionDelayMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
