package com.ices.simulation.controller.post;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionSelectMapper;
import com.ices.simulation.dao.model.instructionSelect;
import com.ices.simulation.dao.mapper.taskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class selectController {
    @Autowired
    instructionSelectMapper instructionMapper;

    @Autowired
    taskMapper taskMapper;

    @GetMapping(value = "/post/select")
    @ResponseBody
    public ResponseEntity<String> selectInstruction(@RequestParam("informationName")String InformationName,
                                                     @RequestParam("branch1Id")String branch1Id,
                                                     @RequestParam("branch2Id")String branch2Id)
    {
        instructionSelect instruction = new instructionSelect();
        instruction.setInformationName(InformationName);
        instruction.setBranch1id(Integer.parseInt(branch1Id));
        instruction.setBranch2id(Integer.parseInt(branch2Id));
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
        sequence = sequence+"select,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }
}
