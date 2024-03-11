    package com.pathfindingvisualizer;

    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.fxml.FXML;
    import javafx.scene.control.Button;
    import javafx.scene.layout.Pane;
    import javafx.scene.paint.Color;
    import javafx.util.Duration;

    import java.util.List;
    import java.util.Random;

    public class Controller {
        private MazeApplication mazeApplication;
        public void setMazeApplication(MazeApplication mazeApplication) {
            this.mazeApplication = mazeApplication;
        }

        @FXML
        private Pane gridPlaceholder;

        public Pane getGridPlaceholder() {
            return gridPlaceholder;
        }

        @FXML
        private Button generateBtn;

        @FXML
        private Button startBtn;
        @FXML
        private void handleGenerateBtnAction() {
            Random rdn = new Random();
            Timeline timeline = new Timeline();
            for (int i = 0; i < 3900; i++) {
                final double delay = i * 0.2; // Increment delay for each rectangle
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), e -> {
                    mazeApplication.changeRectangleColorAndPopulateObsticleNodeList(rdn.nextInt(mazeApplication.GRID_SIZE),
                            rdn.nextInt(mazeApplication.GRID_SIZE), Color.GRAY); //bcs of using random there will be overlap

                }));
            }
            timeline.play();
            timeline.setOnFinished(e -> {
                mazeApplication.creatingGraphNodesGraphAndEdges(); //  will be called after all animations are done
                mazeApplication.createEndNode();
            });
        }

        @FXML
        private void handleStartBtnAction() {
            mazeApplication.startAlgorithmProcess();
            mazeApplication.getPathfindingAlgorithm().findPath();
            List<WeightedNode> path = mazeApplication.getPathfindingAlgorithm().getPath();
            List<AlgorithmStep> steps = mazeApplication.getPathfindingAlgorithm().getSteps();
            AlgorithmProcessAnimationUtil.animateAlgorithmSteps(mazeApplication, steps);
            PathAnimationUtil.animatePath(mazeApplication, path);
        }

//        public void creatingGraphNodesGraphAndEdges() {
//            for (int i = 0; i < mazeApplication.GRID_SIZE; i++) {
//                for (int j = 0; j < mazeApplication.GRID_SIZE; j++) {
//                    if (HelperGridNodeIDsAndObctlicleAsMinusOne[i][j] != -1) {
//                        HelperGridNodeIDsAndObctlicleAsMinusOne[i][j] = graphNodeID; //obsolete
//
//                        WeightedNode newNode = new WeightedNode(graphNodeID, i, j);
//                        nodesGrid[i][j] = newNode;
//                        graphNodes.add(newNode);
//                        mappingNodesToRectangles.put(nodesGrid[i][j], gridRectanglesView[i][j]);
//
//                        nodeMapIDLookUp.put(graphNodeID, newNode); //obsolete
//                        rectangleMapIDLookUp.put(graphNodeID, gridRectanglesView[i][j]); //obsolete
//                        graphNodeID++; //obsolete
//                        System.out.print(nodesGrid[i][j].index0 + " " + nodesGrid[i][j].index1 + ", ");
//                    }
//                }
//            }
//            addingEdgesToGraphNodes();
//            startAlgorithmProcess();
//        }
    }