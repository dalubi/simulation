package com.ices.simulation.controller;


import com.ices.simulation.dao.model.instructionSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class insideSelectController {
//    @Autowired
//    insideSelectMapper insideSelectMapper;
//
//
//
//    @GetMapping("/insideSelect")
//    public String insideSelect(){
//        return "insideSelect";
//    }
//
//    @RequestMapping(value = "/post/insideselect", method = RequestMethod.POST)
//    @ResponseBody
//    public String postinsideselect(@RequestParam("id") String id,
//                                 @RequestParam("bran1Info") String bran1Info,
//                                 @RequestParam("bran2Info") String bran2Info){
//        String[] insideselectIds = id.split(",");
//        String[] bran1Infos = bran1Info.split(",");
//        String[] bran2Infos = bran2Info.split(",");
//        int count=insideselectIds.length;
//        for(int i=0;i<count;++i){
//            insideSelect insideSelect=new insideSelect();
//            insideSelect.setId(Integer.parseInt(insideselectIds[i]));
//            insideSelect.setBran1Info(bran1Infos[i]);
//            insideSelect.setBran2Info(bran2Infos[i]);
//            insideSelect.setIscomplete("未完成");
//            insideSelectMapper.insert(insideSelect);
//        }
//        return "success";
//    }
//
//    @RequestMapping(value = "/post/queryInsideSelect", method = RequestMethod.POST)
//    @ResponseBody
//    public Result getallInsideSelect(){
//        Result result = new Result();
//        List<insideSelect> allInsidetask=insideSelectMapper.queryAll();
//        if(allInsidetask != null){
//            result.setMsg("Query all succeed");
//            result.setData(allInsidetask);
//            result.setStateCode(200);
//        }
//        else{
//            result.setMsg("Query failes");
//            result.setStateCode(404);
//        }
//        return result;
//
//    }
//
//    @RequestMapping(value = "/post/updateSelect", method = RequestMethod.POST)
//    @ResponseBody
//    public String updateSelect(@RequestParam("selectId") String selectId,
//                               @RequestParam("branch1Id") String branch1Id,
//                               @RequestParam("branch2Id") String branch2Id){
//
//        String[] selectIds = selectId.split(",");
//        System.out.println(selectIds[0]);
//        String[] branch1Ids = branch1Id.split(",");
//        String[] branch2Ids = branch2Id.split(",");
//        int count=selectIds.length;
//        for(int i=0;i<count;++i){
//            insideSelect insideSelect=new insideSelect();
//            insideSelect.setId(Integer.parseInt(selectIds[i]));
//            insideSelect.setIscomplete("已完成");
//            insideSelectMapper.updateInsideSelect(insideSelect);
//            instructionSelect instructionselect=new instructionSelect();
//            instructionselect.setSelectId(Integer.parseInt(selectIds[i]));
//            instructionselect.setBranch1id(Integer.parseInt(branch1Ids[i]));
//            instructionselect.setBranch2id(Integer.parseInt(branch2Ids[i]));
//            insideSelectMapper.updateInstructionSelect(instructionselect);
//        }
//        return "success";
//    }

}
