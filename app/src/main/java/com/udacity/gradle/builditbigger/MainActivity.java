package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.udacity.gradle.libjokes.Joke;
import com.udacity.gradle.libjokesandroid.JokeActivity;


public class MainActivity extends AppCompatActivity {

    private EndpointsAsyncTask mEndpointsAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mEndpointsAsyncTask != null && !mEndpointsAsyncTask.isCancelled()) {
            mEndpointsAsyncTask.cancel(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        mEndpointsAsyncTask = new EndpointsAsyncTask(new EndpointsAsyncTask.EndPointCallback() {
            @Override
            public void onJokeLoaded(Joke joke) {
                if(joke != null) {
                    JokeActivity.showJoke(getApplicationContext(), joke);
                } else {
                    Toast.makeText(MainActivity.this, "Joke not loaded.\n Please try again", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        mEndpointsAsyncTask.execute();
    }
}
