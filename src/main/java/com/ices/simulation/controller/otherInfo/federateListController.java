package com.ices.simulation.controller.otherInfo;

import com.ices.simulation.dao.mapper.federateListMapper;
import com.ices.simulation.dao.model.federateList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class federateListController {

    @Autowired
    federateListMapper federateListMapper;

    @RequestMapping(value = "/post/federatelist", method = RequestMethod.POST)
    @ResponseBody
    public String postFederatelist(@RequestParam("objectName") String objectName,
                                    @RequestParam("listName") String listName){
        String[] objectNames = objectName.split(",");
        String[] listNames = listName.split(",");
        int count=objectNames.length;
        for(int i=0;i<count;++i){
            federateList federatelist=new federateList();
            federatelist.setObjectName(objectNames[i]);
            federatelist.setListName(listNames[i]);
            federateListMapper.insert(federatelist);
        }
        return "联邦设计中所需的队列上传完成";
    }

}