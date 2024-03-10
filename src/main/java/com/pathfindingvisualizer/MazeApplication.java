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

    public static final int GRID_SIZE = 100;
    public static final int CELL_SIZE = 5; // Adjust the size of each cell
    private Rectangle[][] gridRectangles = new Rectangle[GRID_SIZE][GRID_SIZE]; // Store references to rectangles
    private int obsticleNodeID = 0;
    private int graphNodeID = 0;
    ArrayList<WeightedNode> obsticleNodes = new ArrayList<>();
    ArrayList<WeightedNode> graphNodes = new ArrayList<>();
    HashMap<Integer, WeightedNode> nodeMapIDLookUp = new HashMap<>();
    HashMap<Integer,Rectangle> rectangleMapIDLookUp = new HashMap<>();
    Dijkstra d;
    int[][] grid = new int[GRID_SIZE][GRID_SIZE];


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
                gridRectangles[i][j] = cell;
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

    public void changeRectangleColorAndPopulateObsticleNodeList(int row, int col, Color color) {
        if ((row > 5 || col > 5)) { //leaving empty space without obsticles for starting point
            Rectangle rectangle = gridRectangles[row][col];
            rectangle.setFill(color);
            WeightedNode w = new WeightedNode(obsticleNodeID, row, col);
            w.distance = Integer.MAX_VALUE;
            obsticleNodes.add(w);
            obsticleNodeID++;
            grid[row][col] = -1;
            //System.out.print(obsticleNodeID + " ");
        }
    }

    public void creatingGraphNodesGraphAndEdges() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] != -1) {
                    grid[i][j] = graphNodeID;
                    WeightedNode newNode = new WeightedNode(graphNodeID, i, j);
                    graphNodes.add(newNode);
                    nodeMapIDLookUp.put(graphNodeID, newNode);
                    rectangleMapIDLookUp.put(graphNodeID, gridRectangles[i][j]);
                    graphNodeID++;
                    System.out.print(graphNodeID + " ");
                }
            }
        }
        generateDijkstra();
        addingEdgesToGraphNodes();
    }

    public void generateDijkstra() {
        d = new Dijkstra(graphNodes, this);
    }

    public void addingEdgesToGraphNodes() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] != -1) {
                    if (i < GRID_SIZE - 1 && grid[i + 1][j] != -1) { //-1 is obsticle
                        nodeMapIDLookUp.get(grid[i][j]).neighbors.add(nodeMapIDLookUp.get(grid[i + 1][j]));
                        nodeMapIDLookUp.get(grid[i][j]).weightMap.put(nodeMapIDLookUp.get(grid[i + 1][j]), 1);
                    }
                    if (j < GRID_SIZE - 1 && grid[i][j + 1] != -1) {
                        nodeMapIDLookUp.get(grid[i][j]).neighbors.add(nodeMapIDLookUp.get(grid[i][j + 1]));
                        nodeMapIDLookUp.get(grid[i][j]).weightMap.put(nodeMapIDLookUp.get(grid[i][j + 1]), 1);//1 weight for every node
                    }
                    if (i > 0 && grid[i - 1][j] != -1) {
                        nodeMapIDLookUp.get(grid[i][j]).neighbors.add(nodeMapIDLookUp.get(grid[i - 1][j]));
                        nodeMapIDLookUp.get(grid[i][j]).weightMap.put(nodeMapIDLookUp.get(grid[i - 1][j]), 1);//1 weight for every node
                    }
                    if (j > 0 && grid[i][j - 1] != -1) {
                        nodeMapIDLookUp.get(grid[i][j]).neighbors.add(nodeMapIDLookUp.get(grid[i][j - 1]));
                        nodeMapIDLookUp.get(grid[i][j]).weightMap.put(nodeMapIDLookUp.get(grid[i][j - 1]), 1);//1 weight for every node
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
        Rectangle endPointRectangle = gridRectangles[i][j];
        WeightedNode endPointWeightedNode = nodeMapIDLookUp.get(grid[i][j]);
        System.out.println("Trying to set end node at grid position: (" + i + ", " + j + ")");
        if (nodeMapIDLookUp.get(grid[i][j]) == null) {
            createEndNode();
        } else {
            endPointRectangle.setFill(Color.BLUE);
            endPointWeightedNode.endNode = true;
        }
    }

    public HashMap<Integer, WeightedNode> getNodeMapIDLookUp() {
        return nodeMapIDLookUp;
    }
}