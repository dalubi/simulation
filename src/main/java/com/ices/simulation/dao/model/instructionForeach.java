package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionForeach {
    private int id;
    private String listName;
    private String objectName;
    private String outName;
    private int insideTaskId;
    private int indent;
}
