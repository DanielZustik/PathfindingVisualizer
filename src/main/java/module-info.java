module com.example.pathfindingvisualizer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.pathfindingvisualizer to javafx.fxml;
    exports com.pathfindingvisualizer;
}