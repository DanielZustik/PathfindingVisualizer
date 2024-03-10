    package com.pathfindingvisualizer;

    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.scene.paint.Color;
    import javafx.util.Duration;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.PriorityQueue;

    public class Dijkstra implements PathfindingAlgorithm{
        private List<WeightedNode> nodeList;
        private MazeApplication mazeApplication;


        public Dijkstra(ArrayList<WeightedNode> nodeList, MazeApplication mazeApplication) {

            this.nodeList = nodeList;
            this.mazeApplication = mazeApplication;

        }


        public void launch () {
            Timeline timeline = new Timeline();
            int delay = 0;

            PriorityQueue<WeightedNode> queue = new PriorityQueue<>();
            nodeList.getFirst().distance = 0;
            queue.addAll(nodeList);
            while (!queue.isEmpty()) {
                WeightedNode currentNode = queue.remove(); //vyrazeni s prvku s minimalni hodnotou distance
                if (currentNode.endNode) {
                    timeline.play();
                    timeline.setOnFinished(e -> {
                        drawShortestPath(currentNode);
                    });
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

//        public void addWeightedEdge (int i, int j, int d) {
//            WeightedNode first = nodeList.get(i);
//            WeightedNode second = nodeList.get(j);
//            first.neighbors.add(second);
//            first.weightMap.put(second, d);
//        }

//        public static void pathPrint(WeightedNode node) {
//            if (node.parent != null) {
//                pathPrint(node.parent);
//            }
//            System.out.print(node.id + " ");
//        }

        public void creatingFrame (Timeline timeline, MazeApplication mazeApplication, WeightedNode currentNode,
                                   int delay) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), e -> {
                    mazeApplication.rectangleMapIDLookUp.get(currentNode.id).setFill(Color.RED);
                }));
        }

        public void drawShortestPath (WeightedNode currentNode) {
            mazeApplication.rectangleMapIDLookUp.get(currentNode.id).setFill(Color.GREEN);
            if (currentNode.parent != null) {
                drawShortestPath(currentNode.parent);
            }
        }

        @Override
        public void initialize(MazeApplication mazeApplication, List<WeightedNode> nodes) {

        }

        @Override
        public void findPath() {

        }

        @Override
        public List<WeightedNode> getPath() {
            return null;
        }
    }
