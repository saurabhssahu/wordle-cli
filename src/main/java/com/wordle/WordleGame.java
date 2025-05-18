package com.wordle;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * Core logic for the Wordle Game.
 */
@Getter
public class WordleGame {

    private final String targetWord;
    private static final int MAX_ATTEMPTS = 5;
    private static final int WORD_LENGTH = 5;
    private static final String WORD_LIST_PATH = "src/main/resources/words.txt";

    public WordleGame() throws IOException {
        List<String> words = Files.readAllLines(Paths.get(WORD_LIST_PATH));
        List<String> validWords = words.stream()
                .map(String::toUpperCase)
                .filter(word -> word.length() == WORD_LENGTH)
                .toList();
        this.targetWord = validWords.get(new Random().nextInt(validWords.size()));
    }

    /**
     * Evaluates the guess against the target word.
     *
     * @param guess the guessed word
     * @return the formatted feedback string with ANSI colors
     */
    public String evaluateGuess(String guess) {
        guess = guess.toUpperCase();
        char[] target = targetWord.toCharArray();
        char[] guessChars = guess.toCharArray();
        boolean[] matchedTarget = new boolean[WORD_LENGTH];
        boolean[] matchedGuess = new boolean[WORD_LENGTH];
        StringBuilder feedback = new StringBuilder();

        // First pass: correct position (GREEN)
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (guessChars[i] == target[i]) {
                matchedTarget[i] = true;
                matchedGuess[i] = true;
                feedback.append(colorWrap(guessChars[i], HighlightColor.GREEN));
            } else {
                feedback.append("_"); // placeholder to replace later
            }
        }

        // Second pass: wrong position (YELLOW) or not in word (GRAY)
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (matchedGuess[i]) continue;

            boolean found = false;
            for (int j = 0; j < WORD_LENGTH; j++) {
                if (!matchedTarget[j] && guessChars[i] == target[j]) {
                    matchedTarget[j] = true;
                    found = true;
                    break;
                }
            }

            HighlightColor color = found ? HighlightColor.YELLOW : HighlightColor.GRAY;
            int start = i * 9; // each formatted block is 9 characters long: "\u001B[xxm X \u001B[0m"
            feedback.replace(start, start + 9, colorWrap(guessChars[i], color));
        }

        return feedback.toString();
    }

    private String colorWrap(char letter, HighlightColor color) {
        return color.getCode() + " " + letter + " " + HighlightColor.RESET.getCode();
    }


    public int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }

    public static int getWordLength() {
        return WORD_LENGTH;
    }
}