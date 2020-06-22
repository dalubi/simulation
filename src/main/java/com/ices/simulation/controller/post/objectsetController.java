package com.ices.simulation.controller.post;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionObjectSetMapper;
import com.ices.simulation.dao.model.instructionObjectSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class objectsetController {
    @Autowired
    instructionObjectSetMapper instructionMapper;

    @Autowired
    com.ices.simulation.dao.mapper.taskMapper taskMapper;


    @RequestMapping(value = "/post/objectset",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> objectsetInstruction(@RequestParam("parameterName")String parameterName,
                                                     @RequestParam("objectName")String objectName,
                                                     @RequestParam("setName")String setName,
                                                     @RequestParam("setType")String setType)
    {
        instructionObjectSet instruction = new instructionObjectSet();
        instruction.setParameterName(parameterName);
        instruction.setSetType(Integer.parseInt(setType));
        instruction.setSetName(setName);
        instruction.setObjectName(objectName);
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
        sequence = sequence+"objectset,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }
}
