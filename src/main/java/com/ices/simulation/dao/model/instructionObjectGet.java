package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionObjectGet {
    private int objectGetId;
    private String objectName;
    private String outType;
    private String parameterName;
    private String outName;
    private int indent;
}
