package com.ices.simulation.controller.post;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionFornumberMapper;
import com.ices.simulation.dao.mapper.taskMapper;
import com.ices.simulation.dao.model.instructionFornumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class fornumberController {
    @Autowired
    instructionFornumberMapper instructionMapper;

    @Autowired
    taskMapper taskMapper;


    @RequestMapping(value = "/post/fornumber",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> fornumberInstruction(@RequestParam("number")String number,
                                                     @RequestParam("insideTaskId")String insideTaskId)
    {
        instructionFornumber instruction = new instructionFornumber();
        instruction.setNumber(Integer.parseInt(number));
        instruction.setInsideTaskId(Integer.parseInt(insideTaskId));
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
        sequence = sequence+"fornumber,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }
}
