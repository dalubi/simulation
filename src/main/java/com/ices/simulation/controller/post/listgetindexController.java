package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionListGetIndexMapper;
import com.ices.simulation.dao.model.instructionListGetIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class listgetindexController {
    @Autowired
    instructionListGetIndexMapper instructionMapper;


    @RequestMapping(value = "/post/listgetindex",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> listgetindexInstruction(@RequestParam("listName")String listName,
                                                     @RequestParam("objectName")String objectName,
                                                     @RequestParam("outName")String outName,
                                                     @RequestParam("fillInformation")String fillInformation)
    {
        instructionListGetIndex instruction = new instructionListGetIndex();
        instruction.setListName(listName);
        instruction.setObjectName(objectName);
        instruction.setOutName(outName);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);
    }
}
