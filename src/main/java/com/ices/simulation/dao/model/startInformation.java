package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class startInformation {
    private int federateId;
    private int isFirst;
    private int firstTask;
    private int initialInstanceTask;
    private int updateInstanceTask;
    private String listIds;
    private String variableIds;
}
