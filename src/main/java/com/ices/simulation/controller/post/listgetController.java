package com.ices.simulation.controller.post;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionListGetMapper;
import com.ices.simulation.dao.model.instructionListGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class listgetController {
    @Autowired
    instructionListGetMapper instructionMapper;

    @Autowired
    com.ices.simulation.dao.mapper.taskMapper taskMapper;

    @RequestMapping(value = "/post/listget",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> listgetInstruction(@RequestParam("listName")String listName,
                                                     @RequestParam("objectName")String objectName,
                                                     @RequestParam("getType")int getType,
                                                     @RequestParam("outName")String outName,
                                                     @RequestParam("fillInformation")String fillInformation,
                                                     @RequestParam("accordingParameter")String accordingParameter)
    {
        instructionListGet instruction = new instructionListGet();
        instruction.setListName(listName);
        instruction.setObjectName(objectName);
        instruction.setOutName(outName);
        instruction.setFillInformation(fillInformation);
        instruction.setAccordingParameter(accordingParameter);
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
        sequence = sequence+"listget,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }
}
