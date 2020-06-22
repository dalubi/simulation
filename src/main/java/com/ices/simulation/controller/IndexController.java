package com.ices.simulation.controller;

import com.ices.simulation.dao.mapper.cleanData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index()
    {
        cleanData cleanData = new cleanData();
        cleanData.cleanSimulationData();

        return "index";
    }
}
