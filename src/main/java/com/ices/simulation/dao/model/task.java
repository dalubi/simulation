package com.ices.simulation.dao.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class task implements Serializable {
    private int taskId;
    private String InstructionSequence;
    private String InstructionIds;
    private int nextTaskId;
}
