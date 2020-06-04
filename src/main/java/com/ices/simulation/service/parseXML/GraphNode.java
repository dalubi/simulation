package com.ices.simulation.service.parseXML;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphNode {
   private String graphId;
    private boolean isVisited;
    //0 startEvent;  1 userTask;  2 exclusiveGateway;   3 endEvent;
    private int nodeState;
    //节点的name属性 只有usertask有
    private String graphName;
    //父节点 有向图
    private GraphNode parent;
    //父节点 状态图
    private GraphNode stateParent;
    //之后的节点列表  有向图中
    private List<GraphNode> neighborList;
    //属于哪个process
    private  String belongProcess;
    //之后的节点列表  状态图中
    private List<GraphNode>  stateNeighborList;
    //交互类信息
    private String interactionInformation;

    public String getInteractionInformation() {
        return interactionInformation;
    }

    public void setInteractionInformation(String interactionInformation) {
        this.interactionInformation = interactionInformation;
    }

    public String getBelongProcess() {
        return belongProcess;
    }

    public void setBelongProcess(String belongProcess) {
        this.belongProcess = belongProcess;
    }

    public GraphNode getParent() {
        return parent;
    }

    public void setParent(GraphNode parent) {
        this.parent = parent;
    }

    public GraphNode getStateParent() {
        return stateParent;
    }

    public void setStateParent(GraphNode stateParent) {
        this.stateParent = stateParent;
    }

    public List<GraphNode> getStateNeighborList() {
        return stateNeighborList;
    }

    public void setStateNeighborList(List<GraphNode> stateNeighborList) {
        this.stateNeighborList = stateNeighborList;
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "graphId='" + graphId + '\'' +
                ", isVisited=" + isVisited +
                ", nodeState=" + nodeState +
                ", graphName='" + graphName + '\'' +
                ", belongProcess='" + belongProcess + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode graphNode = (GraphNode) o;
        return Objects.equals(graphId, graphNode.graphId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graphId);
    }

    public List<GraphNode> getNeighborList() {
        return neighborList;
    }

    public void setNeighborList(List<GraphNode> neighborList) {
        this.neighborList = neighborList;
    }



    public GraphNode(String graphId) {
        this.graphId = graphId;
        neighborList=new ArrayList<>();
        isVisited=false;
        stateNeighborList=new ArrayList<>();
        nodeState=-1;
    }

    public String getGraphId() {
        return graphId;
    }

    public void setGraphId(String graphId) {
        this.graphId = graphId;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public int getNodeState() {
        return nodeState;
    }

    public void setNodeState(int nodeState) {
        this.nodeState = nodeState;
    }

    public String getGraphName() {
        return graphName;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }
}
