package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionListGet {
    private String id;
    private String listName;
    private String objectName;
    private int getType;
    private String fillInformation;
    private String outName;
    private String accordingParameter;
    private int indent;
}
