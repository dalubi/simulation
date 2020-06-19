package com.ices.simulation.service.taskInfoService.pojo;

import lombok.Data;

@Data
public class taskInfo {
    private int instructionCount;
    private int instructionId;
    private String instructionName;
    private String instructionDesc;
    private String buttonValue;
}
