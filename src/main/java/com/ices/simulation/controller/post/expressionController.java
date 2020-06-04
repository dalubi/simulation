package com.ices.simulation.controller.post;
import com.ices.simulation.dao.mapper.instructionExpressionMapper;
import com.ices.simulation.dao.model.instructionExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class expressionController {
    @Autowired
    instructionExpressionMapper instructionexpressionMapper;


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
        String body = "成功插入";
        return new ResponseEntity<String>(body, HttpStatus.OK);

    }
}
