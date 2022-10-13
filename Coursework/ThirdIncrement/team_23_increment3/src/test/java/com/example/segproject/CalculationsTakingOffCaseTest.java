package com.example.segproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class to ensure the correct case is chosen between taking off away and taking off towards
 * 
 * <p> When Object Distance is less than or equal to half of the TORA, case should be takeoff away. Takeoff towards otherwise.
 */
public class CalculationsTakingOffCaseTest {
    @Test
    public void NegativeDistanceCaseTest() {
        var calc = new Calculations("01", "Takeoff", 3600, 3600, 3600, 3600, 0, -50, "North", 0, 0, 300, 60, 300);
        //should be 3350 and not -410
        var expected = 3350;
        assertEquals(expected, calc.getNewTORA());
        assertEquals(expected, calc.getNewTODA());
        assertEquals(expected, calc.getNewASDA());
    }

    @Test
    public void LowDistanceCaseTest() {
        var calc = new Calculations("01", "Takeoff", 3600, 3600, 3600, 3600, 0, 100, "North", 0, 0, 300, 60, 300);
        //should be 3200 and not -260
        var expected = 3200;
        assertEquals(expected, calc.getNewTORA());
        assertEquals(expected, calc.getNewTODA());
        assertEquals(expected, calc.getNewASDA());
    }

    @Test
    public void MidDistanceCaseTest() {
        var calc = new Calculations("01", "Takeoff", 3600, 3600, 3600, 3600, 0, 1800, "North", 0, 0, 300, 60, 300);
        //should be 1500 and not 1440
        var expected = 1500;
        assertEquals(expected, calc.getNewTORA());
        assertEquals(expected, calc.getNewTODA());
        assertEquals(expected, calc.getNewASDA());
    }

    @Test
    public void HighDistanceCaseTest() {
        var calc = new Calculations("01", "Takeoff", 3600, 3600, 3600, 3600, 0, 3500, "North", 0, 0, 300, 60, 300);
        //should be 3140 and not -200
        var expected = 3140;
        assertEquals(expected, calc.getNewTORA());
        assertEquals(expected, calc.getNewTODA());
        assertEquals(expected, calc.getNewASDA());
    }

    @Test
    public void BeyondDistanceCaseTest() {
        var calc = new Calculations("01", "Takeoff", 3600, 3600, 3600, 3600, 0, 3700, "North", 0, 0, 300, 60, 300);
        //should be 3340 and not -400
        var expected = 3340;
        assertEquals(expected, calc.getNewTORA());
        assertEquals(expected, calc.getNewTODA());
        assertEquals(expected, calc.getNewASDA());
    }

}
