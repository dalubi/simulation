package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionListSizeMapper;
import com.ices.simulation.dao.model.instructionListSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class listsizeController {
    @Autowired
    instructionListSizeMapper instructionMapper;


    @RequestMapping(value = "/post/listsize",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> listsizeInstruction(@RequestParam("listName")String listName,
                                                     @RequestParam("outName")String outName)
    {
        instructionListSize instruction = new instructionListSize();
        instruction.setListName(listName);
        instruction.setOutName(outName);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
