package com.example.segproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class to ensure that the new TORA, ASDA, and TODA
 * are bound above by the originals when
 * taking off away.
 * 
 * <p> The new values will be larger when the obstacle distance is negative 
 * and the absolute distance size is larger than the blast protection.
 * We want to cap this to the originals.
 */
public class TakeoffAwayResultsBoundsTest {
    @Test
    public void LargerDistanceBoundsTest() {
        var calc = new Calculations("01", "Takeoff", 3600, 3600, 3600, 3600, 0, -500, "North", 0, 0, 240, 60, 300);
        //should be 3600 not 3800
        var expected = 3600;
        assertEquals(expected, calc.getNewTORA());
        assertEquals(expected, calc.getNewTODA());
        assertEquals(expected, calc.getNewASDA());
    }

    @Test
    public void EqualDistanceBoundsTest() {
        var calc = new Calculations("01", "Takeoff", 3600, 3600, 3600, 3600, 0, -300, "North", 0, 0, 240, 60, 300);
        //should be 3600 either way
        var expected = 3600;
        assertEquals(expected, calc.getNewTORA());
        assertEquals(expected, calc.getNewTODA());
        assertEquals(expected, calc.getNewASDA());
    }

    @Test
    public void SmallerDistanceBoundsTest() {
        var calc = new Calculations("01", "Takeoff", 3600, 3600, 3600, 3600, 0, -100, "North", 0, 0, 240, 60, 300);
        //should be 3400 not 3600
        var expected = 3400;
        assertEquals(expected, calc.getNewTORA());
        assertEquals(expected, calc.getNewTODA());
        assertEquals(expected, calc.getNewASDA());
    }
}
