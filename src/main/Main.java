package main;

import graphics.City3D;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Graph;
import ui.Dashboard;
import utils.GraphGenerator;

/**
 * Main.java
 *
 * Entry point for the 3D Smart Ambulance Route Optimizer.
 *
 * Boot sequence:
 *   1. Generate the weighted city graph (GraphGenerator)
 *   2. Build the 3D city scene             (City3D)
 *   3. Build the dashboard UI               (Dashboard)
 *   4. Show the JavaFX Stage
 *
 * Run configuration in Eclipse:
 *   - VM args (Java 11+ with separate JavaFX SDK):
 *       --module-path "<path-to-javafx-sdk>/lib" --add-modules javafx.controls,javafx.fxml
 *   - Main class: main.Main
 *
 *   (or use Java 8 with built-in JavaFX -- no VM args needed)
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // 1. Build the city graph
        Graph graph = GraphGenerator.generateCity();

        // 2. Build the 3D city
        City3D city3D = new City3D(graph, 980, 720);

        // 3. Build the dashboard wrapping it
        Dashboard dashboard = new Dashboard(graph, city3D);

        // 4. Show the scene
        Scene scene = new Scene(dashboard, 1500, 900, Color.web("#06101f"));
        stage.setTitle("3D Smart Ambulance Route Optimizer  —  Graph Algorithms Visualizer");
        stage.setScene(scene);
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
