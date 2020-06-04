package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionFornumber {
    private int id;
    private int number;
    private int insideTaskId;
    private int indent;
}
