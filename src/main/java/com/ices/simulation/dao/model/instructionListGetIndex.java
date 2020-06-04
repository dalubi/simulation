package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionListGetIndex {
    private int id;
    private String listName;
    private String objectName;
    private String fillInformation;
    private String outName;
    private int indent;
}
