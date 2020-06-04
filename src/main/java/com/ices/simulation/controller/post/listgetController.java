package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionListGetMapper;
import com.ices.simulation.dao.model.instructionListGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class listgetController {
    @Autowired
    instructionListGetMapper instructionMapper;

    @RequestMapping(value = "/post/listget",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> listgetInstruction(@RequestParam("listName")String listName,
                                                     @RequestParam("objectName")String objectName,
                                                     @RequestParam("getType")int getType,
                                                     @RequestParam("outName")String outName,
                                                     @RequestParam("fillInformation")String fillInformation,
                                                     @RequestParam("accordingParameter")String accordingParameter)
    {
        instructionListGet instruction = new instructionListGet();
        instruction.setListName(listName);
        instruction.setObjectName(objectName);
        instruction.setOutName(outName);
        instruction.setFillInformation(fillInformation);
        instruction.setAccordingParameter(accordingParameter);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
