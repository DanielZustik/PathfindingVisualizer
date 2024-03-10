package com.pathfindingvisualizer;

import java.util.ArrayList;
import java.util.HashMap;

public class WeightedNode implements Comparable<WeightedNode>{
    public int id;
    public int index0;
    public int index1;
    public ArrayList<WeightedNode> neighbors = new ArrayList<>();
    public HashMap<WeightedNode, Integer> weightMap = new HashMap<>();
    public WeightedNode parent;
    public int distance;
    public boolean endNode;


    public WeightedNode(int id, int index0, int index1) {
        this.id = id;
        distance = Integer.MAX_VALUE;
        this.index0 = index0;
        this.index1 = index1;
        endNode = false;
    }


    @Override
    public int compareTo(WeightedNode o) {
        return this.distance - o.distance; //how it works in prior queue?
    }
}
