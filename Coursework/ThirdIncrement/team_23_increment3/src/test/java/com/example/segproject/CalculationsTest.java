package com.example.segproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests built from examples given in spec
 */
public class CalculationsTest {

    //TORA, ASDA, TODA, LDA, Obstacle height, Distance, Threshold Displacement,
    //Expected TORA, Expected ASDA, Expected TODA, Expected LDA
    private static Stream<Arguments> calculationsTestParamProvider() {
        return Stream.of(
            Arguments.of(3884, 3884, 3962, 3884, 12, 3646, 0, 2986, 2986, 2986, 3346), //27R Scenario 1
            Arguments.of(3902, 3902, 3902, 3595, 12, -50, 306, 3346, 3346, 3346, 2985), //09L Scenario 1
            Arguments.of(3660, 3660, 3660, 3353, 25, 2853, 307, 1850, 1850, 1850, 2553), //09R Scenario 2
            Arguments.of(3660, 3660, 3660, 3660, 25, 500, 0, 2860, 2860, 2860, 1850), //27L Scenario 2
            Arguments.of(3660, 3660, 3660, 3353, 15, 150, 307, 2903, 2903, 2903, 2393), //09R Scenario 3
            Arguments.of(3660, 3660, 3660, 3660, 15, 3203, 0, 2393, 2393, 2393, 2903), //27L Scenario 3
            Arguments.of(3902, 3902, 3902, 3595, 20, 3546, 306, 2792, 2792, 2792, 3246), //09L Scenario 4
            Arguments.of(3884, 3884, 3962, 3884, 20, 50, 0, 3534, 3534, 3612, 2774)
        );
    }

    @ParameterizedTest
    @MethodSource("calculationsTestParamProvider")
    @DisplayName("Correct distances for takeoff")
    public void testTakeOff(int tora, int asda, int toda, int lda, int obHeight, int obDistance, int displacement, int expTora, int expAsda, int expToda, int expLda) {
        var calculator = new Calculations("01", "Takeoff", tora, asda, toda, lda, obHeight, obDistance, "North", 0, displacement, 240, 60, 300);
        assertEquals(expTora, calculator.getNewTORA(), "TORA is incorrect");
        assertEquals(expAsda, calculator.getNewASDA(), "ASDA is incorrect");
        assertEquals(expToda, calculator.getNewTODA(), "TODA is incorrect");
    }

    @ParameterizedTest
    @MethodSource("calculationsTestParamProvider")
    @DisplayName("Correct distances for landing")
    public void testLanding(int tora, int asda, int toda, int lda, int obHeight, int obDistance, int displacement, int expTora, int expAsda, int expToda, int expLda) {
        var calculator = new Calculations("01", "Landing", tora, asda, toda, lda, obHeight, obDistance, "North", 0, displacement, 240, 60, 300);
        assertEquals(expLda, calculator.getNewLDA(), "LDA is incorrect");
    }
}