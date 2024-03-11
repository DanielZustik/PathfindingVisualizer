package com.pathfindingvisualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;
import java.util.Timer;

public class AlgorithmProcessAnimationUtil {
    public static void animateAlgorithmSteps(MazeApplication mazeApplication, List<AlgorithmStep> steps) {
        Timeline timeline = new Timeline();
        for (int i = 0; i < steps.size(); i++) {
            AlgorithmStep step = steps.get(i);
            int delay = i * 1;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), e -> {
                Rectangle rectangle = mazeApplication.mappingNodesToRectangles.get(step.getNode());
                if (step.getActionType() == AlgorithmStep.ActionType.VISIT) {
                    rectangle.setFill(Color.RED);
                } else if (step.getActionType() == AlgorithmStep.ActionType.EXPLORE) {
                    rectangle.setFill(Color.ORANGE);
                }
            }));
        }
        timeline.play();
    }
}
