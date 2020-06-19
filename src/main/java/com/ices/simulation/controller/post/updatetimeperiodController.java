package com.ices.simulation.controller.post;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionUpdateTimePeriodMapper;
import com.ices.simulation.dao.model.instructionUpdateTimePeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class updatetimeperiodController {
    @Autowired
    instructionUpdateTimePeriodMapper instructionMapper;

    @Autowired
    com.ices.simulation.dao.mapper.taskMapper taskMapper;

    @RequestMapping(value = "/post/updatetimeperiod",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> updatetimeperiodInstruction(@RequestParam("instanceName")String instanceName,
                                                     @RequestParam("activeListName")String activeListName,
                                                     @RequestParam("dormantListName")String dormantListName)
    {
        instructionUpdateTimePeriod instruction = new instructionUpdateTimePeriod();
        instruction.setInstanceName(instanceName);
        instruction.setActiveListName(activeListName);
        instruction.setDormantListName(dormantListName);
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
        sequence = sequence+"updatetimeperiod,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }
}
