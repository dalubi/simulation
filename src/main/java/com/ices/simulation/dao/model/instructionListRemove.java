package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionListRemove {
    private int removeId;
    private int listId;
    private String accordingToParameterName;
    private int removeType;
    private String removeName;
    private int indent;
}
