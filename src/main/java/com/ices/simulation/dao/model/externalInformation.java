package com.ices.simulation.dao.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class externalInformation implements Serializable {
    private int id;
    //InstructionSend Id
    private String eventsTaskIds;
    private String Probability;


}
