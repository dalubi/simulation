package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class federate {
    private int federateId;
    private String federateName;
    private String publishInteractionIds;
    private String subscribeInteractionIds;
}
