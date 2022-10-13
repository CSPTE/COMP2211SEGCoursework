package com.example.segproject.components;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * An input for Runway Designators with format of an
 * integer from 01 to 36 then optionally a
 * choice from L, C or R
 * <p>
 * e.g. 14R, 35, 01L
 */
public class RunwayDesignatorInput extends HBox {
    Spinner<Integer> spinner;
    ChoiceBox<String> positionChoice;
    CheckBox positionCheck;

    public RunwayDesignatorInput() {
        spinner = new Spinner<Integer>(1, 36, 1);
        getChildren().add(spinner);

        positionCheck = new CheckBox("Has position");
        positionCheck.setAllowIndeterminate(false);
        positionCheck.setSelected(false);
        getChildren().add(positionCheck);

        positionChoice = new ChoiceBox<String>();
        positionChoice.getItems().addAll("L", "C", "R");
        positionChoice.setValue("L");
        positionChoice.disableProperty().bind(positionCheck.selectedProperty().not());
        getChildren().add(positionChoice);
    }

    /**
     * Return the entered input for the runway designator
     * in String form
     * @return runway designator string
     */
    public String getText() {
        String outStr = this.spinner.getValue().toString();
        if (outStr.length()==1) {
            outStr = "0".concat(outStr);
        }
        if (positionCheck.isSelected()) {
            outStr = outStr.concat(positionChoice.getValue());
        }
        return outStr;
    }
}
