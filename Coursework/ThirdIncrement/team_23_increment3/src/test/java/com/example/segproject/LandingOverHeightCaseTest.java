package com.example.segproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class to ensure that the Calculations class correctly calculates
 * using the max of the RESA or height * 50 when landing over
 * <p> i.e. LDA_1 = LDA_0 - max(RESA, height * 50) - Strip end - Obstacle distance
 */
public class LandingOverHeightCaseTest {
    @Test
    public void SmallerHeightCaseTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 2, 100, "North", 0, 0, 240, 60, 300);
        //should be 3200 not 3340
        assertEquals(3200, calc.getNewLDA());
    }

    @Test
    public void LargerHeightCase() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 10, 100, "North", 0, 0, 240, 60, 300);
        //should be 2940 not 3200
        assertEquals(2940, calc.getNewLDA());
    }
}
