package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionTypeConversionMapper;
import com.ices.simulation.dao.model.instructionTypeConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class typeconversionController {
    @Autowired
    instructionTypeConversionMapper instructionMapper;


    @RequestMapping(value = "/post/typeconversion",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> typeconversionInstruction(@RequestParam("conversionType")String conversionType,
                                                     @RequestParam("information")String information,
                                                     @RequestParam("outName")String outName)
    {
        instructionTypeConversion instruction = new instructionTypeConversion();
        instruction.setConversionType(conversionType);
        instruction.setInformation(information);
        instruction.setOutName(outName);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
