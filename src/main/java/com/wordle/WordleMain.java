package com.wordle;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@Slf4j
public class WordleMain {
    public static void main(String[] args) {

        log.info("Welcome to Wordle!");
        log.info("Guess the 5-letter word. You have 5 attempts.");

        log.info("Correct letter & position = " + "\u001B[42m GREEN \u001B[0m");
        log.info("Correct letter, wrong position = " + "\u001B[43m YELLOW \u001B[0m");


        try (Scanner scanner = new Scanner(System.in)) {
            WordleGame game = new WordleGame();
            int attempts = 0;
            boolean guessedCorrectly = false;

            while (attempts < game.getMaxAttempts()) {
                log.info("Attempt {}/{}: Enter your guess: ", attempts + 1, game.getMaxAttempts());
                String guess = scanner.nextLine().trim();

                if (guess.length() == WordleGame.getWordLength()) {
                    String feedback = game.evaluateGuess(guess);
                    log.info("Result: {}", feedback);
                    log.info("feedback length : {}", feedback.length());
                    attempts++;

                    if (guess.equalsIgnoreCase(game.getTargetWord())) {
                        log.info("Congratulations! You guessed it right.");
                        guessedCorrectly = true;
                        break;
                    }
                } else {
                    log.info("Please enter exactly 5 characters.\n");
                }
            }

            if (!guessedCorrectly) {
                log.info("You've used all attempts. The word was: {}", game.getTargetWord());
            }

        } catch (IOException e) {
            log.error("Failed to load words: {}", e.getMessage());
        }
    }
}