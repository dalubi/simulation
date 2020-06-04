package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionDelay {
    private int delayId;
    private int delayTime;
    private String logInformation;
    private int indent;
}
