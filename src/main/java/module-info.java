module com.example.pathfindingvisualizer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pathfindingvisualizer to javafx.fxml;
    exports com.example.pathfindingvisualizer;
}