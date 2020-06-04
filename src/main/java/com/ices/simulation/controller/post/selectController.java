package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionSelectMapper;
import com.ices.simulation.dao.model.instructionSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class selectController {
    @Autowired
    instructionSelectMapper instructionMapper;


    @RequestMapping(value = "/post/select",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> selectInstruction(@RequestParam("InformationName")String InformationName,
                                                     @RequestParam("branch1Id")int branch1Id,
                                                     @RequestParam("branch2Id")int branch2Id)
    {
        instructionSelect instruction = new instructionSelect();
        instruction.setInformationName(InformationName);
        instruction.setBranch1id(branch1Id);
        instruction.setBranch2id(branch2Id);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
