package com.ices.simulation.controller.post;
import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionExpressionMapper;
import com.ices.simulation.dao.mapper.taskMapper;
import com.ices.simulation.dao.model.instructionExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
public class expressionController {
    @Autowired
    instructionExpressionMapper instructionexpressionMapper;

    @Autowired
    taskMapper taskMapper;

    @RequestMapping(value = "/post/expression",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> expressionInstruction(@RequestParam("expressionInformation")String expressionInformation,
                                                    @RequestParam("fillType")String fillType,
                                                    @RequestParam("fillInformation")String fillInformation,
                                                    @RequestParam("outType")String outType,
                                                    @RequestParam("outName")String outName)
    {
        instructionExpression instruction = new instructionExpression();
        instruction.setExpressionInformation(expressionInformation);
        instruction.setFillType(fillType);
        instruction.setFillInformation(fillInformation);
        instruction.setOutType(outType);
        instruction.setOutName(outName);
        instruction.setIndent(5);
        instructionexpressionMapper.insert(instruction);

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

        int instructionId = instructionexpressionMapper.maxId();
        sequence = sequence+"expression,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }
}
