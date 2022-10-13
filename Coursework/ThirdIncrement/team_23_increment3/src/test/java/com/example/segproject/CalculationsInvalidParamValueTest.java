package com.example.segproject;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class that checks for the Calculations class not accepting invalid parameters
 * 
 * <p> VALUES TABLE: 
 * <p> 0 <= TORA
 * <p> 0 <= ASDA
 * <p> TORA <= TODA
 * <p> 0 <= LDA <= TORA
 * <p> 0 <= Obstacle Height
 * <p> 0 <= Threshold Displacement < TORA
 * <p> 240 <= RESA < TORA
 * <p> 60 <= Strip End < TORA
 * <p> 0 <= Blast Protection < TORA
 */
public class CalculationsInvalidParamValueTest {
    @Test
    @DisplayName("Valid TORA value test")
    public void TORAValidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", -1, 3660, 3660, 3353, 0, 0, "North", 0, 307, 240, 60, 300);
        }, "Calculations should not accept negative values for TORA"); //test for exception on negative tora value
    }

    @Test
    @DisplayName("Valid TODA Value Test")
    public void TODAValidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3659, 3353, 0, 0, "North", 0, 307, 240, 60, 300);
        }, "Calculations should not accept values less than TORA for TODA"); //test for exception on toda < tora
    }

    @Test
    @DisplayName("Valid ASDA Value Test")
    public void ASDAValidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, -1, 3660, 3353, 0, 0, "North", 0, 307, 240, 60, 300);
        }, "Calculations should not accept negative values for ASDA"); // test for exception on negative asda
    }

    @Test
    @DisplayName("Valid LDA Value Test")
    public void LDAValidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3660, -1, 0, 0, "North", 0, 307, 240, 60, 300);
        }, "Calculations should not accept negative values for LDA"); // test for exception on negative lda
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3660, 3661, 0, 0, "North", 0, 307, 240, 60, 300);
        }, "Calculations should not accept values larger than TORA for LDA"); // test for exception on lda > tora
    }

    @Test
    @DisplayName("Valid Strip End Value Test")
    public void StripEndValidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3660, 3353, 0, 0, "North", 0, 307, 240, 59, 300);
        }, "Calculations should not accept values < 60 for strip end"); // test for exception on strip end < 60
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3660, 3353, 0, 0, "North", 0, 307, 240, 3661, 300);
        }, "Calculations should not accept values larger than TORA for strip end"); // test for exception on strip end > tora
    }

    @Test
    @DisplayName("Valid RESA Value Test")
    public void RESAValidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3660, 3353, 0, 0, "North", 0, 307, 239, 60, 300);
        }, "Calculations should not accept values < 240 for RESA"); // test for exception on RESA < 240
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3660, 3353, 0, 0, "North", 0, 307, 3661, 60, 300);
        }, "Calculations should not accept values larger than TORA for RESA"); // test for exception on RESA > tora
    }

    @Test
    @DisplayName("Valid Threshold Displacement Value Test")
    public void DisplacementValidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3660, 3353, 0, 0, "North", 0, -1, 240, 60, 300);
        }, "Calculations should not accept negative values for threshold displacement"); // test for exception on negative displacement
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3660, 3353, 0, 0, "North", 0, 3661, 240, 60, 300);
        }, "Calculations should not accept values larger than TORA for threshold displacememt"); // test for exception on displacement > tora
    }

    @Test
    @DisplayName("Valid Obstacle Height Value Test")
    public void HeightValidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3660, 3353, -1, 0, "North", 0, 307, 240, 60, 300);
        }, "Calculations should not accept negative values for obstacle height"); // test for exception on negative height
    }

    @Test
    @DisplayName("Valid Blast Protection Value Test")
    public void BlastValidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3660, 3353, 0, 0, "North", 0, 307, 240, 60, -1);
        }, "Calculations should not accept negative values for blast protection"); // test for exception on negative displacement
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "Takeoff", 3660, 3660, 3660, 3353, 0, 0, "North", 0, 307, 240, 60, 3661);
        }, "Calculations should not accept values larger than TORA for blast protection"); // test for exception on displacement > tora
    }

    @Test
    @DisplayName("Valid Status Value Test")
    public void StatusValidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Calculations("01", "", 3660, 3660, 3660, 3353, 0, 0, "North", 0, 307, 240, 60, 300);
        }, "Calculations should not accept values that aren't 'Takeoff' or 'Landing' for status");
    }
}
