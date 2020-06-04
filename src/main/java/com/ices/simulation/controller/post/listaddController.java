package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionListAddMapper;
import com.ices.simulation.dao.model.instructionListAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class listaddController {
    @Autowired
    instructionListAddMapper instructionMapper;

    @RequestMapping(value = "/post/listadd",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> listaddInstruction(@RequestParam("listName")String listName,
                                                     @RequestParam("objectName")String objectName)
    {
        instructionListAdd instruction = new instructionListAdd();
        instruction.setListName(listName);
        instruction.setObjectName(objectName);
        instruction.setIndent("5");
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
