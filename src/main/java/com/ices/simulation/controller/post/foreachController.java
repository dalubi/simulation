package com.ices.simulation.controller.post;


import com.ices.simulation.dao.mapper.instructionForeachMapper;
import com.ices.simulation.dao.model.instructionForeach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class foreachController {
    @Autowired
    instructionForeachMapper instructionMapper;


    @RequestMapping(value = "/post/foreach",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> foreachInstruction(@RequestParam("listName")String listName,
                                                    @RequestParam("objectName")String objectName,
                                                    @RequestParam("outName")String outName,
                                                    @RequestParam("insideTaskId")int insideTaskId)
    {
        instructionForeach instruction = new instructionForeach();
        instruction.setListName(listName);
        instruction.setObjectName(objectName);
        instruction.setOutName(outName);
        instruction.setInsideTaskId(insideTaskId);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
