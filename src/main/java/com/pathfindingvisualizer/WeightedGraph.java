    package com.pathfindingvisualizer;

    import java.util.ArrayList;
    import java.util.PriorityQueue;

    public class WeightedGraph {
        public ArrayList<WeightedNode> nodeList = new ArrayList<>();
        public boolean[][] isNode = new boolean[][]{};

        public WeightedGraph (ArrayList<WeightedNode> nodeList) {
            this.nodeList = nodeList;
        }

        public void dijkstra (WeightedNode node) {
            PriorityQueue<WeightedNode> queue = new PriorityQueue<>();
            node.distance = 0;
            queue.addAll(nodeList);
            while (!queue.isEmpty()) {
                WeightedNode currentNode = queue.remove(); //vyrazeni s prvku s minimalni hodnotou distance
                for (WeightedNode neighbor : currentNode.neighbors) {
                    if (queue.contains(neighbor)) { //jsou li nenavstivene
                        if (neighbor.distance > currentNode.distance + currentNode.weightMap.get(neighbor)) {
                            neighbor.distance = currentNode.distance + currentNode.weightMap.get(neighbor);
                            neighbor.parent = currentNode;
                            queue.remove(neighbor);
                            queue.add(neighbor); //pouze za ucelem refreshe queue, jinak by pocital s puvodni hodnotou dist.
                        }
                    }
                }
            }

            for (WeightedNode nodeToCheck : nodeList) {
                System.out.print("Node "+nodeToCheck+", distance: "+nodeToCheck.distance+", Path: ");
                pathPrint(nodeToCheck);
                System.out.println();
            }
        }

        public void addWeightedEdge (int i, int j, int d) {
            WeightedNode first = nodeList.get(i);
            WeightedNode second = nodeList.get(j);
            first.neighbors.add(second);
            first.weightMap.put(second, d);
        }

        public static void pathPrint(WeightedNode node) {
            if (node.parent != null) {
                pathPrint(node.parent);
            }
            System.out.print(node.name + " ");
        }
    }
