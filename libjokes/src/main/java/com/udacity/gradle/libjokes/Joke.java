package com.udacity.gradle.libjokes;


import java.io.Serializable;

public class Joke implements Serializable {

    private String text;
    private String category;

    public Joke(String text, String category) {
        this.text = text;
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public String getCategory() {
        return category;
    }
}
