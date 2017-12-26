package com.udacity.gradle.libjokesandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.udacity.gradle.libjokes.Joke;

public class JokeActivity extends AppCompatActivity {

    public static final String PARAM_JOKE = "param_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Joke joke = (Joke) getIntent().getSerializableExtra(PARAM_JOKE);

        TextView jokeText = findViewById(R.id.joke_ac_joke_text_textview);
        TextView jokeCategory = findViewById(R.id.joke_ac_joke_category_textview);

        jokeText.setText(joke.getText());
        jokeCategory.setText(joke.getCategory());
    }

    public static void showJoke(Context context, Joke joke) {
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(PARAM_JOKE, joke);
        context.startActivity(intent);
    }
}
