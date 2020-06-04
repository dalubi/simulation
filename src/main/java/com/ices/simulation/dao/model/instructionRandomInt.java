package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionRandomInt {
    private int randomId;
    private int fromInt;
    private int toInt;
    private String outName;
    private int indent;
}
