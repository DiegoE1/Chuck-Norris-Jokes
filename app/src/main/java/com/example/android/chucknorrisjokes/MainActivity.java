package com.example.android.chucknorrisjokes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button getJoke;
    private TextView joke;
    private ProgressBar indeterminateProgressBar;
    private RecyclerView jokesRecyclerView;
    private JokeAdapter jokeAdapter;

    private static final String EndPoint = "https://api.chucknorris.io/jokes/random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getJoke = (Button) findViewById(R.id.button_get_joke);
        joke = (TextView) findViewById(R.id.tv_return_joke);
        indeterminateProgressBar = (ProgressBar) findViewById(R.id.indeterminateBar);
        jokesRecyclerView = (RecyclerView) findViewById(R.id.rv_jokes);


        LinearLayoutManager layoutManager
               = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        jokesRecyclerView.setLayoutManager(layoutManager);
        jokesRecyclerView.setHasFixedSize(true);

        jokeAdapter = new JokeAdapter();
        jokesRecyclerView.setAdapter(jokeAdapter);



        getJoke.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                new ChuckQueryTask().execute();
            }
        });
    }

    public class ChuckQueryTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            indeterminateProgressBar.setVisibility(View.VISIBLE);
            getJoke.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(EndPoint).build();
            String chuckValue = null;
            try {
                Response response = client.newCall(request).execute();
                chuckValue = response.body().string();
                Log.d("tag", chuckValue);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String result = null;
            try {
                JSONObject jsonObject = new JSONObject(chuckValue);
                Log.d("tag", "jsonObject " + jsonObject.getString("value"));
                result = jsonObject.getString("value");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null) {
                Log.d("TAG", "onPostExecute() called with: s = [" + s + "]");
                indeterminateProgressBar.setVisibility(View.INVISIBLE);
                getJoke.setVisibility(View.VISIBLE);
                joke.setText(s);

                Log.d("RecyclerView Visible", s);
                jokeAdapter.setJokeData(s);
            }
        }
    }

}
