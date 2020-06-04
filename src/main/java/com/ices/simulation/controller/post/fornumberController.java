package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionFornumberMapper;
import com.ices.simulation.dao.model.instructionFornumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class fornumberController {
    @Autowired
    instructionFornumberMapper instructionMapper;


    @RequestMapping(value = "/post/fornumber",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> fornumberInstruction(@RequestParam("number")int number,
                                                     @RequestParam("insideTaskId")int insideTaskId)
    {
        instructionFornumber instruction = new instructionFornumber();
        instruction.setNumber(number);
        instruction.setInsideTaskId(insideTaskId);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
