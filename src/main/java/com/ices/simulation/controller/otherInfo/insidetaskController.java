package com.ices.simulation.controller.otherInfo;

import com.ices.simulation.dao.mapper.insidetaskMapper;
import com.ices.simulation.dao.model.insideTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class insidetaskController {
//    @Autowired
//    insidetaskMapper insidetaskMapper;
//
//    @GetMapping("/insideTask")
//    public String insideTask(){
//        return "insideTask";
//    }
//
//    @RequestMapping(value = "/post/insidetask", method = RequestMethod.POST)
//    @ResponseBody
//    public String postinsidetask(@RequestParam("insidetaskId") String insidetaskId,
//                                 @RequestParam("information") String information,
//                                   @RequestParam("listName") String iscomplete){
//        String[] insidetaskIds = insidetaskId.split(",");
//        String[] informations = information.split(",");
//        String[] iscompletes = iscomplete.split(",");
//        int count=insidetaskIds.length;
//        for(int i=0;i<count;++i){
//            insideTask insidetask=new insideTask();
//            insidetask.setInsidetaskId(Integer.parseInt(insidetaskIds[i]));
//            insidetask.setInformation(informations[i]);
//            insidetask.setIscomplete(iscompletes[i]);
//            insidetaskMapper.insert(insidetask);
//        }
//        return "success";
//    }

}
