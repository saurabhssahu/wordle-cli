package com.wordle;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * @param guess the user's guessed word
     * @param target the target word to match
     * @return the formatted feedback string with ANSI colors
     */
    public String evaluateGuess(String guess, String target) {
        guess = guess.toUpperCase();

        char[] guessChars = guess.toCharArray();
        char[] targetChars = target.toCharArray();

        // Track frequency of each character in the target
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : targetChars) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        // First pass: mark correct letters (green)
        HighlightColor[] colors = new HighlightColor[5];
        for (int i = 0; i < 5; i++) {
            if (guessChars[i] == targetChars[i]) {
                colors[i] = HighlightColor.GREEN;
                charCount.put(guessChars[i], charCount.get(guessChars[i]) - 1);
            }
        }

        // Second pass: mark misplaced letters (yellow) or incorrect (gray)
        for (int i = 0; i < 5; i++) {
            if (colors[i] == null) {
                char c = guessChars[i];
                if (charCount.getOrDefault(c, 0) > 0) {
                    colors[i] = HighlightColor.YELLOW;
                    charCount.put(c, charCount.get(c) - 1);
                } else {
                    colors[i] = HighlightColor.GRAY;
                }
            }
        }

        // Build feedback string with color codes
        StringBuilder feedback = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            feedback.append(colorWrap(guessChars[i], colors[i]));
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