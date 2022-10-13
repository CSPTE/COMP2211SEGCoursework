package com.example.segproject.scenes;

import com.example.segproject.Calculations;
import com.example.segproject.components.*;
import com.example.segproject.SceneController;
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

/**
 * Class that builds and determines the behaviour of the side view
 */
public class SideScene extends BaseScene {

    private Rectangle runway;
    private ImageView vector;
    private Rectangle clearedAndGradedArea;
    private Rectangle lowerBackground;
	private Rectangle upperBackground;
	

    public SideScene(SceneController controller) {
            super(controller);

        }

        /**
         * Builds the ui elements of the scene
         */
        public void build () {
            setupDefaultScene();
			inputs.setOnButtonClicked(this::newValues);
			getSideScenePane();
			// ((ImageView)test.getChildren().get(0)).setFitHeight(50.0);
			// runwayPane.getChildren().add(test);


			// for (Node node : runwayPane.getChildren()) {
			// 	if (node.getClass().getName().equals("javafx.scene.shape.Rectangle")) {
			// 		// ((Rectangle) node).setHeight(((Rectangle) node).getHeight() / 2);
			// 		((Rectangle) node).setWidth(((Rectangle) node).getWidth() / 2);
			// 		((Rectangle) node).setX(((Rectangle) node).getX() / 2);
			// 	}
			// 	if (node.getClass().getName().equals("com.example.segproject.components.DistanceIndicator")) {
			// 		// ((DistanceIndicator) node).setWidth(((DistanceIndicator) node).getWidth() / 2);
			// 		((DistanceIndicator) node).setStartX(((DistanceIndicator) node).getStartX() / 2);
			// 		((DistanceIndicator) node).setEndX(((DistanceIndicator) node).getEndX() / 2);
			// 	}
			// 		System.out.println(node.getClass());
			// }
    }

	public Pane getSideScenePane() {
		Pane sidePane = new Pane();
		runway = new Rectangle();
		runway.setWidth(controller.getWidth() * 0.66 - 300);
		runway.setHeight(50);
		runway.setX((runwayPaneCenterX - runway.getWidth() * 0.5));
		runway.setY(runwayPaneCenterY - runway.getHeight() * 0.5);

		this.clearedAndGradedArea = new Rectangle(runway.getX() - 60, runway.getY(), runway.getWidth() + 120,
				runway.getHeight());
		this.lowerBackground = new Rectangle(0, runway.getY(), controller.getWidth() * 0.66,
				controller.getHeight());
		this.upperBackground = new Rectangle(0, 0, controller.getWidth() * 0.66,
				controller.getHeight() * 0.5 - runway.getHeight() * 0.5);

		obstacle = new Rectangle();
		obstacle.setWidth(50);
		obstacle.setHeight(50);
		obstacle.setX(runway.getX());
		obstacle.setY(runway.getY() - obstacle.getHeight());
		obstacle.setFill(Color.ORANGE);

		lowerBackground.setFill(Color.GREEN);
		upperBackground.setFill(Color.LIGHTCYAN);
		clearedAndGradedArea.setFill(Color.BLUE);
		runway.setFill(Color.DARKGRAY);

		runwayPane.getChildren().addAll(lowerBackground, upperBackground, clearedAndGradedArea, runway, obstacle);

		toraIndicator = new DistanceIndicator(runway, 0, 0, "", 0);
		asdaIndicator = new DistanceIndicator(runway, 0, 0, "", 0);
		todaIndicator = new DistanceIndicator(runway, 0, 0, "", 0);
		ldaIndicator = new DistanceIndicator(runway, 0, 0, "", 0);
		distanceFromThresholdIndicator = new DistanceIndicator(runway, 0, 0, "", 0);
		displacementThresholdIndicator = new DistanceIndicator(runway, 0, 0, "", 0);
		resaIndicator = new DistanceIndicator(runway, 0, 0, "", 0);
		stripEndIndicator = new DistanceIndicator(runway, 0, 0, "", 0);
		blastProtectionIndicator = new DistanceIndicator(runway, 0, 0, "", 0);
		slopeCalculationIndicator = new DistanceIndicator(runway, 0, 0, "", 0);

		vector = new ImageView("vectorArrow.png");
		vector.setPreserveRatio(true);
		vector.setFitHeight(100);
		vector.setLayoutX(runway.getX() + 100);
		vector.setLayoutY(runway.getY() - 200);
		vector.setVisible(false);
		// System.out.println(vector.getFitHeight());
		sidePane.getChildren().add(vector);
		runwayPane.getChildren().addAll(toraIndicator, asdaIndicator, todaIndicator, ldaIndicator,
				distanceFromThresholdIndicator, displacementThresholdIndicator, resaIndicator,
				stripEndIndicator, blastProtectionIndicator, slopeCalculationIndicator, sidePane);
		return runwayPane;
}

