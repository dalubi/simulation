package com.ices.simulation.controller.post;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;

import com.ices.simulation.controller.util.curTaskId;
import com.ices.simulation.dao.mapper.instructionCreateMapper;
import com.ices.simulation.dao.mapper.taskMapper;
import com.ices.simulation.dao.model.instructionCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Controller
public class createController {

    @Autowired
    instructionCreateMapper instructionCreateMapper;

    @Autowired
    taskMapper taskMapper;

    @RequestMapping(value = "/post/create",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createInstruction(@RequestParam("federateObject")String federateObject,
                                            @RequestParam("typeInformation")String typeInformation,
                                            @RequestParam("actualInformation")String actualInformation,
                                            @RequestParam("outName")String outName)
    {
        instructionCreate create = new instructionCreate();
        create.setOutName(outName);
        create.setTypeInformation(typeInformation);
        create.setActualInformation(actualInformation);
        create.setIndent(5);
        int objectId = instructionCreateMapper.findObjectIdByName(federateObject);
        create.setObjectId(objectId);
        instructionCreateMapper.insert(create);

        updateTask();

        String body = "成功插入，ObjectId为"+objectId;
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

        int instructionId = instructionCreateMapper.maxId();
        sequence = sequence+"delay,";
        interactionIds=interactionIds+instructionId+",";

        taskMapper.updateSequenceAndinstructionIdsById(sequence,interactionIds,taskId);
    }
}
