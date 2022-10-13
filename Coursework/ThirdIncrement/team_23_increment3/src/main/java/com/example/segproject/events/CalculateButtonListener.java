package com.example.segproject.events;

import com.example.segproject.Calculations;
import javafx.event.ActionEvent;

/**
 * The Calculations Input button clicked listener
 * Passes the new calculated values to the scene
 */
public interface CalculateButtonListener {

    public void calculateButtonClicked(Calculations cal, ActionEvent event);
}
