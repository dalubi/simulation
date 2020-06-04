package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionTypeConversion {
    private int id;
    private String conversionType;
    private String information;
    private String outName;
    private int indent;
}
