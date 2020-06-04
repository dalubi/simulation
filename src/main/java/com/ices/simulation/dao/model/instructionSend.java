package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionSend {
    private int sendId;
    private int sendInteractionId;
    private String sendTypes;
    private String sendInformation;
    private int indent;
}
