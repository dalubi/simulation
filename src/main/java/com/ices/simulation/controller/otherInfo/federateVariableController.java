package com.ices.simulation.controller.otherInfo;


import com.ices.simulation.dao.mapper.federateVariableMapper;
import com.ices.simulation.dao.model.federateVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class federateVariableController {

    @Autowired
    federateVariableMapper federateVariableMapper;

    @RequestMapping(value = "/post/federateVariable",method = RequestMethod.POST)
    @ResponseBody
    public String postFederateVariable(@RequestParam("variableType")String variableType,
                                       @RequestParam("variableName")String variableNames,
                                       @RequestParam("isStatic")String isStatics,
                                       @RequestParam("InitialValue")String InitialValues){
        String[] typeArray = variableType.split(",");
        String[] nameArray = variableNames.split(",");
        String[] isStaticArray = isStatics.split(",");
        String[] initialValueArray = InitialValues.split(",");
        int count = typeArray.length;
        for(int i=0;i<count;i++){
            federateVariable federateVariable=new federateVariable();
            federateVariable.setVariableType(typeArray[i]);
            federateVariable.setVariableName(nameArray[i]);
            federateVariable.setIsStatic(Integer.parseInt(isStaticArray[i]));
            federateVariable.setInitialValue(initialValueArray[i]);
            federateVariableMapper.insert(federateVariable);
        }
        return "success";
    }
}