    public void newValues(Calculations cal, ActionEvent event) {
        this.cal = cal;
        outputs.updateValues(cal);

        vector.setVisible(true);

        disableIndicators(new DistanceIndicator[]{toraIndicator, asdaIndicator, todaIndicator, ldaIndicator,
                distanceFromThresholdIndicator, displacementThresholdIndicator, resaIndicator,
                stripEndIndicator, blastProtectionIndicator, slopeCalculationIndicator});

        // once implemented needs to add clearway and stopway
        runwayLength = cal.getTORA();

        // ----------------------------------- Indicator Visualisation below here////
        // -----------------------------------


        if (Double.valueOf(cal.getRunwayName().substring(0, 2)) <= 18) { // calculating from 01 to 18
            vector.setRotate(180);
            obstacle.setX((((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getWidth() + runway.getX());

            if (cal.getObstacleDistanceFromThreshold() <= (runwayLength * 0.5)) { // obstacle on near-side
                if (cal.getStatus() == "Landing") { // Plane is landing
                    obstacle.setX((((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getWidth() + runway.getX());

                    displacementThresholdIndicator.update(runway.getX(),
                            ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getWidth() + runway.getX(),
                            "" + cal.getDisplacementThreshold(),
                            0);

                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getEndX(),
                            ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getWidth() + displacementThresholdIndicator.getEndX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);

                    if (cal.getResa() > cal.getSlopeValue()) {
                        if (cal.getResa() + cal.getStripEnd() < cal.getBlastProtection()) {
                            blastProtectionIndicator.update(distanceFromThresholdIndicator.getEndX(),
                                    ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getWidth() + distanceFromThresholdIndicator.getEndX(),
                                    "Blast Protection: " + cal.getBlastProtection(),
                                    1);
                            ldaIndicator.update(blastProtectionIndicator.getEndX(),
                                    ((double) cal.getNewLDA() / (double) runwayLength) * runway.getWidth() + blastProtectionIndicator.getEndX(),
                                    "LDA: " + cal.getNewLDA(),
                                    2);
                        } else {
                            resaIndicator.update(distanceFromThresholdIndicator.getEndX(),
                                    ((double) cal.getResa() / (double) runwayLength) * runway.getWidth() + distanceFromThresholdIndicator.getEndX(),
                                    "RESA: " + cal.getResa(),
                                    1);
                            stripEndIndicator.update(resaIndicator.getEndX(),
                                    ((double) cal.getStripEnd() / (double) runwayLength) * runway.getWidth() + resaIndicator.getEndX(),
                                    "" + cal.getStripEnd(),
                                    2);
                            ldaIndicator.update(stripEndIndicator.getEndX(),
                                    ((double) cal.getNewLDA() / (double) runwayLength) * runway.getWidth() + stripEndIndicator.getEndX(),
                                    "LDA: " + cal.getNewLDA(),
                                    2);
                        }

                    } else {
                        if (cal.getSlopeValue() + cal.getStripEnd() < cal.getBlastProtection()) {
                            blastProtectionIndicator.update(distanceFromThresholdIndicator.getEndX(),
                                    ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getWidth() + distanceFromThresholdIndicator.getEndX(),
                                    "Blast Protection: " + cal.getBlastProtection(),
                                    1);
                            ldaIndicator.update(blastProtectionIndicator.getEndX(),
                                    ((double) cal.getNewLDA() / (double) runwayLength) * runway.getWidth() + blastProtectionIndicator.getEndX(),
                                    "LDA: " + cal.getNewLDA(),
                                    2);
                        } else {
                            slopeCalculationIndicator.update(distanceFromThresholdIndicator.getEndX(),
                                    ((double) cal.getSlopeValue() / (double) runwayLength) * runway.getWidth() + distanceFromThresholdIndicator.getEndX(),
                                    "Slope Calculation: " + cal.getSlopeValue(),
                                    1);
                            stripEndIndicator.update(slopeCalculationIndicator.getEndX(),
                                    ((double) cal.getStripEnd() / (double) runwayLength) * runway.getWidth() + slopeCalculationIndicator.getEndX(),
                                    "" + cal.getStripEnd(),
                                    2);
                            ldaIndicator.update(stripEndIndicator.getEndX(),
                                    ((double) cal.getNewLDA() / (double) runwayLength) * runway.getWidth() + stripEndIndicator.getEndX(),
                                    "LDA: " + cal.getNewLDA(),
                                    2);
                        }
                    }

                } else { // Plane is taking off
                    obstacle.setX((((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getWidth() + runway.getX() - obstacle.getWidth());

                    displacementThresholdIndicator.update(runway.getX(),
                            ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getWidth() + runway.getX(),
                            "" + cal.getDisplacementThreshold(),
                            0);
                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getEndX(),
                            ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getWidth() + displacementThresholdIndicator.getEndX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);
                    blastProtectionIndicator.update(distanceFromThresholdIndicator.getEndX(),
                            ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getWidth() + distanceFromThresholdIndicator.getEndX(),
                            "" + cal.getBlastProtection(),
                            0);

                    toraIndicator.update(blastProtectionIndicator.getEndX(),
                            ((double) cal.getNewTORA() / (double) runwayLength) * runway.getWidth() + blastProtectionIndicator.getEndX(),
                            "TORA: " + cal.getNewTORA(),
                            0);
                    todaIndicator.update(blastProtectionIndicator.getEndX(),
                            ((double) cal.getNewTODA() / (double) runwayLength) * runway.getWidth() + blastProtectionIndicator.getEndX(),
                            "TODA: " + cal.getNewTODA(),
                            1);
                    asdaIndicator.update(blastProtectionIndicator.getEndX(),
                            ((double) cal.getNewASDA() / (double) runwayLength) * runway.getWidth() + blastProtectionIndicator.getEndX(),
                            "ASDA: " + cal.getNewASDA(),
                            2);
                }
            } else { // obstacle on far-side
                if (cal.getStatus() == "Landing") { // Plane is landing
                    obstacle.setX((((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getWidth() + runway.getX());

                    displacementThresholdIndicator.update(runway.getX(),
                            ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getWidth() + runway.getX(),
                            "" + cal.getDisplacementThreshold(),
                            0);
                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getEndX(),
                            ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getWidth() + displacementThresholdIndicator.getEndX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);
                    ldaIndicator.update(displacementThresholdIndicator.getEndX(),
                            ((double) cal.getNewLDA() / (double) runwayLength) * runway.getWidth() + displacementThresholdIndicator.getEndX(),
                            "LDA: " + cal.getNewLDA(),
                            1);
                    stripEndIndicator.update(ldaIndicator.getEndX(),
                            ((double) cal.getStripEnd() / (double) runwayLength) * runway.getWidth() + ldaIndicator.getEndX(),
                            "" + cal.getStripEnd(),
                            1);
                    resaIndicator.update(stripEndIndicator.getEndX(),
                            ((double) cal.getResa() / (double) runwayLength) * runway.getWidth() + stripEndIndicator.getEndX(),
                            "RESA: " + cal.getResa(),
                            1);


                } else { // Plane is taking off
                    obstacle.setX((((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getWidth() + runway.getX() - obstacle.getWidth());

                    displacementThresholdIndicator.update(runway.getX(),
                            ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getWidth() + runway.getX(),
                            "" + cal.getDisplacementThreshold(),
                            0);
                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getEndX(),
                            ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getWidth() + displacementThresholdIndicator.getEndX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);
                    toraIndicator.update(runway.getX(),
                            ((double) cal.getNewTORA() / (double) runwayLength) * runway.getWidth() + runway.getX(),
                            "TORA: " + cal.getNewTORA(),
                            1);
                    stripEndIndicator.update(toraIndicator.getEndX(),
                            ((double) cal.getStripEnd() / (double) runwayLength) * runway.getWidth() + toraIndicator.getEndX(),
                            "" + cal.getStripEnd(),
                            1);

                    if (cal.getResa() > cal.getSlopeValue()) {
                        resaIndicator.update(stripEndIndicator.getEndX(),
                                ((double) cal.getResa() / (double) runwayLength) * runway.getWidth() + stripEndIndicator.getEndX(),
                                "RESA: " + cal.getResa(),
                                1);
                    } else {
                        slopeCalculationIndicator.update(stripEndIndicator.getEndX(),
                                ((double) cal.getSlopeValue() / (double) runwayLength) * runway.getWidth() + stripEndIndicator.getEndX(),
                                "Slope Calculation: " + cal.getSlopeValue(),
                                1);
                    }

                }
            }
        } else { // calculating from 19 to 36
            vector.setRotate(0);
                obstacle.setX((runway.getX() + runway.getWidth()) - (((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getWidth());

                if (cal.getObstacleDistanceFromThreshold() <= (runwayLength * 0.5)) { // obstacle on near-side
                    obstacle.setX((runway.getX() + runway.getWidth()) - (((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getWidth() - obstacle.getWidth());

                    if (cal.getStatus() == "Landing") { // Plane is landing
                        displacementThresholdIndicator.update((runway.getX() + runway.getWidth()) - ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getWidth(),
                                runway.getX() + runway.getWidth(),
                                "" + cal.getDisplacementThreshold(),
                                0);

                        distanceFromThresholdIndicator.update(displacementThresholdIndicator.getStartX() - ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getWidth(),
                                displacementThresholdIndicator.getStartX(),
                                "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                                0);

                        if (cal.getResa() > cal.getSlopeValue()) {
                            if (cal.getResa() + cal.getStripEnd() < cal.getBlastProtection()) {
                                blastProtectionIndicator.update(distanceFromThresholdIndicator.getStartX() - ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getWidth(),
                                        distanceFromThresholdIndicator.getStartX(),
                                        "Blast Protection: " + cal.getBlastProtection(),
                                        0);
                                ldaIndicator.update(blastProtectionIndicator.getStartX() - ((double) cal.getNewLDA() / (double) runwayLength) * runway.getWidth(),
                                        blastProtectionIndicator.getStartX(),
                                        "LDA: " + cal.getNewLDA(),
                                        0);
                            } else {
                                resaIndicator.update(distanceFromThresholdIndicator.getStartX() - ((double) cal.getResa() / (double) runwayLength) * runway.getWidth(),
                                        distanceFromThresholdIndicator.getStartX(),
                                        "RESA: " + cal.getResa(),
                                        0);
                                stripEndIndicator.update(resaIndicator.getStartX() - ((double) cal.getStripEnd() / (double) runwayLength) * runway.getWidth(),
                                        resaIndicator.getStartX(),
                                        "" + cal.getStripEnd(),
                                        0);
                                ldaIndicator.update(stripEndIndicator.getStartX() - ((double) cal.getNewLDA() / (double) runwayLength) * runway.getWidth(),
                                        stripEndIndicator.getStartX(),
                                        "LDA: " + cal.getNewLDA(),
                                        0);
                            }

                        } else {
                            if (cal.getSlopeValue() + cal.getStripEnd() < cal.getBlastProtection()) {
                                blastProtectionIndicator.update(distanceFromThresholdIndicator.getStartX() - ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getWidth(),
                                        distanceFromThresholdIndicator.getStartX(),
                                        "Blast Protection: " + cal.getBlastProtection(),
                                        0);
                                ldaIndicator.update(blastProtectionIndicator.getStartX() - ((double) cal.getNewLDA() / (double) runwayLength) * runway.getWidth(),
                                        blastProtectionIndicator.getStartX(),
                                        "LDA: " + cal.getNewLDA(),
                                        0);
                            } else {
                                slopeCalculationIndicator.update(distanceFromThresholdIndicator.getStartX() - ((double) cal.getSlopeValue() / (double) runwayLength) * runway.getWidth(),
                                        distanceFromThresholdIndicator.getStartX(),
                                        "Slope Calculation: " + cal.getSlopeValue(),
                                        0);
                                stripEndIndicator.update(slopeCalculationIndicator.getStartX() - ((double) cal.getStripEnd() / (double) runwayLength) * runway.getWidth(),
                                        slopeCalculationIndicator.getStartX(),
                                        "" + cal.getStripEnd(),
                                        0);
                                ldaIndicator.update(stripEndIndicator.getStartX() - ((double) cal.getNewLDA() / (double) runwayLength) * runway.getWidth(),
                                        stripEndIndicator.getStartX(),
                                        "LDA: " + cal.getNewLDA(),
                                        0);
                            }
                        }
                    } else { // Plane is taking off
                        obstacle.setX((runway.getX() + runway.getWidth()) - (((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getWidth());

                        displacementThresholdIndicator.update((runway.getX() + runway.getWidth()) - ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getWidth(),
                                runway.getX() + runway.getWidth(),
                                "" + cal.getDisplacementThreshold(),
                                0);
                        distanceFromThresholdIndicator.update(displacementThresholdIndicator.getStartX() - ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getWidth(),
                                displacementThresholdIndicator.getStartX(),
                                "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                                0);
                        blastProtectionIndicator.update(distanceFromThresholdIndicator.getStartX() - ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getWidth(),
                                distanceFromThresholdIndicator.getStartX(),
                                "" + cal.getBlastProtection(),
                                0);

                        toraIndicator.update(blastProtectionIndicator.getStartX() - ((double) cal.getNewTORA() / (double) runwayLength) * runway.getWidth(),
                                blastProtectionIndicator.getStartX(),
                                "TORA: " + cal.getNewTORA(),
                                0);
                        todaIndicator.update(blastProtectionIndicator.getStartX() - ((double) cal.getNewTODA() / (double) runwayLength) * runway.getWidth(),
                                blastProtectionIndicator.getStartX(),
                                "TODA: " + cal.getNewTODA(),
                                1);
                        asdaIndicator.update(blastProtectionIndicator.getStartX() - ((double) cal.getNewASDA() / (double) runwayLength) * runway.getWidth(),
                                blastProtectionIndicator.getStartX(),
                                "ASDA: " + cal.getNewASDA(),
                                2);
                    }
                } else { // obstacle on far-side
                    if (cal.getStatus() == "Landing") { // Plane is landing
                        obstacle.setX((runway.getX() + runway.getWidth()) - (((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getWidth() - obstacle.getWidth());

                        displacementThresholdIndicator.update((runway.getX() + runway.getWidth()) - ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getWidth(),
                                runway.getX() + runway.getWidth(),
                                "" + cal.getDisplacementThreshold(),
                                0);
                        distanceFromThresholdIndicator.update(displacementThresholdIndicator.getStartX() - ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getWidth(),
                                displacementThresholdIndicator.getStartX(),
                                "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                                0);
                        ldaIndicator.update(displacementThresholdIndicator.getStartX() - ((double) cal.getNewLDA() / (double) runwayLength) * runway.getWidth(),
                                displacementThresholdIndicator.getStartX(),
                                "LDA: " + cal.getNewLDA(),
                                1);
                        stripEndIndicator.update(ldaIndicator.getStartX() - ((double) cal.getStripEnd() / (double) runwayLength) * runway.getWidth(),
                                ldaIndicator.getStartX(),
                                "" + cal.getStripEnd(),
                                1);
                        resaIndicator.update(stripEndIndicator.getStartX() - ((double) cal.getResa() / (double) runwayLength) * runway.getWidth(),
                                stripEndIndicator.getStartX(),
                                "RESA: " + cal.getResa(),
                                1);


                    } else { // Plane is taking off
                        obstacle.setX((runway.getX() + runway.getWidth()) - (((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getWidth());

                        displacementThresholdIndicator.update((runway.getX() + runway.getWidth()) - ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getWidth(),
                                runway.getX() + runway.getWidth(),
                                "" + cal.getDisplacementThreshold(),
                                0);
                        distanceFromThresholdIndicator.update(displacementThresholdIndicator.getStartX() - ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getWidth(),
                                displacementThresholdIndicator.getStartX(),
                                "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                                0);
                        toraIndicator.update((runway.getX() + runway.getWidth()) - ((double) cal.getNewTORA() / (double) runwayLength) * runway.getWidth(),
                                runway.getX() + runway.getWidth(),
                                "TORA: " + cal.getNewTORA(),
                                1);
                        stripEndIndicator.update(toraIndicator.getStartX() - ((double) cal.getStripEnd() / (double) runwayLength) * runway.getWidth(),
                                toraIndicator.getStartX(),
                                "" + cal.getStripEnd(),
                                1);

                        if (cal.getResa() > cal.getSlopeValue()) {
                            resaIndicator.update(stripEndIndicator.getStartX() - ((double) cal.getResa() / (double) runwayLength) * runway.getWidth(),
                                    stripEndIndicator.getStartX(),
                                    "RESA: " + cal.getResa(),
                                    1);
                        } else {
                            slopeCalculationIndicator.update(stripEndIndicator.getStartX() - ((double) cal.getSlopeValue() / (double) runwayLength) * runway.getWidth(),
                                    stripEndIndicator.getStartX(),
                                    "Slope Calculation: " + cal.getSlopeValue(),
                                    1);
                        }
                    }
                }
            }

        setIndicatorsLabel(new DistanceIndicator[]{toraIndicator, asdaIndicator, todaIndicator, ldaIndicator,
                distanceFromThresholdIndicator, displacementThresholdIndicator, resaIndicator, stripEndIndicator,
                blastProtectionIndicator, slopeCalculationIndicator});

        }

    public void changeColorScheme(String value){
        if (value.equals("Normal")){
            //System.out.println("Normal If Passed");
            obstacle.setFill(Color.ORANGE);
            lowerBackground.setFill(Color.GREEN);
            upperBackground.setFill(Color.LIGHTCYAN);
            clearedAndGradedArea.setFill(Color.BLUE);
            runway.setFill(Color.DARKGRAY);
            setIndicatorsToLightMode(new DistanceIndicator[]{toraIndicator, asdaIndicator, todaIndicator, ldaIndicator,
                    distanceFromThresholdIndicator, displacementThresholdIndicator, resaIndicator,
                    stripEndIndicator, blastProtectionIndicator, slopeCalculationIndicator});
        }
        if(value.equals("Dark")){
            //System.out.println("Dark If Passed");
            obstacle.setFill(Color.web("0xC84B31"));
            lowerBackground.setFill(Color.web("0x2D4263"));
            upperBackground.setFill(Color.web("0xECDBBA"));
            clearedAndGradedArea.setFill(Color.web("0x191919"));
            runway.setFill(Color.DARKGRAY);
            setIndicatorsToDarkMode(new DistanceIndicator[]{toraIndicator, asdaIndicator, todaIndicator, ldaIndicator,
                    distanceFromThresholdIndicator, displacementThresholdIndicator, resaIndicator,
                    stripEndIndicator, blastProtectionIndicator, slopeCalculationIndicator});
        }
    }
}