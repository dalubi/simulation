package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionObjectGetMapper;
import com.ices.simulation.dao.model.instructionObjectGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class objectgetController {
    @Autowired
    instructionObjectGetMapper instructionMapper;


    @RequestMapping(value = "/post/objectget",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> objectgetInstruction(@RequestParam("parameterName")String parameterName,
                                                     @RequestParam("objectName")String objectName,
                                                     @RequestParam("outName")String outName,
                                                     @RequestParam("outType")String outType)
    {
        instructionObjectGet instruction = new instructionObjectGet();
        instruction.setObjectName(objectName);
        instruction.setOutType(outType);
        instruction.setParameterName(parameterName);
        instruction.setOutName(outName);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
