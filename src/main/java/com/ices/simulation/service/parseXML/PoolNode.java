package com.ices.simulation.service.parseXML;

import java.util.ArrayList;
import java.util.List;

public class PoolNode {
    private String processId;
    private String pollName;
    //不包含sequenceFlow节点数
    private int poolNodeNumber;
    private boolean hasStartEvent;
    //池中的节点
    private List<GraphNode> poolGraphNodes;

    //池孤立节点list
    private List<GraphNode> poolUnVisitedNodes;


    public List<GraphNode> getPoolUnVisitedNodes() {
        return poolUnVisitedNodes;
    }

    public void setPoolUnVisitedNodes(List<GraphNode> poolUnVisitedNodes) {
        this.poolUnVisitedNodes = poolUnVisitedNodes;
    }


    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    @Override
    public String toString() {
        return "PoolNode{" +
                "processId='" + processId + '\'' +
                ", poolNodeNumber=" + poolNodeNumber +
                ", hasStartEvent=" + hasStartEvent +
                '}';
    }

    public PoolNode(String processId) {
        this.processId = processId;
        poolGraphNodes=new ArrayList<>();
        hasStartEvent=false;
        poolUnVisitedNodes=new ArrayList<>();
    }

    public List<GraphNode> getPoolGraphNodes() {
        return poolGraphNodes;
    }

    public void setPoolGraphNodes(List<GraphNode> poolGraphNodes) {
        this.poolGraphNodes = poolGraphNodes;
    }

    public boolean isHasStartEvent() {
        return hasStartEvent;
    }

    public void setHasStartEvent(boolean hasStartEvent) {
        this.hasStartEvent = hasStartEvent;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

//    public String getPollName() {
//        return pollName;
//    }
//
//    public void setPollName(String pollName) {
//        this.pollName = pollName;
//    }

    public int getPoolNodeNumber() {
        return poolNodeNumber;
    }

    public void setPoolNodeNumber(int poolNodeNumber) {
        this.poolNodeNumber = poolNodeNumber;
    }
}
