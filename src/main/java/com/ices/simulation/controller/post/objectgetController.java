package com.ices.simulation.controller.post;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionObjectGetMapper;
import com.ices.simulation.dao.model.instructionObjectGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class objectgetController {
    @Autowired
    instructionObjectGetMapper instructionMapper;

    @Autowired
    com.ices.simulation.dao.mapper.taskMapper taskMapper;


    @RequestMapping(value = "/post/objectget",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> objectgetInstruction(@RequestParam("parameterName")String parameterName,
                                                     @RequestParam("objectName")String objectName,
                                                     @RequestParam("outName")String outName,
                                                     @RequestParam("outType")String outType)
    {
        instructionObjectGet instruction = new instructionObjectGet();
        instruction.setObjectName(objectName);
        instruction.setOutType(outType);
        instruction.setParameterName(parameterName);
        instruction.setOutName(outName);
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
        sequence = sequence+"objectget,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }
}
