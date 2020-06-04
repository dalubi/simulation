package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionRandomOrderNameMapper;
import com.ices.simulation.dao.model.instructionRandomOrderName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class randomordernameController {
    @Autowired
    instructionRandomOrderNameMapper instructionMapper;
    
    @RequestMapping(value = "/post/randomordername",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> randomordernameInstruction(@RequestParam("outName")String outName)
    {
        instructionRandomOrderName instruction = new instructionRandomOrderName();
        instruction.setOutName(outName);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
