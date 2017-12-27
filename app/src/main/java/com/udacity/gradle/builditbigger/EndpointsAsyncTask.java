package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.libjokes.Joke;

import java.io.IOException;


class EndpointsAsyncTask extends AsyncTask<Void, Void, Joke> {
    private MyApi myApiService = null;
    private EndPointCallback mCallback;

    EndpointsAsyncTask(EndPointCallback callback) {
        mCallback = callback;
    }

    @Override
    protected Joke doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            com.udacity.gradle.builditbigger.backend.myApi.model.Joke joke = myApiService.joke().execute();
            return new com.udacity.gradle.libjokes.Joke(
                    joke.getText(),
                    joke.getCategory());
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Joke joke) {
        if(!this.isCancelled()) {
            mCallback.onJokeLoaded(joke);
        }
    }

    public interface EndPointCallback {
        void onJokeLoaded(Joke joke);
    }
}