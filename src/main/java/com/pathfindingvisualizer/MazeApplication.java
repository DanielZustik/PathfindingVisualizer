package com.pathfindingvisualizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MazeApplication extends Application {
    private PathfindingAlgorithm pathfindingAlgorithm = new Dijkstra(); //provizorne.. pozdeji udelat samostatnou funcki pro vybrani alg.
    public static final int GRID_SIZE = 100;
    public static final int CELL_SIZE = 5; // Adjust the size of each cell
    public Rectangle[][] gridRectanglesView = new Rectangle[GRID_SIZE][GRID_SIZE]; // Store references to rectangles
    int[][] HelperGridNodeIDsAndObctlicleAsMinusOne = new int[GRID_SIZE][GRID_SIZE];
    private WeightedNode [][] nodesGrid = new WeightedNode[GRID_SIZE][GRID_SIZE];

//    private int obsticleNodeID = 0;
//    private int graphNodeID = 0;
    ArrayList<WeightedNode> obsticleNodes = new ArrayList<>();
    ArrayList<WeightedNode> graphNodes = new ArrayList<>();
//    HashMap<Integer, WeightedNode> nodeMapIDLookUp = new HashMap<>();
//    HashMap<Integer,Rectangle> rectangleMapIDLookUp = new HashMap<>();
    HashMap<WeightedNode,Rectangle> mappingNodesToRectangles = new HashMap<>();



    public MazeApplication() {
        super();
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("maze-view.fxml"));
        Parent root = loader.load();

        GridPane gridPane = new GridPane();
        gridPane.gridLinesVisibleProperty().set(true);
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE, Color.WHITE);
                gridRectanglesView[i][j] = cell;
                gridPane.add(cell, j, i);
            }
        }

        Controller controller = loader.getController();
        controller.setMazeApplication(this);
        controller.getGridPlaceholder().getChildren().add(gridPane);

        Scene scene = new Scene(root);
        primaryStage.setTitle("PathfinderInMatrixMaze");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
//use later when there will be optiosn in javafx form to selcet alg., now its initialized when declared
//    public void setPathfindingAlgorithm(PathfindingAlgorithm pathfindingAlgorithm) {
//        this.pathfindingAlgorithm = pathfindingAlgorithm;
//    }

    public PathfindingAlgorithm getPathfindingAlgorithm() {
        return pathfindingAlgorithm;
    }

    public void startAlgorithmProcess() {
        pathfindingAlgorithm.initialize(graphNodes);
        pathfindingAlgorithm.findPath();

    }


    public void changeRectangleColorAndPopulateObsticleNodeList(int row, int col, Color color) {
        if ((row > 5 || col > 5)) { //leaving empty space without obsticles for starting point
            Rectangle rectangle = gridRectanglesView[row][col];
            rectangle.setFill(color);
            WeightedNode w = new WeightedNode(row, col);
            w.obsticle = true;
            nodesGrid[row][col] = w;
            w.distance = Integer.MAX_VALUE; //obsolete?
            obsticleNodes.add(w);
//            obsticleNodeID++; //obsolete?
            // HelperGridNodeIDsAndObctlicleAsMinusOne[row][col] = -1;
            //System.out.print(obsticleNodeID + " ");
        }
    }

    public void creatingGraphNodesGraphAndEdges() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (nodesGrid[i][j] == null) {
//                    HelperGridNodeIDsAndObctlicleAsMinusOne[i][j] = graphNodeID; //obsolete

                    WeightedNode newNode = new WeightedNode(i, j);
                    nodesGrid[i][j] = newNode;
                    graphNodes.add(newNode);
                    mappingNodesToRectangles.put(nodesGrid[i][j], gridRectanglesView[i][j]);

//                    nodeMapIDLookUp.put(graphNodeID, newNode); //obsolete
//                    rectangleMapIDLookUp.put(graphNodeID, gridRectanglesView[i][j]); //obsolete
//                    graphNodeID++; //obsolete
                    System.out.print(nodesGrid[i][j].index0 + " " + nodesGrid[i][j].index1 + ", ");
                }
            }
        }
        addingEdgesToGraphNodes();
    }

    public void addingEdgesToGraphNodes() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (nodesGrid[i][j] != null) {
                    if (i < GRID_SIZE - 1 && nodesGrid[i + 1][j] != null) { //-1 is obsticle
                        nodesGrid[i][j].neighbors.add(nodesGrid[i + 1][j]);
                        nodesGrid[i][j].weightMap.put(nodesGrid[i + 1][j], 1);
                    }
                    if (j < GRID_SIZE - 1 && nodesGrid[i][j + 1] != null) {
                        nodesGrid[i][j].neighbors.add(nodesGrid[i][j + 1]);
                        nodesGrid[i][j].weightMap.put(nodesGrid[i][j + 1], 1);//1 weight for every node
                    }
                    if (i > 0 && nodesGrid[i - 1][j] != null) {
                        nodesGrid[i][j].neighbors.add(nodesGrid[i - 1][j]);
                        nodesGrid[i][j].weightMap.put(nodesGrid[i - 1][j], 1);//1 weight for every node
                    }
                    if (j > 0 && nodesGrid[i][j - 1] != null) {
                        nodesGrid[i][j].neighbors.add(nodesGrid[i][j - 1]);
                        nodesGrid[i][j].weightMap.put(nodesGrid[i][j - 1], 1);//1 weight for every node
                    }
                }
            }
        }
    }

    public void createEndNode () {
        Random rnd = new Random();
        int halfOfGridSize = GRID_SIZE / 2;
        int i = rnd.nextInt(halfOfGridSize) + halfOfGridSize - 1;
        int j = rnd.nextInt(GRID_SIZE - 1);
        Rectangle endPointRectangle = gridRectanglesView[i][j];
        WeightedNode endPointWeightedNode = nodesGrid[i][j];
        if (endPointWeightedNode.obsticle) {
            createEndNode();}
        else {
            System.out.println("Trying to set end node at grid position: (" + i + ", " + j + ")");
            endPointRectangle.setFill(Color.BLUE);
            endPointWeightedNode.endNode = true;
        }
        }
    }

//    public HashMap<Integer, WeightedNode> getNodeMapIDLookUp() {
//        return nodeMapIDLookUp;
//    }
