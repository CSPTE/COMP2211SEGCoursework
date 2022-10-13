package com.example.segproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class to ensure that the Calculations class correctly calculates
 * using the max of the RESA or height * 50 when taking off towards.
 * <p> i.e. TORA_1 = Obstacle distance - max(RESA, height * 50) - Strip end
 */
public class TakeoffTowardsHeightCaseTest {
    @Test
    public void SmallerHeightCaseTest() {
        var calc = new Calculations("01", "Takeoff", 3600, 3600, 3600, 3600, 2, 3600, "North", 0, 0, 240, 60, 300);
        //should be 3300 not 3440
        var expected = 3300;
        assertEquals(expected, calc.getNewTORA());
        assertEquals(expected, calc.getNewTODA());
        assertEquals(expected, calc.getNewASDA());
    }

    @Test
    public void LargerHeightCase() {
        var calc = new Calculations("01", "Takeoff", 3600, 3600, 3600, 3600, 10, 3600, "North", 0, 0, 240, 60, 300);
        //should be 3040 not 3300
        var expected = 3040;
        assertEquals(expected, calc.getNewTORA());
        assertEquals(expected, calc.getNewTODA());
        assertEquals(expected, calc.getNewASDA());
    }
}
