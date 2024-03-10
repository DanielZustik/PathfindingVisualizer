package com.pathfindingvisualizer;

import java.util.List;

public interface PathfindingAlgorithm {
    void initialize(MazeApplication mazeApplication, List<WeightedNode> nodes);
    void findPath();
    List<WeightedNode>getPath();
}
