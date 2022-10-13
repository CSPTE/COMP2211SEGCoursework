package com.example.segproject;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Class that instantiates the application
 */
public class App extends Application {

    private int resH;
    private int resV;

    private Stage stage;

    /**
     * Generates the scene controller which then runs the rest of the application
     * @param stage the application window
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        var screenSize = Screen.getPrimary().getBounds();
        resH = (int)screenSize.getWidth() / 10 * 8 + 220;
        resV = (int)screenSize.getHeight() / 10 * 8;
        var controller = new SceneController(stage, resH, resV, "Runway Redeclaration");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void shutdown() {
        System.exit(0);
    }
}