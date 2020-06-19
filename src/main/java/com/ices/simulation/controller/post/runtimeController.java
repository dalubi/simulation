package com.ices.simulation.controller.post;

import com.ices.simulation.dao.mapper.generateDaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class runtimeController {

    @Autowired
    generateDaoMapper generateDaoMapper;

    @Autowired
    com.ices.simulation.dao.mapper.taskMapper taskMapper;

    @RequestMapping("/post/runtime")
    @ResponseBody
    public String runtime(@RequestParam(name = "runtime")String runtime){
        //generateDaoMapper.insertRuntime(Integer.parseInt(runtime));
        return "已设置仿真运行时间为"+runtime+"天";
    }

}
