package com.ices.simulation.controller.post;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionTypeConversionMapper;
import com.ices.simulation.dao.model.instructionTypeConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class typeconversionController {
    @Autowired
    instructionTypeConversionMapper instructionMapper;

    @Autowired
    com.ices.simulation.dao.mapper.taskMapper taskMapper;

    @RequestMapping(value = "/post/typeconversion",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> typeconversionInstruction(@RequestParam("conversionType")String conversionType,
                                                     @RequestParam("information")String information,
                                                     @RequestParam("outName")String outName)
    {
        instructionTypeConversion instruction = new instructionTypeConversion();
        instruction.setConversionType(conversionType);
        instruction.setInformation(information);
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
        sequence = sequence+"typeconversion,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }
}
