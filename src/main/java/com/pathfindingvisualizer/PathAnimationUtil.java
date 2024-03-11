package com.pathfindingvisualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

public class PathAnimationUtil {
    public static void animatePath(MazeApplication mazeApplication, List<WeightedNode> path) {
        Timeline timeline = new Timeline();
        for (int i = 0; i < path.size(); i++) {
            WeightedNode node = path.get(i);
            int delay = i * 10;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), e -> {
                Rectangle rect = mazeApplication.gridRectanglesView[node.index0][node.index1];
                rect.setFill(Color.BLUE);
            }));
        }
        timeline.play();
    }
}