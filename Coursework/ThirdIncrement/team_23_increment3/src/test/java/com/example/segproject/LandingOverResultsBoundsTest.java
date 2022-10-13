package com.example.segproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class that ensures the new LDA is bounded above by the original
 * when landing over.
 * 
 * <p> The new LDA will be larger than original when the distance is negative
 * and the absolute value is larger than max(RESA, height) + Strip end. We want to cap this.
 */
public class LandingOverResultsBoundsTest {
    @Test
    public void LargerDistanceBoundsTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 0, -1000, "North", 0, 0, 240, 60, 300);
        //should be 3600 not 4300
        assertEquals(3600, calc.getNewLDA());
    }

    @Test
    public void EqualDistanceBoundsTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 0, -300, "North", 0, 0, 240, 60, 300);
        //should be 3600 for both here
        assertEquals(3600, calc.getNewLDA());
    }

    @Test
    public void SmallerDistanceBoundsTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 0, -100, "North", 0, 0, 240, 60, 300);
        //should be 3400 not 3600 here
        assertEquals(3400, calc.getNewLDA());
    }
}