package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.instructionListRemoveMapper;
import com.ices.simulation.dao.model.instructionListRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
public class listremoveController {
    @Autowired
    instructionListRemoveMapper instructionMapper;
    
    @RequestMapping(value = "/post/listremove",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> listremoveInstruction(@RequestParam("listId")int listId,
                                                     @RequestParam("accordingToParameterName")String accordingToParameterName,
                                                     @RequestParam("removeType")int removeType,
                                                     @RequestParam("removeName")String removeName)
    {
        instructionListRemove instruction = new instructionListRemove();
        instruction.setListId(listId);
        instruction.setAccordingToParameterName(accordingToParameterName);
        instruction.setRemoveName(removeName);
        instruction.setRemoveType(removeType);
        instruction.setIndent(5);
        instructionMapper.insert(instruction);
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
