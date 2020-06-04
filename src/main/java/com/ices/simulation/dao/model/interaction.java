package com.ices.simulation.dao.model;



import lombok.Data;

import java.io.Serializable;

/*
接收A-发B或C数据库里没有逻辑，必须流程图
 */
@Data
public class interaction implements Serializable {
    private int interactionId;
    private String interactionName;
    private String containParameterIds;
}
