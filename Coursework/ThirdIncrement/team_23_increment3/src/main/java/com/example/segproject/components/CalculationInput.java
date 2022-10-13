package com.example.segproject.components;

import java.util.Arrays;
import java.util.List;

import com.example.segproject.Calculations;

import com.example.segproject.SceneController;
import com.example.segproject.scenes.SideScene;
import com.example.segproject.scenes.SimulScene;
import com.example.segproject.scenes.TopScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import com.example.segproject.events.CalculateButtonListener;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.util.converter.IntegerStringConverter;

public class CalculationInput extends VBox {
    Calculations cal;
    String name;
    String status;
    String toraString;
    String asdaString;
    String todaString;
    String ldaString;
    String obHeightString;
    String distanceString;
    String direction;
    String obDistanceString;
    String displacementString;
    String resaString;
    String stripEndString;
    String blastProtString;
    boolean firstCalculation = true;
    String newLine = System.getProperty("line.separator");

    private CalculateButtonListener buttonClickedListener;

    public CalculationInput(SceneController controller){
        cal = new Calculations();

        Label colorLabel = new Label("Color Scheme");
        HBox colorBox = new HBox();
        String colorChoices[] = {"Normal", "Dark"};
        ChoiceBox colorChoice = new ChoiceBox(FXCollections.observableArrayList(colorChoices));
        colorChoice.setValue("Normal");
        colorBox.getChildren().add(colorChoice);
        this.getChildren().add(colorLabel);
        this.getChildren().add(colorBox);
        colorChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                if(controller.getCurrentScene() instanceof TopScene){
                    //System.out.println("Top scene");
                    ((TopScene) controller.getCurrentScene()).changeColorScheme(colorChoices[new_value.intValue()]);
                }
                if(controller.getCurrentScene() instanceof SideScene){
                    //System.out.println("Side scene");
                    ((SideScene) controller.getCurrentScene()).changeColorScheme(colorChoices[new_value.intValue()]);
                }
                if(controller.getCurrentScene() instanceof SimulScene){
                    ((SimulScene) controller.getCurrentScene()).changeColorScheme(colorChoices[new_value.intValue()]);
                }
            }
        });

        Label nameLabel = new Label("Runway Designator:");
        HBox nameBox = new HBox();
        RunwayDesignatorInput nameText = new RunwayDesignatorInput();
        nameBox.getChildren().add(nameText);
        this.getChildren().add(nameLabel);
        this.getChildren().add(nameBox);

        Label statusLabel = new Label("Takeoff or Landing");
        HBox statusBox = new HBox();
        ChoiceBox<String> statusChoice = new ChoiceBox<String>();
        statusChoice.getItems().addAll("Takeoff", "Landing");
        statusBox.getChildren().add(statusChoice);
        this.getChildren().add(statusLabel);
        this.getChildren().add(statusBox);

        Label toraLabel = new Label("Original TORA:");
        HBox toraBox = new HBox();
        TextField toraText = new IntegerField();
        Label toraMeasure = new Label("Meters");
        toraBox.getChildren().add(toraText);
        toraBox.getChildren().add(toraMeasure);
        this.getChildren().add(toraLabel);
        this.getChildren().add(toraBox);

        Label asdaLabel = new Label("Original ASDA:");
        HBox asdaBox = new HBox();
        TextField asdaText = new IntegerField();
        Label asdaMeasure = new Label("Meters");
        asdaBox.getChildren().add(asdaText);
        asdaBox.getChildren().add(asdaMeasure);
        this.getChildren().add(asdaLabel);
        this.getChildren().add(asdaBox);

        Label todaLabel = new Label("Original TODA:");
        HBox todaBox = new HBox();
        TextField todaText = new IntegerField();
        Label todaMeasure = new Label("Meters");
        todaBox.getChildren().add(todaText);
        todaBox.getChildren().add(todaMeasure);
        this.getChildren().add(todaLabel);
        this.getChildren().add(todaBox);

        Label ldaLabel = new Label("Original LDA:");
        HBox ldaBox = new HBox();
        TextField ldaText = new IntegerField();
        Label ldaMeasure = new Label("Meters");
        ldaBox.getChildren().add(ldaText);
        ldaBox.getChildren().add(ldaMeasure);
        this.getChildren().add(ldaLabel);
        this.getChildren().add(ldaBox);

        Label obstacleHeightLabel = new Label("Obstacle Height:");
        HBox obstacleHeightBox = new HBox();
        TextField obstacleHeightText = new IntegerField();
        Label obstacleHeightMeasure = new Label("Meters");
        obstacleHeightBox.getChildren().add(obstacleHeightText);
        obstacleHeightBox.getChildren().add(obstacleHeightMeasure);
        this.getChildren().add(obstacleHeightLabel);
        this.getChildren().add(obstacleHeightBox);

        Label distanceLabel = new Label("Distance From Threshold to Obstacle:");
        HBox distanceBox = new HBox();
        TextField distanceText = new TextField();
        distanceText.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        Label distanceMeasure = new Label("Meters");
        distanceBox.getChildren().add(distanceText);
        distanceBox.getChildren().add(distanceMeasure);
        this.getChildren().add(distanceLabel);
        this.getChildren().add(distanceBox);

        Label obstacleDistanceLabel = new Label("Obstacle Distance From Centerline:");
        HBox obstacleDistanceBox = new HBox();
        TextField obstacleDistanceText = new IntegerField();
        Label obstacleDistanceMeasure = new Label("Meters");
        obstacleDistanceBox.getChildren().add(obstacleDistanceText);
        obstacleDistanceBox.getChildren().add(obstacleDistanceMeasure);
        this.getChildren().add(obstacleDistanceLabel);
        this.getChildren().add(obstacleDistanceBox);

        Label directionLabel = new Label("Direction of Obstacle From Centerline:");
        HBox directionBox = new HBox();
        ChoiceBox<String> directionChoice = new ChoiceBox<String>();
        directionChoice.getItems().addAll("North", "East", "South", "West");
        directionBox.getChildren().add(directionChoice);
        this.getChildren().add(directionLabel);
        this.getChildren().add(directionBox);

        Label displacementLabel = new Label("Displacement Threshold:");
        HBox displacementBox = new HBox();
        TextField displacementText = new IntegerField();
        Label displacementMeasure = new Label("Meters");
        displacementBox.getChildren().add(displacementText);
        displacementBox.getChildren().add(displacementMeasure);
        this.getChildren().add(displacementLabel);
        this.getChildren().add(displacementBox);

        Label resaLabel = new Label("RESA:");
        HBox resaBox = new HBox();
        TextField resaText = new IntegerField();
        Label resaMeasure = new Label("Meters");
        resaBox.getChildren().add(resaText);
        resaBox.getChildren().add(resaMeasure);
        this.getChildren().add(resaLabel);
        this.getChildren().add(resaBox);

        Label stripEndLabel = new Label("Strip End Length:");
        HBox stripEndBox = new HBox();
        TextField stripEndText = new IntegerField();
        Label stripEndMeasure = new Label("Meters");
        stripEndBox.getChildren().add(stripEndText);
        stripEndBox.getChildren().add(stripEndMeasure);
        this.getChildren().add(stripEndLabel);
        this.getChildren().add(stripEndBox);

        Label blastProtectionLabel = new Label("Airplane Blast Protection Length:");
        HBox blastProtectionBox = new HBox();
        TextField blastProtectionText = new IntegerField();
        Label blastProtectionMeasure = new Label("Meters");
        blastProtectionBox.getChildren().add(blastProtectionText);
        blastProtectionBox.getChildren().add(blastProtectionMeasure);
        this.getChildren().add(blastProtectionLabel);
        this.getChildren().add(blastProtectionBox);

        //Label obstacleHeightDirectionLabel = new Label("Runway Designator To Which Obstacle Max Height Is Closer:");
        //HBox obstacleHeightDirectionBox = new HBox();
        //TextField obstacleHeightDirectionText = new TextField();
        //Label obstacleHeightDirectionMeasure = new Label("Meters");
        //obstacleHeightDirectionBox.getChildren().add(obstacleHeightDirectionText);
        //obstacleHeightDirectionBox.getChildren().add(obstacleHeightDirectionMeasure);
        //this.getChildren().add(obstacleHeightDirectionLabel);
        //this.getChildren().add(obstacleHeightDirectionBox);

        //Region blank = new Region();
        //this.setVgrow(blank, Priority.ALWAYS);
        //this.getChildren().add(blank);

        Button calculate = new Button("Calculate");
        this.getChildren().add(calculate);

        calculate.setOnAction(e -> {
            String nameNotification = null,statusNotification = null,toraNotification = null,todaNotification = null,asdaNotification = null,ldaNotification = null;
            String obHeightNotification = null,distanceNotification = null,directionNotification = null,obDistanceNotification= null,displacementNotification = null;
            String resaNotification = null,stripEndNotification = null,blastProtNotification = null;
            String finalNotification = "You have changed the following data:" + newLine;
            if (firstCalculation == false){
                if (!name.equals(nameText.getText())){ nameNotification = "Name: " + name + " --> " + nameText.getText(); }
                if (!status.equals(statusChoice.getValue())){ statusNotification = "Status: " + status + " --> " + statusChoice.getValue(); }
                if (!toraString.equals(toraText.getText())){ toraNotification = "TORA: " + toraString + " --> " + toraText.getText(); }
                if (!asdaString.equals(asdaText.getText())){ asdaNotification = "ASDA: " + asdaString + " --> " + asdaText.getText(); }
                if (!todaString.equals(todaText.getText())){ todaNotification = "TORA: " + todaString + " --> " + todaText.getText(); }
                if (!ldaString.equals(ldaText.getText())){ ldaNotification = "LDA: " + ldaString + " --> " + ldaText.getText(); }
                if (!obHeightString.equals(obstacleHeightText.getText())){ obHeightNotification = "Obstacle Height: " + obHeightString + " --> " + obstacleHeightText.getText(); }
                if (!distanceString.equals(distanceText.getText())){ distanceNotification = "Distance From Threshold: " + distanceString + " --> " + distanceText.getText(); }
                if (!direction.equals(directionChoice.getValue())){ directionNotification = "Direction From Centerline: " + direction + " --> " + directionChoice.getValue(); }
                if (!obDistanceString.equals(obstacleDistanceText.getText())){ obDistanceNotification = "Obstacle Distance From Centerline: " + obDistanceString + " --> " + obstacleDistanceText.getText(); }
                if (!displacementString.equals(displacementText.getText())){ displacementNotification = "Displacement Threshold: " + displacementString + " --> " + displacementText.getText(); }
                if (!resaString.equals(resaText.getText())){ resaNotification = "RESA: " + resaString + " --> " + resaText.getText(); }
                if (!stripEndString.equals(stripEndText.getText())){ stripEndNotification = "Strip End: " + stripEndString + " --> " + stripEndText.getText(); }
                if (!blastProtString.equals(blastProtectionText.getText())){ blastProtNotification = "Blast Protection: " + blastProtString + " --> " + blastProtectionText.getText(); }
            }
            if(!(nameNotification == null)){ finalNotification = finalNotification + nameNotification + newLine; }
            if(!(statusNotification == null)){ finalNotification = finalNotification + statusNotification + newLine; }
            if(!(toraNotification == null)){ finalNotification = finalNotification + toraNotification + newLine; }
            if(!(todaNotification == null)){ finalNotification = finalNotification + todaNotification + newLine; }
            if(!(asdaNotification == null)){ finalNotification = finalNotification + asdaNotification + newLine; }
            if(!(ldaNotification == null)){ finalNotification = finalNotification + ldaNotification + newLine; }
            if(!(obHeightNotification == null)){ finalNotification = finalNotification + obHeightNotification + newLine; }
            if(!(obHeightNotification == null)){ finalNotification = finalNotification + obHeightNotification + newLine; }
            if(!(distanceNotification == null)){ finalNotification = finalNotification + distanceNotification + newLine; }
            if(!(directionNotification == null)){ finalNotification = finalNotification + directionNotification + newLine; }
            if(!(obDistanceNotification == null)){ finalNotification = finalNotification + obDistanceNotification + newLine; }
            if(!(displacementNotification == null)){ finalNotification = finalNotification + displacementNotification + newLine; }
            if(!(resaNotification == null)){ finalNotification = finalNotification + resaNotification + newLine; }
            if(!(stripEndNotification == null)){ finalNotification = finalNotification + stripEndNotification + newLine; }
            if(!(blastProtNotification == null)){ finalNotification = finalNotification + blastProtNotification + newLine; }


            name = nameText.getText();
            status = statusChoice.getValue();
            toraString = toraText.getText();
            asdaString = asdaText.getText();
            todaString = todaText.getText();
            ldaString = ldaText.getText();
            obHeightString = obstacleHeightText.getText();
            distanceString = distanceText.getText();
            direction = directionChoice.getValue();
            obDistanceString = obstacleDistanceText.getText();
            displacementString = displacementText.getText();
            resaString = resaText.getText();
            stripEndString = stripEndText.getText();
            blastProtString = blastProtectionText.getText();

            //check none of the parameters are blank otherwise give notification of updates if data changed
            List<String> paramList = Arrays.asList(name, status, toraString,asdaString,todaString,ldaString,obHeightString,distanceString,direction,obDistanceString,displacementString,resaString,stripEndString,blastProtString);
            if (paramList.stream().anyMatch(str -> str.isBlank())) {
                new Alert(AlertType.NONE, "Please enter values for all fields.", ButtonType.OK).showAndWait();
                return;
            } else if (firstCalculation == false){
                Alert al = new Alert(AlertType.NONE, finalNotification, ButtonType.OK);
                al.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                al.show();
            } else {
                firstCalculation = false;
            }

            int tora = Integer.parseInt(toraString);
            int asda = Integer.parseInt(asdaString);
            int toda = Integer.parseInt(todaString);
            int lda = Integer.parseInt(ldaString);
            int obHeight = Integer.parseInt(obHeightString);
            int distance = Integer.parseInt(distanceString);
            int obDistance = Integer.parseInt(obDistanceString);
            int displacement = Integer.parseInt(displacementString);
            int resa = Integer.parseInt(resaString);
            int stripEnd = Integer.parseInt(stripEndString);
            int blastProtection = Integer.parseInt(blastProtString);

            if (lda > tora) {
                new Alert(AlertType.NONE, "LDA cannot exceed TORA", ButtonType.OK).showAndWait();
            } else if (displacement > tora) {
                new Alert(AlertType.NONE, "Displacement Threshold cannot exceed TORA", ButtonType.OK).showAndWait();
            } else if (tora > toda) {
                new Alert(AlertType.NONE, "TORA cannot exceed TODA", ButtonType.OK).showAndWait();
            } else if (tora > asda) {
                new Alert(AlertType.NONE, "TORA cannot exceed ASDA", ButtonType.OK).showAndWait();
            } else {
                cal.setRunwayName(name);
                cal.setStatus(status);
                cal.setTORA(tora);
                cal.setASDA(asda);
                cal.setTODA(toda);
                cal.setLDA(lda);
                cal.setObstacleHeight(obHeight);
                cal.setObstacleDistanceFromThreshold(distance);
                cal.setObstacleDistanceFromCenter(obDistance);
                cal.setObstacleDirection(direction);
                cal.setDisplacementThreshold(displacement);
                cal.setRESA(resa);
                cal.setStripEnd(stripEnd);
                cal.setBlastProtection(blastProtection);
                cal.setALS(50);
                cal.setTOCS(50);
                cal.runCalculations();
                buttonClicked(cal, e);
            }
            //String o = obstacleHeightDirectionText.getText();
            //System.out.println(a);
        });
    }

    /**
     * Set the listener for when the calculate button is clicked
     * @param listener the listener to be set
     */
    public void setOnButtonClicked(CalculateButtonListener listener) {
        this.buttonClickedListener = listener;
    }

    /**
     * Calls the attached listener
     * @param cal new calculated values
     * @param event the button click event
     */
    private void buttonClicked(Calculations cal, ActionEvent event) {
        if (buttonClickedListener != null) {
            buttonClickedListener.calculateButtonClicked(cal, event);
        }
    }
}
