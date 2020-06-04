package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionRandomIntMapper;
import com.ices.simulation.dao.model.instructionRandomInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class randomintController {
    @Autowired
    instructionRandomIntMapper instructionMapper;


    @RequestMapping(value = "/post/randomint",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> randomintInstruction(@RequestParam("fromInt")int fromInt,
                                                     @RequestParam("toInt")int toInt,
                                                     @RequestParam("outName")String outName)
    {
        instructionRandomInt instruction = new instructionRandomInt();
        instruction.setFromInt(fromInt);
        instruction.setToInt(toInt);
        instruction.setOutName(outName);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
