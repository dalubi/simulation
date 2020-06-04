package com.ices.simulation.controller.post;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;


import com.example.elema.dao.mapper.instructioncreateMapper;
import com.example.elema.dao.model.instructioncreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class createController {

    @Autowired
    instructioncreateMapper instructioncreateMapper;



    @RequestMapping(value = "/post/create",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createInstruction(@RequestParam("federateObject")String federateObject,
                                            @RequestParam("typeInformation")String typeInformation,
                                            @RequestParam("actualInformation")String actualInformation,
                                            @RequestParam("outName")String outName)
    {
        instructioncreate create = new instructioncreate();
        create.setOutName(outName);
        create.setTypeInformation(typeInformation);
        create.setActualInformation(actualInformation);
        create.setIndent(5);
        int objectId = instructioncreateMapper.findObjectIdByName(federateObject);
        create.setObjectId(objectId);
        instructioncreateMapper.insert(create);

        String body = "成功插入，ObjectId为"+objectId;
        return new ResponseEntity<String>(body, HttpStatus.OK);
    }
}
