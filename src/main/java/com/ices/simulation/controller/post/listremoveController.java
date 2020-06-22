package com.ices.simulation.controller.post;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionListRemoveMapper;
import com.ices.simulation.dao.model.instructionListRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class listremoveController {
    @Autowired
    instructionListRemoveMapper instructionMapper;

    @Autowired
    com.ices.simulation.dao.mapper.taskMapper taskMapper;
    
    @RequestMapping(value = "/post/listremove",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> listremoveInstruction(@RequestParam("listId")String listId,
                                                     @RequestParam("accordingToParameterName")String accordingToParameterName,
                                                     @RequestParam("removeType")String removeType,
                                                     @RequestParam("removeName")String removeName)
    {
        instructionListRemove instruction = new instructionListRemove();
        instruction.setListId(Integer.parseInt(listId));
        instruction.setAccordingToParameterName(accordingToParameterName);
        instruction.setRemoveName(removeName);
        instruction.setRemoveType(Integer.parseInt(removeType));
        instruction.setIndent(5);
        instructionMapper.insert(instruction);

        updateTask();

        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTask(){
        String taskId = curTaskId.getTaskId();

        String sequence = taskMapper.findInstructionSequenceById(taskId);
        String interactionIds = taskMapper.findInstructionIdsById(taskId);

        if (sequence==null){
            sequence="";
        }
        if (interactionIds==null){
            interactionIds="";
        }

        int instructionId = instructionMapper.maxId();
        sequence = sequence+"listremove,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }
}
