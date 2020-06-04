package com.ices.simulation.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class pathVariable {

    @Value("${file.showbpmnpath}")
    private String showbpmnpath;

    @Value("${file.processespath}")
    private String processespath;

    @Value("${file.processesfilefolder}")
    private String processesfilefolder;
}
