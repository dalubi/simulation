package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionExpression {
    private int expressId;
    private String expressionInformation;
    private String fillType;
    private String fillInformation;
    private String outType;
    private String outName;
    private int indent;
}
