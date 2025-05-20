# Wordle CLI Game (Java)

This is a command-line version of the popular game **Wordle**, implemented in **Java 21** using **Maven**. The game
selects a random 5-letter word from a predefined word list and gives the player **5 attempts** to guess it correctly.
After each guess, the game provides feedback using colored highlights:

* 🟩 **Green** — Correct letter in the correct position
* 🟨 **Yellow** — Correct letter but in the wrong position
* ⬜ **Gray** — Letter not in the word

---

## 📜 Game Rules

* All words must be exactly **5 letters** long.
* The player has **5 guesses** to find the correct word.
* Feedback is shown using ANSI color codes:

    * Green for correct letter in correct place.
    * Yellow for correct letter in wrong place.
    * Gray for letters not in the word.
* If the guessed word contains more instances of a letter than the target word, only the correct count is highlighted.

    * Example: If the correct word is `WATER` and the guess is `OTTER`, only **one** `T` will be marked (the one in the
      correct position).

---

## 🧪 Unit Testing

* The project includes **JUnit 5** test cases that cover:

    * Exact matches.
    * Partial matches.
    * Rules for duplicate letters.
    * All incorrect letters.

---

## 📁 Project Structure

```
wordle-cli-game/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/wordle/
│   │   │       ├── WordleGame.java
│   │   │       ├── HighlightColor.java
│   │   │       └── WordleMain.java
│   │   └── resources/
│   │       └── words.txt
│   └── test/
│       └── java/
│           └── com/wordle/
│               └── WordleGameTest.java
```

---

## ▶️ How to Run

Ensure you have **Java 21** and **Maven** installed. Then run:

```bash
mvn clean compile exec:java -Dexec.mainClass="com.wordle.WordleMain"
```

---

## 📝 Word List

The words are loaded from the `words.txt` file inside `src/main/resources/`. Each word must be 5 letters long.

Example content:

```
water
hound
eagle
pizza
fruit
paper
otter
```

---

## 🧠 Feedback Coloring (ANSI)

The ANSI escape codes are used to colorize terminal output:

* Green: `\u001B[42m`
* Yellow: `\u001B[43m`
* Gray (default): `\u001B[100m`

After each letter, we reset the format using `\u001B[0m`.

---

## 📌 Notes

* Guesses are allowed even if the word is not present in the word list.
* The input is validated to ensure it is 5 characters long.
* No network communication is used.
* Designed for clarity, correctness, and assessment readability.
