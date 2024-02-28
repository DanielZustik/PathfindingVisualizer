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

public class MazeApplication extends Application { // Make class public

    public static final int GRID_SIZE = 100;
    public static final int CELL_SIZE = 5; // Adjust the size of each cell
    private Rectangle[][] gridRectangles = new Rectangle[GRID_SIZE][GRID_SIZE]; // Store references to rectangles
    private int nodesID = 0;
    ArrayList<WeightedNode> nodeList = new ArrayList<>();
    WeightedGraph g;




    public MazeApplication() {
        super();
    }
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start (Stage primaryStage)  throws Exception {
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

    public void changeRectangleColorAndPopulateNodeList(int row, int col, Color color) {
        if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
            Rectangle rectangle = gridRectangles[row][col];
            rectangle.setFill(color);
            nodeList.add(new WeightedNode(nodesID, row, col));
            nodesID++;
            System.out.print(nodesID + " ");
        }
    }

    public void generateWeightedGraph () {
        g = new WeightedGraph(nodeList);
    }

}