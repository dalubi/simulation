package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class federateVariable {
    private int variableId;
    private String variableType;
    private String variableName;
    private int isStatic;
    private String InitialValue;
}
