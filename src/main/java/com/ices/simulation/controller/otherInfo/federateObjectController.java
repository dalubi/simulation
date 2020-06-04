package com.ices.simulation.controller.otherInfo;

import com.ices.simulation.dao.mapper.federateObjectMapper;
import com.ices.simulation.dao.model.federateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class federateObjectController {

    @Autowired
    federateObjectMapper federateObjectMapper;

    @RequestMapping(value = "/post/federateobject",method = RequestMethod.POST)
    @ResponseBody
    public String postFederateObjct(@RequestParam("objectName")String objectName,
                                    @RequestParam("InitialId")String InitialId,
                                    @RequestParam("parameterTypes")String parameterTypes,
                                    @RequestParam("parameterNames")String parameterNames){

        String[] objectNames = objectName.split(",");
        String[] InitialIds = InitialId.split(",");
        //fwe ewfw,w32f fwfe-->fwe ewfw
        String[] parameterNamesArray = parameterNames.split(",");
        String[] paremeterNamesFinals = new String[parameterNamesArray.length];
        int parameterNamesCount = 0 ;
        for(String parameterNamesElem:parameterNamesArray){
            //将他们用逗号连接起来
            String[] split1 = parameterNamesElem.split(" ");
            StringBuilder sb = new StringBuilder();
            for(String elem1:split1){
                sb.append(elem1);
                sb.append(",");
            }
            paremeterNamesFinals[parameterNamesCount++]=sb.toString();
        }
        //ewfwe wfew,ewfew fwefw
        String[] parameterTypesArray = parameterTypes.split(",");
        String[] paremeterTypesFinals = new String[parameterTypesArray.length];
        int parameterTypesCount = 0 ;
        for(String parameterTypesElem:parameterTypesArray){
            //fwe ewfw 将他们用逗号连接起来
            String[] split1 = parameterTypesElem.split(" ");
            StringBuilder sb = new StringBuilder();
            for(String elem1:split1){
                sb.append(elem1);
                sb.append(",");
            }
            paremeterTypesFinals[parameterTypesCount++]=sb.toString();
        }
        int objectCount=objectNames.length;
        for(int i=0;i<objectCount;i++){
            federateObject federateobject = new federateObject();
            federateobject.setObjectName(objectNames[i]);
            federateobject.setInitialId(InitialIds[i]);
            federateobject.setParameterTypes(paremeterTypesFinals[i]);
            federateobject.setParameterNames(paremeterNamesFinals[i]);
            federateObjectMapper.insert(federateobject);
        }
        return "本联邦所需对象的设计信息已提交";
    }
}
