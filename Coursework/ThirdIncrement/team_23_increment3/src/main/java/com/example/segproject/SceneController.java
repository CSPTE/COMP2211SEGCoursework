package com.example.segproject;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import com.example.segproject.scenes.*;

/**
 * Utility class that creates the window on which all scenes are displayed
 * Provides the ability to switch between scenes
 */
public class SceneController {

    private final int resH;
    private final int resV;

    private final Stage stage;
    private final String title;
    private BaseScene currentScene;
    private Scene scene;

    /**
     * Constructor that runs on program launch
     * Builds the window to display scenes in
     * Gives control to the menu scene
     * @param stage Application window
     * @param resH Horizontal resolution
     * @param resV Vertical resolution
     * @param title Name of the window
     */
    public SceneController(Stage stage, int resH, int resV, String title) {
        this.resH = resH;
        this.resV = resV;
        this.stage = stage;
        this.title = title;

        createStage();

        this.scene = new Scene(new Pane(), this.resH, this.resV, Color.BLACK);
        stage.setScene(this.scene);

        openSideScene();
    }

    /**
     * Method called by constructor only
     * Sets the values of the application window
     * Allows the user to close the application safely
     */
    private void createStage() {
        stage.setTitle(this.title);
        stage.setMinWidth(this.resH);
        stage.setMinHeight(this.resV);
        stage.setOnCloseRequest(e -> App.shutdown());
    }

    /**
     * Transfers control over the application window from the current scene to a new scene
     * @param newScene The scene to hand control over to
     */
    private void loadScene(BaseScene newScene) {
        newScene.build();
        currentScene = newScene;
        scene = newScene.setScene();
        stage.setScene(scene);
    }

    /**
     * Method that can be called by scenes to transfer control to the Side view
     */
    public void openSideScene() {loadScene(new SideScene(this));}

    /**
     * Method that can be called by scenes to transfer control to the Top view
     */
    public void openTopScene() {loadScene(new TopScene(this));}

    /**
     * Method that can be called by scenes to transfer control to the Dual view
     */
    public void openDoubleScene() {loadScene(new SimulScene(this));}

    /**
     * Getter method for the current scene
     * @return scene The current scene
     */
    public Scene getScene() {return scene;}

    /**
     * Getter method for horizontal resolution
     * @return resH The horizontal resolution
     */
    public int getWidth() {return resH;}

    /**
     * Getter method for vertical resolution
     * @return resV The vertical resolution
     */
    public int getHeight() {return resV;}

    public Stage getStage() {return stage;}

    public BaseScene getCurrentScene() {return currentScene;}
}
