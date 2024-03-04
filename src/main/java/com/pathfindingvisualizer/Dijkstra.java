    package com.pathfindingvisualizer;

    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.scene.paint.Color;
    import javafx.util.Duration;

    import java.util.ArrayList;
    import java.util.PriorityQueue;

    public class Dijkstra {
        public ArrayList<WeightedNode> nodeList;

        public Dijkstra(ArrayList<WeightedNode> nodeList) {
            this.nodeList = nodeList;
        }

        public void launch (MazeApplication mazeApplication) {
            Timeline timeline = new Timeline();
            int delay = 0;

            PriorityQueue<WeightedNode> queue = new PriorityQueue<>();
            nodeList.getFirst().distance = 0;
            queue.addAll(nodeList);
            while (!queue.isEmpty()) {
                WeightedNode currentNode = queue.remove(); //vyrazeni s prvku s minimalni hodnotou distance
                if (currentNode.endNode) {
                    timeline.play();
                    return;
                }
                if (currentNode.distance != Integer.MAX_VALUE){ //for not going to nodes that are unreachable
                    creatingFrame(timeline, mazeApplication, currentNode, delay);
                    delay++;
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

//                for (WeightedNode nodeToCheck : nodeList) {
//                    System.out.print("Node "+nodeToCheck+", distance: "+nodeToCheck.distance+", Path: ");
//                    pathPrint(nodeToCheck);
//                    System.out.println();
//                }
            }
            timeline.play();
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
            System.out.print(node.id + " ");
        }

        public void creatingFrame (Timeline timeline, MazeApplication mazeApplication, WeightedNode currentNode,
                                   int delay) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), e -> {
                    mazeApplication.rectangleMapIDLookUp.get(currentNode.id).setFill(Color.RED);
                }));
        }

    }
