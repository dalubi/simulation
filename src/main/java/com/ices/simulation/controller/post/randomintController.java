package com.ices.simulation.controller.post;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionRandomIntMapper;
import com.ices.simulation.dao.model.instructionRandomInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class randomintController {
    @Autowired
    instructionRandomIntMapper instructionMapper;

    @Autowired
    com.ices.simulation.dao.mapper.taskMapper taskMapper;


    @RequestMapping(value = "/post/randomint",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> randomintInstruction(@RequestParam("range")String range,
                                                     @RequestParam("outName")String outName)
    {
        String[] split = range.split(",");
        int fromInt = Integer.parseInt(split[0]);
        int toInt = Integer.parseInt(split[1]);

        instructionRandomInt instruction = new instructionRandomInt();
        instruction.setFromInt(fromInt);
        instruction.setToInt(toInt);
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
        sequence = sequence+"randomint,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }
}
