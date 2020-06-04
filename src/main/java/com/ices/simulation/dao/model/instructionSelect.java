package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionSelect {
    private int selectId;
    private String informationName;
    private int branch1id;
    private int branch2id;
    private int indent;
}
