package com.example.segproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class that ensures landing chooses the correct case between landing over and landing towards
 * 
 * <p> When Object Distance is less than or equal to half of the TORA, case should be landing over. Landing towards otherwise.
 */
public class CalculationsLandingCaseTest {
    @Test
    public void NegativeDistanceCaseTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 0, -50, "North", 0, 0, 240, 60, 300);
        //should be 3350 instead of -350
        assertEquals(3350, calc.getNewLDA());
    }

    @Test
    public void LowDistanceCaseTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 0, 100, "North", 0, 0, 240, 60, 300);
        //should be 3200 instead of -200
        assertEquals(3200, calc.getNewLDA());
    }

    @Test
    public void MidDistanceCaseTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 0, 1800, "North", 0, 0, 240, 60, 300);
        //case doesn't matter here, both should be 1800
        assertEquals(1500, calc.getNewLDA());
    }

    @Test
    public void HighDistanceCaseTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 0, 3500, "North", 0, 0, 240, 60, 300);
        //should be 3200 instead of -200
        assertEquals(3200, calc.getNewLDA());
    }

    @Test
    public void BeyondDistanceCaseTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 0, 3700, "North", 0, 0, 240, 60, 300);
        //should be 3400 instead of -400
        assertEquals(3400, calc.getNewLDA());
    }
}
