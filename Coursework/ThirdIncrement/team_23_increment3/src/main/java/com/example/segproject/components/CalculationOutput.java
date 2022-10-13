package com.example.segproject.components;

import com.example.segproject.Calculations;
import com.example.segproject.events.CalculateButtonListener;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * UI component for displaying the calculated values
 */
public class CalculationOutput extends VBox {
    Calculations cal;

    private Text runwayName;
    private Text TORA;
    private Text TORACalc;
    private Text ASDA;
    private Text ASDACalc;
    private Text TODA;
    private Text TODACalc;
    private Text LDA;
    private Text LDACalc;
    private Text slopeCalc;
    //private Text obstacleDirection;
    //private Text obstacleDistanceFromCenter;
    //private Text displacementThreshold;

    /**
     * Instantiates the labels
     */
    public CalculationOutput(){
        this.runwayName = new Text("Runway Designator: ");
        this.TORA = new Text("TORA: ");
        this.ASDA = new Text("ASDA: ");
        this.TODA = new Text("TODA: ");
        this.LDA = new Text("LDA: ");
        this.TORACalc = new Text("TORA Calculation: ");
        this.ASDACalc = new Text("ASDA Calculation: ");
        this.TODACalc = new Text("TODA Calculation: ");
        this.LDACalc = new Text("LDA Calculation: ");
        this.slopeCalc = new Text("Slope Calculation: ");
        //this.obstacleDirection = new Text("Obstacle Direction: ");
        //this.obstacleDistanceFromCenter = new Text("Obstacle Distance from Center: ");
        //this.displacementThreshold = new Text("Displacement Threshold: ");

        this.getChildren().add(this.runwayName);
        this.getChildren().add(this.TORA);
        this.getChildren().add(this.ASDA);
        this.getChildren().add(this.TODA);
        this.getChildren().add(this.LDA);
        this.getChildren().add(this.TORACalc);
        this.getChildren().add(this.ASDACalc);
        this.getChildren().add(this.TODACalc);
        this.getChildren().add(this.LDACalc);
        this.getChildren().add(this.slopeCalc);
        //this.getChildren().add(this.obstacleDirection);
        //this.getChildren().add(this.obstacleDistanceFromCenter);
        //this.getChildren().add(this.displacementThreshold);
    }

    /**
     * Updates the values of each label when values are available
     * @param cal the values to be updated
     */
    public void updateValues(Calculations cal) {
        this.cal = cal;
        this.runwayName.setText("Runway Designator: " + cal.getRunwayName());
        this.TORA.setText("TORA: " + cal.getNewTORA() + " Meters");
        this.ASDA.setText("ASDA: " + cal.getNewASDA() + " Meters");
        this.TODA.setText("TODA: " + cal.getNewTODA() + " Meters");
        this.LDA.setText("LDA: " + cal.getNewLDA() + " Meters");
        //this.obstacleDirection.setText("Obstacle Direction: " + cal.getObstacleDirection());
        //this.obstacleDistanceFromCenter.setText("Obstacle Distance from Center: " + cal.getObstacleDistanceFromCenter() + " Meters");
        //this.displacementThreshold.setText("Displacement Threshold: " + cal.getDisplacementThreshold() + " Meters");

        this.TORACalc.setText(cal.getTORACalc());
        this.ASDACalc.setText(cal.getASDACalc());
        this.TODACalc.setText(cal.getTODACalc());
        this.LDACalc.setText(cal.getLDACalc());
        this.slopeCalc.setText(cal.getSlopeCalc());
    }
}
