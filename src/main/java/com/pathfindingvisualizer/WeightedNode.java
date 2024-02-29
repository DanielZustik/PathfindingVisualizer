package com.pathfindingvisualizer;

import java.util.ArrayList;
import java.util.HashMap;

public class WeightedNode implements Comparable<WeightedNode>{
    public int id;
    public int index0;
    public int index1;
    public ArrayList<WeightedNode> neighbors = new ArrayList<>();
    public HashMap<WeightedNode, Integer> weightMap = new HashMap<>();
    public boolean isVisited;
    public WeightedNode parent;
    public int distance;



    public WeightedNode(int id, int index0, int index1) {
        this.id = id;
        distance = 100000;
        this.index0 = index0;
        this.index1 = index1;

    }


    @Override
    public int compareTo(WeightedNode o) {
        return this.distance - o.distance; //how it works in prior queue?
    }
}
