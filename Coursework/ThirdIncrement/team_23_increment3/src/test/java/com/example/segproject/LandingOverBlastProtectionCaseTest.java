package com.example.segproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LandingOverBlastProtectionCaseTest {
    @Test
    public void BlastLargerHeightSmallerCaseTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 2, 100, "North", 0, 0, 240, 60, 600);
        //should be 2900 not 3200 or 3340
        assertEquals(2900, calc.getNewLDA());
    }

    @Test
    public void BlastLargerHeightLargerCaseTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 10, 100, "North", 0, 0, 240, 60, 600);
        //should be 2900 not 3200 or 2940
        assertEquals(2900, calc.getNewLDA());
    }

    @Test
    public void BlastSmallerHeightSmallerCaseTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 2, 100, "North", 0, 0, 240, 60, 200);
        //should be 3200 not 3300 or 3340
        assertEquals(3200, calc.getNewLDA());
    }

    @Test
    public void BlastSmallerHeightLargerCaseTest() {
        var calc = new Calculations("01", "Landing", 3600, 3600, 3600, 3600, 10, 100, "North", 0, 0, 240, 60, 500);
        //should be 2940 not 3000 or 3200
        assertEquals(2940, calc.getNewLDA());
    }
}
