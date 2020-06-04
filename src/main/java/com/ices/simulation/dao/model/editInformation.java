package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class editInformation {
    private int editId;
    private int taskId;
    private int fromFederateId;
    private int fromInteractionId;
}
