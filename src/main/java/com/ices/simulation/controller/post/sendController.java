package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionSendMapper;
import com.ices.simulation.dao.model.instructionSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class sendController {
    @Autowired
    instructionSendMapper instructionMapper;


    @RequestMapping(value = "/post/send",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> sendInstruction(@RequestParam("sendInteractionId")int sendInteractionId,
                                                     @RequestParam("sendTypes")String sendTypes,
                                                     @RequestParam("sendInformation")String sendInformation)
    {
        instructionSend instruction = new instructionSend();
        instruction.setSendInteractionId(sendInteractionId);
        instruction.setSendTypes(sendTypes);
        instruction.setSendInformation(sendInformation);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
