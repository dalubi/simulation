package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionObjectSet {
    private int objectSetId;
    private String objectName;
    private int setType;
    private String setName;
    private String parameterName;
    private int indent;
}
