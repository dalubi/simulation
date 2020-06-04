package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionMathAbsMapper;
import com.ices.simulation.dao.model.instructionMathAbs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class mathabsController {
    @Autowired
    instructionMathAbsMapper instructionMapper;


    @RequestMapping(value = "/post/mathabs",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> mathabsInstruction(@RequestParam("fillInformation")String fillInformation,
                                                     @RequestParam("outName")String outName)
    {
        instructionMathAbs instruction = new instructionMathAbs();
        instruction.setFillInformation(fillInformation);
        instruction.setOutName(outName);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
