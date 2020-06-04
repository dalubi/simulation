package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionListClearMapper;
import com.ices.simulation.dao.model.instructionListClear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class listclearController {
    @Autowired
    instructionListClearMapper instructionMapper;

    @RequestMapping(value = "/post/listclear",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> listclearInstruction(@RequestParam("listName")String listName)
    {
        instructionListClear instruction = new instructionListClear();
        instruction.setListName(listName);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
