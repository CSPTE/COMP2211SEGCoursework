package com.example.segproject.scenes;

import com.example.segproject.Calculations;
import com.example.segproject.components.*;
import com.example.segproject.SceneController;
import com.example.segproject.scenes.SideScene;
import java.util.stream.Stream;
import java.util.Iterator;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;

/**
 * Class that builds and determines the behaviour of the side view
 */
public class SimulScene extends BaseScene {

    private Rectangle runway;
    private ImageView vector;
	private SideScene side;
	private TopScene top;

    public SimulScene(SceneController controller) {
            super(controller);

        }

        /**
         * Builds the ui elements of the scene
         */
        public void build () {
            setupDefaultScene();
			inputs.setOnButtonClicked(this::newValues);
			HBox hbox = new HBox();
			side = new SideScene(controller);
			top = new TopScene(controller);
			Pane stackPaneLeft = new Pane();
			Pane stackPaneRight = new Pane();
			side.setupDefaultScene();
			top.setupDefaultScene();
			top.build();
			Pane topScene = top.getRunwayPane();
			Pane sideScene = side.getSideScenePane();
			runwayPane.getChildren().add(hbox);
			Iterator<Node> it = topScene.getChildren().iterator();
			ArrayList<Double> dbl = new ArrayList<>();
			Polygon clearedAndGradedArea = new Polygon();


			while(it.hasNext()) {
				Node node = it.next();
				if (node.getClass().getName().equals("javafx.scene.shape.Polygon")) {
					Object[] points = ((Polygon)node).getPoints().toArray();
					for (Object o : points) dbl.add(Double.parseDouble(o.toString()) / 2);
					((Polygon)node).getPoints().clear();
					((Polygon)node).getPoints().addAll(dbl);
					((Polygon)node).setLayoutY(180);
				}
				if (node.getClass().getName().equals("com.example.segproject.components.DistanceIndicator")) {
					((DistanceIndicator) node).setStartX(((DistanceIndicator) node).getStartX() / 2);
					((DistanceIndicator) node).setEndX(((DistanceIndicator) node).getEndX() / 2);
					((DistanceIndicator) node).setY(((DistanceIndicator) node).getY() - 25);
				}
				else if (node.getClass().getName().equals("javafx.scene.image.ImageView")) {
					Double width = ((ImageView)node).getFitWidth();
					Double height = ((ImageView)node).getFitHeight();
					Double x = ((ImageView)node).getLayoutX();
					Double y = ((ImageView)node).getLayoutY();
					((ImageView)node).setFitWidth(width / 2);
					((ImageView)node).setFitHeight(height / 2);
					((ImageView)node).setLayoutX(x / 2);
					((ImageView)node).setLayoutY(y + height / 4);
				}
				else if (node.getClass().getName().equals("javafx.scene.shape.Rectangle")) {
					if (((Rectangle) node).getWidth() == ((Rectangle) node).getHeight()) {
						((Rectangle) node).setHeight(((Rectangle) node).getHeight() / 2);
					}
					((Rectangle) node).setWidth(((Rectangle) node).getWidth() / 2);
					((Rectangle) node).setX(((Rectangle) node).getX() / 2);
				}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						stackPaneRight.getChildren().add(node);
					}
				});
			}

			for (Node node : sideScene.getChildren()) {
				if (node.getClass().getName().equals("javafx.scene.shape.Rectangle")) {
					if (((Rectangle) node).getWidth() == ((Rectangle) node).getHeight()) {
						((Rectangle) node).setHeight(((Rectangle) node).getHeight() / 2);
						((Rectangle) node).setY(((Rectangle) node).getY() + ((Rectangle) node).getHeight());
					}
					((Rectangle) node).setWidth(((Rectangle) node).getWidth() / 2);
					((Rectangle) node).setX(((Rectangle) node).getX() / 2);
					((Rectangle) node).setY(((Rectangle) node).getY());
				}
				else if (node.getClass().getName().equals("com.example.segproject.components.DistanceIndicator")) {
					((DistanceIndicator) node).setStartX(((DistanceIndicator) node).getStartX() / 2);
					((DistanceIndicator) node).setEndX(((DistanceIndicator) node).getEndX() / 2);
					// ((DistanceIndicator) node).setY(((DistanceIndicator) node).getY() + 10);
				}
				else if (node.getClass().getName().equals("javafx.scene.layout.Pane")) {
					var hello = (ImageView)(((Pane)node)).getChildren().get(0);
					Double sz = hello.getFitHeight();
					Double x = hello.getLayoutX();
					((ImageView)(((Pane)node)).getChildren().get(0)).setFitHeight(sz / 2);
					((ImageView)(((Pane)node)).getChildren().get(0)).setLayoutX(x / 2);
				}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						stackPaneLeft.getChildren().add(node);
					}
				});
			}
			hbox.getChildren().addAll(stackPaneLeft, stackPaneRight);
		}

		private void newValues(Calculations cal, ActionEvent event) {
			side.newValues(cal, event);
			top.newValues(cal, event);
		}

		public void changeColorScheme(String value){
			// if (value.equals("Normal")) {
				top.changeColorScheme(value);
				side.changeColorScheme(value);
			// }
			// if (value.equals("Normal")){
			// 	background.setFill(Color.GREEN);
			// 	clearedAndGradedArea.setFill(Color.BLUE);
			// 	obstacle.setFill(Color.ORANGE);
			// 	setIndicatorsToLightMode(new DistanceIndicator[]{toraIndicator, asdaIndicator, todaIndicator, ldaIndicator,
			// 			distanceFromThresholdIndicator, displacementThresholdIndicator, resaIndicator,
			// 			stripEndIndicator, blastProtectionIndicator, slopeCalculationIndicator});
			// }
			// if(value.equals("Dark")){
			// 	background.setFill(Color.web("0x191919"));
			// 	clearedAndGradedArea.setFill(Color.web("0x2D4263"));
			// 	obstacle.setFill(Color.web("0xC84B31"));
			// 	setIndicatorsToDarkMode(new DistanceIndicator[]{toraIndicator, asdaIndicator, todaIndicator, ldaIndicator,
			// 			distanceFromThresholdIndicator, displacementThresholdIndicator, resaIndicator,
			// 			stripEndIndicator, blastProtectionIndicator, slopeCalculationIndicator});
			// }
		}

}