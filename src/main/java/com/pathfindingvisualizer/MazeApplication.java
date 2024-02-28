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

public class MazeApplication extends Application { // Make class public

    public static final int GRID_SIZE = 100;
    public static final int CELL_SIZE = 5; // Adjust the size of each cell
    private Rectangle[][] gridRectangles = new Rectangle[GRID_SIZE][GRID_SIZE]; // Store references to rectangles
    private int obsticleNodeID = 0;
    private int graphNodeID = 0;

    ArrayList<WeightedNode> obsticleNodes = new ArrayList<>();
    ArrayList<WeightedNode> graphNodes = new ArrayList<>();
    HashMap<Integer, WeightedNode> nodeMapIDLookUp = new HashMap<>();


    WeightedGraph g;
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
        if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
            Rectangle rectangle = gridRectangles[row][col];
            rectangle.setFill(color);
            obsticleNodes.add(new WeightedNode(obsticleNodeID, row, col));
            obsticleNodeID++;
            grid[row][col] = -1;
            //System.out.print(obsticleNodeID + " ");
        }
    }

    public void creatingGraphNodes() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] != -1) {
                    grid[i][j] = graphNodeID;
                    WeightedNode newNode = new WeightedNode(graphNodeID, i, j);
                    graphNodes.add(newNode);
                    nodeMapIDLookUp.put(graphNodeID, newNode);
                    graphNodeID++;
                    System.out.print(graphNodeID + " ");
                }
            }
        }
        generateWeightedGraph();
        addingEdgesToGraphNodes();
    }

    public void generateWeightedGraph() {
        g = new WeightedGraph(graphNodes);
    }

    public void addingEdgesToGraphNodes() {
        for (int i = 0; i < GRID_SIZE - 1; i++) {
            for (int j = 0; j < GRID_SIZE - 1; j++) {
                if (grid[i][j] != -1) {
                    if (grid[i + 1][j] != -1) {
                        nodeMapIDLookUp.get(grid[i][j]).neighbors.add(nodeMapIDLookUp.get(grid[i + 1][j]));
                    } else if (grid[i][j + 1] != -1) {
                        nodeMapIDLookUp.get(grid[i][j]).neighbors.add(nodeMapIDLookUp.get(grid[i][j + 1]));
                    }
                }
            }
        }
    }
}