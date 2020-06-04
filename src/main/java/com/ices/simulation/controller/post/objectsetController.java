package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionObjectSetMapper;
import com.ices.simulation.dao.model.instructionObjectSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class objectsetController {
    @Autowired
    instructionObjectSetMapper instructionMapper;


    @RequestMapping(value = "/post/objectset",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> objectsetInstruction(@RequestParam("parameterName")String parameterName,
                                                     @RequestParam("objectName")String objectName,
                                                     @RequestParam("setName")String setName,
                                                     @RequestParam("setType")int setType)
    {
        instructionObjectSet instruction = new instructionObjectSet();
        instruction.setParameterName(parameterName);
        instruction.setSetType(setType);
        instruction.setSetName(setName);
        instruction.setObjectName(objectName);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
