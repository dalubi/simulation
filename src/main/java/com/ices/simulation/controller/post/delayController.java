package com.ices.simulation.controller.post;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionDelayMapper;
import com.ices.simulation.dao.mapper.taskMapper;
import com.ices.simulation.dao.model.instructionDelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class delayController {
    @Autowired
    instructionDelayMapper instructionDelayMapper;

    @Autowired
    taskMapper taskMapper;

    private static String taskId;


    @RequestMapping(value = "/post/delay",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> delayInstruction(@RequestParam("delayTime")String delayTime,
                                                   @RequestParam("logInformation")String logInformation)
    {
        instructionDelay instruction = new instructionDelay();
        instruction.setDelayTime(Integer.parseInt(delayTime));
        instruction.setLogInformation(logInformation);
        instruction.setIndent(5);
        instructionDelayMapper.insert(instruction);

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

        int instructionId = instructionDelayMapper.maxId();
        sequence = sequence+"delay,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }

}
