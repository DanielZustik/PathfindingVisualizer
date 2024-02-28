package com.pathfindingvisualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Controller {

    private MazeApplication mazeApplication;
    private WeightedGraph weightedGraph;
    private ArrayList<WeightedNode> nodeList = new ArrayList<>();

    @FXML
    private ToggleGroup algGroup = new ToggleGroup();
    @FXML
    private Pane gridPlaceholder;
    @FXML
    private Button generateBtn;
    @FXML
    private RadioButton bfsRadioButton;
    @FXML
    private RadioButton dfsRadioButton;
    @FXML
    private RadioButton djikstraRadioButton;
    @FXML
    private RadioButton aStarRadioButton;

    @FXML
    private void initialize() {
        bfsRadioButton.setToggleGroup(algGroup);
        dfsRadioButton.setToggleGroup(algGroup);
        djikstraRadioButton.setToggleGroup(algGroup);
        aStarRadioButton.setToggleGroup(algGroup);
    }

    public Pane getGridPlaceholder() {
        return gridPlaceholder;
    }
    public void setMazeApplication(MazeApplication mazeApplication) {
        this.mazeApplication = mazeApplication;
    }

    @FXML
    private void handleGenerateBtnAction() {
        Random rdn = new Random();
        //Color[] colors = new Color[] {Color.GRAY,Color.ALICEBLUE, Color.BEIGE, Color.AZURE, Color.BISQUE, Color.FORESTGREEN};
        Timeline timeline = new Timeline();
        for (int i = 0; i < 1000; i++) {
            final int delay = i * 1; // Increment delay for each rectangle
            int rnd1 = rdn.nextInt(mazeApplication.GRID_SIZE);
            int rnd2 = rdn.nextInt(mazeApplication.GRID_SIZE);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), e -> {
                mazeApplication.changeRectangleColor(rnd1, rnd2, Color.GRAY);
            }));
            weightedGraph.nodeListController.add(new WeightedNode(rnd1, rnd1));
        }
        timeline.play();
        addNodesToWeightedGraph();
    }

    @FXML
    private void handleStartBtnAction() {
        WeightedGraph g = new WeightedGraph(;
        Timeline timeline = new Timeline();
        for (int i = 0; i < 1000; i++) {
            final int delay = i * 1; // Increment delay for each rectangle
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), e -> {

                mazeApplication.changeRectangleColor(rdn.nextInt(mazeApplication.GRID_SIZE),
                        rdn.nextInt(mazeApplication.GRID_SIZE), colors[rdn.nextInt(5)]);
            }));
        }
        timeline.play();
    }

    private void addNodesToWeightedGraph () {
        Rectangle[][] rectangles = mazeApplication.getGridRectangles();
        for (int i = 0; i < mazeApplication.GRID_SIZE; i++) {
            for (int j = 0; j < mazeApplication.GRID_SIZE; j++) {
                if (!rectangles[i][j].getFill().equals(Color.GRAY)) {
                    weightedGraph.isNode[i][j] = true; //pomocny grid
                    nodeList.add(new WeightedNode(i,j));
                }
            }
        }
    }

    private void addWeightedEdges () {
        for (int i = 0; i < mazeApplication.GRID_SIZE - 1; i++) {
            for (int j = 0; j < mazeApplication.GRID_SIZE - 1; j++) {
                if (weightedGraph.isNode[i][j]) {
                    if (weightedGraph.isNode[i+1][j]) {
                        nodeList.contains(node i j)
                    }
                    else if (weightedGraph.isNode[i][j+1]) {

                    }
                    else if (weightedGraph.isNode[i+1][j+1]) {

                    }
                }
            }
        }
    }
}
