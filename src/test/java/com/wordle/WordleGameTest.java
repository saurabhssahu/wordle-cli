package com.wordle;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WordleGameTest {

    @Test
    void testExactMatch_AllGreen() throws IOException {
        String guess = "WATER";
        String target = "WATER";
        String result = new WordleGame().evaluateGuess(guess, target);

        assertTrue(result.contains("\u001B[42m W \u001B[0m")); // Green for W
        assertTrue(result.contains("\u001B[42m A \u001B[0m")); // Green for A
        assertTrue(result.contains("\u001B[42m T \u001B[0m")); // etc.
        assertTrue(result.contains("\u001B[42m E \u001B[0m"));
        assertTrue(result.contains("\u001B[42m R \u001B[0m"));
    }

    @Test
    void testSomeCorrect_SomeWrong() throws IOException {
        String guess = "WOODS";
        String target = "WATER";
        String result = new WordleGame().evaluateGuess(guess, target);

        // W is correct and in position (green)
        assertTrue(result.contains("\u001B[42m W \u001B[0m"));

        // O, O, D and S not in target (should be gray)
        assertTrue(result.contains("\u001B[100m O \u001B[0m"));
        assertTrue(result.contains("\u001B[100m D \u001B[0m"));
        assertTrue(result.contains("\u001B[100m S \u001B[0m"));
    }

    @Test
    void testDuplicateLetters_ExtraCharacterShouldBeGray() throws IOException {
        String guess = "OTTER";
        String target = "WATER";
        String result = new WordleGame().evaluateGuess(guess, target);

        // 2 T's in guess, only 1 in target => first T should not be yellow
        // 2nd T (at index 2) is in correct position => green
        // 1st T should be gray, but not yellow if only one T in target
        assertTrue(result.contains("\u001B[42m T \u001B[0m")); // Green
        assertTrue(result.contains("\u001B[100m T \u001B[0m")); // One Gray T
        assertFalse(result.contains("\u001B[43m T \u001B[0m")); // No yellow T
    }

    @Test
    void testYellowOnlyOnce_WhenDuplicateLetters() throws IOException {
        String guess = "FRUIT";
        String target = "WATER";
        String result = new WordleGame().evaluateGuess(guess, target);

        // F not in target (should be gray)
        assertTrue(result.contains("\u001B[100m F \u001B[0m"));

        // R & T in target but not in the correct position (should be yellow)
        assertTrue(result.contains("\u001B[43m R \u001B[0m")); // yellow R
        assertTrue(result.contains("\u001B[43m T \u001B[0m")); // yellow T

        // U & I not in target (should be gray)
        assertTrue(result.contains("\u001B[100m U \u001B[0m"));
        assertTrue(result.contains("\u001B[100m I \u001B[0m"));
    }

    @Test
    void testGuessWithNoCorrectLetters_AllGray() throws IOException {
        String guess = "PLUMB";
        String target = "WATER";
        String result = new WordleGame().evaluateGuess(guess, target);

        assertTrue(result.contains("\u001B[100m P \u001B[0m"));
        assertTrue(result.contains("\u001B[100m L \u001B[0m"));
        assertTrue(result.contains("\u001B[100m U \u001B[0m"));
        assertTrue(result.contains("\u001B[100m M \u001B[0m"));
        assertTrue(result.contains("\u001B[100m B \u001B[0m"));
    }
}