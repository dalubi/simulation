package com.ices.simulation.dao.model;

import lombok.Data;

@Data
public class instructionUpdateTimePeriod {
    private int updatetimeperiodId;
    private String instanceName;
    private String activeListName;
    private String dormantListName;
}
