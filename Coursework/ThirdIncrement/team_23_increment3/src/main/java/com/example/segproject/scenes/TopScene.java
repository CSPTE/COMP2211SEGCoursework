package com.example.segproject.scenes;

import com.example.segproject.Calculations;
import com.example.segproject.SceneController;
import com.example.segproject.components.DistanceIndicator;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

/**
 * Class that builds and determines the behaviour of the top view
 */
public class TopScene extends BaseScene {

    private ImageView	runway;
    private ImageView	vector;
	private Pane		rPane;
	private Rectangle	background;
	private Polygon		clearedAndGradedArea;

    public TopScene(SceneController controller) {
        super(controller);
    }

    /**
     * Builds the ui elements of the scene
     */
    public void build() {
        setupDefaultScene();
        inputs.setOnButtonClicked(this::newValues);

        runway = new ImageView("runway.png");
        runway.setFitWidth((controller.getWidth() * 0.66 - 300));
        runway.setFitHeight(100);
        runway.setLayoutX((runwayPaneCenterX - runway.getFitWidth() * 0.5));
        runway.setLayoutY(runwayPaneCenterY - runway.getFitHeight() * 0.5);

        clearedAndGradedArea = new Polygon();
        clearedAndGradedArea.getPoints().addAll(new Double[]{
                runway.getLayoutX() - 60, runway.getLayoutY() - 75,
                runway.getLayoutX() - 60, runway.getLayoutY() + 75 + runway.getFitHeight(),
                runway.getLayoutX() + 60, runway.getLayoutY() + 75 + runway.getFitHeight(),
                runway.getLayoutX() + 120, runway.getLayoutY() + 105 + runway.getFitHeight(),
                runway.getLayoutX() - 120 + runway.getFitWidth(), runway.getLayoutY() + 105 + runway.getFitHeight(),
                runway.getLayoutX() - 60 + runway.getFitWidth(), runway.getLayoutY() + 75 + runway.getFitHeight(),
                runway.getLayoutX() + 60 + runway.getFitWidth(), runway.getLayoutY() + 75 + runway.getFitHeight(),
                runway.getLayoutX() + 60 + runway.getFitWidth(), runway.getLayoutY() - 75,
                runway.getLayoutX() - 60 + runway.getFitWidth(), runway.getLayoutY() - 75,
                runway.getLayoutX() - 120 + runway.getFitWidth(), runway.getLayoutY() - 105,
                runway.getLayoutX() + 120, runway.getLayoutY() - 105,
                runway.getLayoutX() + 60, runway.getLayoutY() - 75
        });

        obstacle = new Rectangle(runwayPaneCenterX - 25, runwayPaneCenterY - 25, 50, 50);
        background = new Rectangle(0, 0, (controller.getWidth() * 0.66), controller.getHeight());

        background.setFill(Color.GREEN);
        clearedAndGradedArea.setFill(Color.BLUE);
        obstacle.setFill(Color.ORANGE);
        runwayPane.getChildren().addAll(background, clearedAndGradedArea, runway, obstacle);

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

        runwayPane.getChildren().addAll(toraIndicator, asdaIndicator, todaIndicator, ldaIndicator,
                distanceFromThresholdIndicator, displacementThresholdIndicator, resaIndicator,
                stripEndIndicator, blastProtectionIndicator, slopeCalculationIndicator);

        vector = new ImageView("vectorArrow.png");
        vector.setPreserveRatio(true);
        vector.setFitHeight(100);
        vector.setLayoutX(runway.getLayoutX() + 100);
        vector.setLayoutY(runway.getLayoutY() - 200);
        vector.setVisible(false);

        runwayPane.getChildren().add(vector);
        this.rPane = runwayPane;
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

        // ----------------------------------- Indicator Visualisation below here -----------------------------------


        if (cal.getObstacleDirection() == "North") {
            obstacle.setY((runwayPaneCenterY - (obstacle.getHeight() * 0.5)) - (double) cal.getObstacleDistanceFromCenter());
        } else if (cal.getObstacleDirection() == "South") {
            obstacle.setY((runwayPaneCenterY - (obstacle.getHeight() * 0.5)) + (double) cal.getObstacleDistanceFromCenter());
        }


        if (Double.valueOf(cal.getRunwayName().substring(0, 2)) <= 18) { // calculating from 01 to 18
            vector.setRotate(180);
            obstacle.setX((((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getFitWidth() + runway.getLayoutX());

            if (cal.getObstacleDistanceFromThreshold() <= (runwayLength * 0.5)) { // obstacle on near-side
                if (cal.getStatus() == "Landing") { // Plane is landing
                    obstacle.setX((((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getFitWidth() + runway.getLayoutX());

                    displacementThresholdIndicator.update(runway.getLayoutX(),
                            ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getFitWidth() + runway.getLayoutX(),
                            "" + cal.getDisplacementThreshold(),
                            0);

                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getEndX(),
                            ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getFitWidth() + displacementThresholdIndicator.getEndX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);

                    if (cal.getResa() > cal.getSlopeValue()) {
                        if (cal.getResa() + cal.getStripEnd() < cal.getBlastProtection()) {
                            blastProtectionIndicator.update(distanceFromThresholdIndicator.getEndX(),
                                    ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getFitWidth() + distanceFromThresholdIndicator.getEndX(),
                                    "Blast Protection: " + cal.getBlastProtection(),
                                    1);
                            ldaIndicator.update(blastProtectionIndicator.getEndX(),
                                    ((double) cal.getNewLDA() / (double) runwayLength) * runway.getFitWidth() + blastProtectionIndicator.getEndX(),
                                    "LDA: " + cal.getNewLDA(),
                                    2);
                        } else {
                            resaIndicator.update(distanceFromThresholdIndicator.getEndX(),
                                    ((double) cal.getResa() / (double) runwayLength) * runway.getFitWidth() + distanceFromThresholdIndicator.getEndX(),
                                    "RESA: " + cal.getResa(),
                                    1);
                            stripEndIndicator.update(resaIndicator.getEndX(),
                                    ((double) cal.getStripEnd() / (double) runwayLength) * runway.getFitWidth() + resaIndicator.getEndX(),
                                    "" + cal.getStripEnd(),
                                    2);
                            ldaIndicator.update(stripEndIndicator.getEndX(),
                                    ((double) cal.getNewLDA() / (double) runwayLength) * runway.getFitWidth() + stripEndIndicator.getEndX(),
                                    "LDA: " + cal.getNewLDA(),
                                    3);
                        }

                    } else {
                        if (cal.getSlopeValue() + cal.getStripEnd() < cal.getBlastProtection()) {
                            blastProtectionIndicator.update(distanceFromThresholdIndicator.getEndX(),
                                    ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getFitWidth() + distanceFromThresholdIndicator.getEndX(),
                                    "Blast Protection: " + cal.getBlastProtection(),
                                    1);
                            ldaIndicator.update(blastProtectionIndicator.getEndX(),
                                    ((double) cal.getNewLDA() / (double) runwayLength) * runway.getFitWidth() + blastProtectionIndicator.getEndX(),
                                    "LDA: " + cal.getNewLDA(),
                                    2);
                        } else {
                            slopeCalculationIndicator.update(distanceFromThresholdIndicator.getEndX(),
                                    ((double) cal.getSlopeValue() / (double) runwayLength) * runway.getFitWidth() + distanceFromThresholdIndicator.getEndX(),
                                    "Slope Calculation: " + cal.getSlopeValue(),
                                    1);
                            stripEndIndicator.update(slopeCalculationIndicator.getEndX(),
                                    ((double) cal.getStripEnd() / (double) runwayLength) * runway.getFitWidth() + slopeCalculationIndicator.getEndX(),
                                    "" + cal.getStripEnd(),
                                    2);
                            ldaIndicator.update(stripEndIndicator.getEndX(),
                                    ((double) cal.getNewLDA() / (double) runwayLength) * runway.getFitWidth() + stripEndIndicator.getEndX(),
                                    "LDA: " + cal.getNewLDA(),
                                    2);
                        }
                    }

                } else { // Plane is taking off
                    obstacle.setX((((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getFitWidth() + runway.getLayoutX() - obstacle.getWidth());

                    displacementThresholdIndicator.update(runway.getLayoutX(),
                            ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getFitWidth() + runway.getLayoutX(),
                            "" + cal.getDisplacementThreshold(),
                            0);
                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getEndX(),
                            ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getFitWidth() + displacementThresholdIndicator.getEndX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);
                    blastProtectionIndicator.update(distanceFromThresholdIndicator.getEndX(),
                            ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getFitWidth() + distanceFromThresholdIndicator.getEndX(),
                            "" + cal.getBlastProtection(),
                            0);

                    toraIndicator.update(blastProtectionIndicator.getEndX(),
                            ((double) cal.getNewTORA() / (double) runwayLength) * runway.getFitWidth() + blastProtectionIndicator.getEndX(),
                            "TORA: " + cal.getNewTORA(),
                            0);
                    todaIndicator.update(blastProtectionIndicator.getEndX(),
                            ((double) cal.getNewTODA() / (double) runwayLength) * runway.getFitWidth() + blastProtectionIndicator.getEndX(),
                            "TODA: " + cal.getNewTODA(),
                            1);
                    asdaIndicator.update(blastProtectionIndicator.getEndX(),
                            ((double) cal.getNewASDA() / (double) runwayLength) * runway.getFitWidth() + blastProtectionIndicator.getEndX(),
                            "ASDA: " + cal.getNewASDA(),
                            2);
                }
            } else { // obstacle on far-side
                if (cal.getStatus() == "Landing") { // Plane is landing
                    obstacle.setX((((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getFitWidth() + runway.getLayoutX());

                    displacementThresholdIndicator.update(runway.getLayoutX(),
                            ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getFitWidth() + runway.getLayoutX(),
                            "" + cal.getDisplacementThreshold(),
                            0);
                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getEndX(),
                            ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getFitWidth() + displacementThresholdIndicator.getEndX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);
                    ldaIndicator.update(displacementThresholdIndicator.getEndX(),
                            ((double) cal.getNewLDA() / (double) runwayLength) * runway.getFitWidth() + displacementThresholdIndicator.getEndX(),
                            "LDA: " + cal.getNewLDA(),
                            1);
                    stripEndIndicator.update(ldaIndicator.getEndX(),
                            ((double) cal.getStripEnd() / (double) runwayLength) * runway.getFitWidth() + ldaIndicator.getEndX(),
                            "" + cal.getStripEnd(),
                            1);
                    resaIndicator.update(stripEndIndicator.getEndX(),
                            ((double) cal.getResa() / (double) runwayLength) * runway.getFitWidth() + stripEndIndicator.getEndX(),
                            "RESA: " + cal.getResa(),
                            1);


                } else { // Plane is taking off
                    obstacle.setX((((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getFitWidth() + runway.getLayoutX() - obstacle.getWidth());

                    displacementThresholdIndicator.update(runway.getLayoutX(),
                            ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getFitWidth() + runway.getLayoutX(),
                            "" + cal.getDisplacementThreshold(),
                            0);
                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getEndX(),
                            ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getFitWidth() + displacementThresholdIndicator.getEndX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);
                    toraIndicator.update(runway.getLayoutX(),
                            ((double) cal.getNewTORA() / (double) runwayLength) * runway.getFitWidth() + runway.getLayoutX(),
                            "TORA: " + cal.getNewTORA(),
                            1);
                    stripEndIndicator.update(toraIndicator.getEndX(),
                            ((double) cal.getStripEnd() / (double) runwayLength) * runway.getFitWidth() + toraIndicator.getEndX(),
                            "" + cal.getStripEnd(),
                            1);

                    if (cal.getResa() > cal.getSlopeValue()) {
                        resaIndicator.update(stripEndIndicator.getEndX(),
                                ((double) cal.getResa() / (double) runwayLength) * runway.getFitWidth() + stripEndIndicator.getEndX(),
                                "RESA: " + cal.getResa(),
                                1);
                    } else {
                        slopeCalculationIndicator.update(stripEndIndicator.getEndX(),
                                ((double) cal.getSlopeValue() / (double) runwayLength) * runway.getFitWidth() + stripEndIndicator.getEndX(),
                                "Slope Calculation: " + cal.getSlopeValue(),
                                1);
                    }
                }
            }

        } else { // calculating from 19 to 36
            vector.setRotate(0);
            obstacle.setX((runway.getLayoutX() + runway.getFitWidth()) - (((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getFitWidth());

            if (cal.getObstacleDistanceFromThreshold() <= (runwayLength * 0.5)) { // obstacle on near-side
                obstacle.setX((runway.getLayoutX() + runway.getFitWidth()) - (((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getFitWidth() - obstacle.getWidth());

                if (cal.getStatus() == "Landing") { // Plane is landing
                    displacementThresholdIndicator.update((runway.getLayoutX() + runway.getFitWidth()) - ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getFitWidth(),
                            runway.getLayoutX() + runway.getFitWidth(),
                            "" + cal.getDisplacementThreshold(),
                            0);

                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getStartX() - ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getFitWidth(),
                            displacementThresholdIndicator.getStartX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);

                    if (cal.getResa() > cal.getSlopeValue()) {
                        if (cal.getResa() + cal.getStripEnd() < cal.getBlastProtection()) {
                            blastProtectionIndicator.update(distanceFromThresholdIndicator.getStartX() - ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getFitWidth(),
                                    distanceFromThresholdIndicator.getStartX(),
                                    "Blast Protection: " + cal.getBlastProtection(),
                                    0);
                            ldaIndicator.update(blastProtectionIndicator.getStartX() - ((double) cal.getNewLDA() / (double) runwayLength) * runway.getFitWidth(),
                                    blastProtectionIndicator.getStartX(),
                                    "LDA: " + cal.getNewLDA(),
                                    0);
                        } else {
                            resaIndicator.update(distanceFromThresholdIndicator.getStartX() - ((double) cal.getResa() / (double) runwayLength) * runway.getFitWidth(),
                                    distanceFromThresholdIndicator.getStartX(),
                                    "RESA: " + cal.getResa(),
                                    0);
                            stripEndIndicator.update(resaIndicator.getStartX() - ((double) cal.getStripEnd() / (double) runwayLength) * runway.getFitWidth(),
                                    resaIndicator.getStartX(),
                                    "" + cal.getStripEnd(),
                                    0);
                            ldaIndicator.update(stripEndIndicator.getStartX() - ((double) cal.getNewLDA() / (double) runwayLength) * runway.getFitWidth(),
                                    stripEndIndicator.getStartX(),
                                    "LDA: " + cal.getNewLDA(),
                                    0);
                        }

                    } else {
                        if (cal.getSlopeValue() + cal.getStripEnd() < cal.getBlastProtection()) {
                            blastProtectionIndicator.update(distanceFromThresholdIndicator.getStartX() - ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getFitWidth(),
                                    distanceFromThresholdIndicator.getStartX(),
                                    "Blast Protection: " + cal.getBlastProtection(),
                                    0);
                            ldaIndicator.update(blastProtectionIndicator.getStartX() - ((double) cal.getNewLDA() / (double) runwayLength) * runway.getFitWidth(),
                                    blastProtectionIndicator.getStartX(),
                                    "LDA: " + cal.getNewLDA(),
                                    0);
                        } else {
                            slopeCalculationIndicator.update(distanceFromThresholdIndicator.getStartX() - ((double) cal.getSlopeValue() / (double) runwayLength) * runway.getFitWidth(),
                                    distanceFromThresholdIndicator.getStartX(),
                                    "Slope Calculation: " + cal.getSlopeValue(),
                                    0);
                            stripEndIndicator.update(slopeCalculationIndicator.getStartX() - ((double) cal.getStripEnd() / (double) runwayLength) * runway.getFitWidth(),
                                    slopeCalculationIndicator.getStartX(),
                                    "" + cal.getStripEnd(),
                                    0);
                            ldaIndicator.update(stripEndIndicator.getStartX() - ((double) cal.getNewLDA() / (double) runwayLength) * runway.getFitWidth(),
                                    stripEndIndicator.getStartX(),
                                    "LDA: " + cal.getNewLDA(),
                                    0);
                        }
                    }
                } else { // Plane is taking off
                    obstacle.setX((runway.getLayoutX() + runway.getFitWidth()) - (((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getFitWidth());

                    displacementThresholdIndicator.update((runway.getLayoutX() + runway.getFitWidth()) - ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getFitWidth(),
                            runway.getLayoutX() + runway.getFitWidth(),
                            "" + cal.getDisplacementThreshold(),
                            0);
                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getStartX() - ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getFitWidth(),
                            displacementThresholdIndicator.getStartX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);
                    blastProtectionIndicator.update(distanceFromThresholdIndicator.getStartX() - ((double) cal.getBlastProtection() / (double) runwayLength) * runway.getFitWidth(),
                            distanceFromThresholdIndicator.getStartX(),
                            "" + cal.getBlastProtection(),
                            0);

                    toraIndicator.update(blastProtectionIndicator.getStartX() - ((double) cal.getNewTORA() / (double) runwayLength) * runway.getFitWidth(),
                            blastProtectionIndicator.getStartX(),
                            "TORA: " + cal.getNewTORA(),
                            0);
                    todaIndicator.update(blastProtectionIndicator.getStartX() - ((double) cal.getNewTODA() / (double) runwayLength) * runway.getFitWidth(),
                            blastProtectionIndicator.getStartX(),
                            "TODA: " + cal.getNewTODA(),
                            1);
                    asdaIndicator.update(blastProtectionIndicator.getStartX() - ((double) cal.getNewASDA() / (double) runwayLength) * runway.getFitWidth(),
                            blastProtectionIndicator.getStartX(),
                            "ASDA: " + cal.getNewASDA(),
                            2);
                }
            } else { // obstacle on far-side
                if (cal.getStatus() == "Landing") { // Plane is landing
                    obstacle.setX((runway.getLayoutX() + runway.getFitWidth()) - (((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getFitWidth() - obstacle.getWidth());

                    displacementThresholdIndicator.update((runway.getLayoutX() + runway.getFitWidth()) - ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getFitWidth(),
                            runway.getLayoutX() + runway.getFitWidth(),
                            "" + cal.getDisplacementThreshold(),
                            0);
                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getStartX() - ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getFitWidth(),
                            displacementThresholdIndicator.getStartX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);
                    ldaIndicator.update( displacementThresholdIndicator.getStartX() - ((double) cal.getNewLDA() / (double) runwayLength) * runway.getFitWidth(),
                            displacementThresholdIndicator.getStartX(),
                            "LDA: " + cal.getNewLDA(),
                            1);
                    stripEndIndicator.update(ldaIndicator.getStartX() - ((double) cal.getStripEnd() / (double) runwayLength) * runway.getFitWidth(),
                            ldaIndicator.getStartX(),
                            "" + cal.getStripEnd(),
                            1);
                    resaIndicator.update(stripEndIndicator.getStartX() - ((double) cal.getResa() / (double) runwayLength) * runway.getFitWidth(),
                            stripEndIndicator.getStartX(),
                            "RESA: " + cal.getResa(),
                            1);


                } else { // Plane is taking off
                    obstacle.setX((runway.getLayoutX() + runway.getFitWidth()) - (((double) cal.getObstacleDistanceFromThreshold() + (double) cal.getDisplacementThreshold()) / (double) runwayLength) * runway.getFitWidth());

                    displacementThresholdIndicator.update((runway.getLayoutX() + runway.getFitWidth()) - ((double) cal.getDisplacementThreshold() / (double) runwayLength) * runway.getFitWidth(),
                            runway.getLayoutX() + runway.getFitWidth(),
                            "" + cal.getDisplacementThreshold(),
                            0);
                    distanceFromThresholdIndicator.update(displacementThresholdIndicator.getStartX() - ((double) cal.getObstacleDistanceFromThreshold() / (double) runwayLength) * runway.getFitWidth(),
                            displacementThresholdIndicator.getStartX(),
                            "Distance from Threshold: " + cal.getObstacleDistanceFromThreshold(),
                            0);
                    toraIndicator.update((runway.getLayoutX() + runway.getFitWidth()) - ((double) cal.getNewTORA() / (double) runwayLength) * runway.getFitWidth(),
                            runway.getLayoutX() + runway.getFitWidth(),
                            "TORA: " + cal.getNewTORA(),
                            1);
                    stripEndIndicator.update(toraIndicator.getStartX() - ((double) cal.getStripEnd() / (double) runwayLength) * runway.getFitWidth(),
                            toraIndicator.getStartX(),
                            "" + cal.getStripEnd(),
                            1);

                    if (cal.getResa() > cal.getSlopeValue()) {
                        resaIndicator.update(stripEndIndicator.getStartX() - ((double) cal.getResa() / (double) runwayLength) * runway.getFitWidth(),
                                stripEndIndicator.getStartX(),
                                "RESA: " + cal.getResa(),
                                1);
                    } else {
                        slopeCalculationIndicator.update(stripEndIndicator.getStartX() - ((double) cal.getSlopeValue() / (double) runwayLength) * runway.getFitWidth(),
                                stripEndIndicator.getStartX(),
                                "Slope Calculation: " + cal.getSlopeValue(),
                                1);
                    }
                }
            }

        }

        setIndicatorsLabel(new DistanceIndicator[] { toraIndicator, asdaIndicator, todaIndicator, ldaIndicator,
                distanceFromThresholdIndicator, displacementThresholdIndicator, resaIndicator,
				stripEndIndicator, blastProtectionIndicator, slopeCalculationIndicator});
		setIndicatorsToDarkMode(new DistanceIndicator[]{toraIndicator, asdaIndicator, todaIndicator, ldaIndicator,
			distanceFromThresholdIndicator, displacementThresholdIndicator, resaIndicator,
			stripEndIndicator, blastProtectionIndicator, slopeCalculationIndicator});
        if (rotationEnabled) {
            rotate(Integer.valueOf(cal.getRunwayName().substring(0, 2)));
        } else {
            rotate(9);
        }
    }

    public void changeColorScheme(String value){
        if (value.equals("Normal")){
            background.setFill(Color.GREEN);
            clearedAndGradedArea.setFill(Color.BLUE);
			obstacle.setFill(Color.ORANGE);            
        }
        if(value.equals("Dark")){
            background.setFill(Color.web("0x2D4263"));
            clearedAndGradedArea.setFill(Color.web("0x191919"));
            obstacle.setFill(Color.web("0xC84B31"));
        }
    }

    public Pane getRunwayPane() { return this.rPane; }

}
