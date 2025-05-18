package com.wordle;

import lombok.Getter;

@Getter
public enum HighlightColor {
    GREEN("\u001B[42m"),
    YELLOW("\u001B[43m"),
    GRAY("\u001B[100m"),
    RESET("\u001B[0m");

    private final String code;

    HighlightColor(String code) {
        this.code = code;
    }

}