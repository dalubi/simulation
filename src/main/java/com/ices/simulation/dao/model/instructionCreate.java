package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionCreate {
    private int createId;
    private int objectId;
    private String typeInformation;
    private String actualInformation;
    private String outName;
    private int indent;
}
