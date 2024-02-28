package com.pathfindingvisualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
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
    private void handleGenerateBtnAction() {
        Random rdn = new Random();
        Timeline timeline = new Timeline();
        for (int i = 0; i < 1000; i++) {
            final int delay = i * 1; // Increment delay for each rectangle
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), e -> {
                mazeApplication.changeRectangleColorAndPopulateNodeList(rdn.nextInt(mazeApplication.GRID_SIZE),
                        rdn.nextInt(mazeApplication.GRID_SIZE), Color.GRAY);
            }));
        }
        timeline.play();
        mazeApplication.generateWeightedGraph();
    }
}