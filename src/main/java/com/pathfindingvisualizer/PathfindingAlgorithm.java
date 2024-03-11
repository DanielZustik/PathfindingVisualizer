package com.pathfindingvisualizer;

import java.util.List;

public interface PathfindingAlgorithm {
    void initialize(List<WeightedNode> nodes);
    void findPath();
    List<WeightedNode>getPath();
    List<AlgorithmStep> getSteps();
}
