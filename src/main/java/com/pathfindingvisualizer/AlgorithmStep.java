package com.pathfindingvisualizer;

public class AlgorithmStep {
    public enum ActionType{
        VISIT, EXPLORE
    }

    private ActionType actionType;
    private WeightedNode node;

    public AlgorithmStep(ActionType actionType, WeightedNode node) {
        this.actionType = actionType;
        this.node = node;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public WeightedNode getNode() {
        return node;
    }
}
