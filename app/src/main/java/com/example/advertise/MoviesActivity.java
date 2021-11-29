package com.example.advertise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MoviesActivity extends AppCompatActivity {
    // Parse the most popular movies using the API for Popular movies
    private static String JSON_URL ="https://api.themoviedb.org/3/movie/popular?api_key=c2238d0d01aa38ca10a288dee46ae575";

//    private static String JSON_URL = "https://run.mocky.io/v3/8205ce05-5ac2-4801-8afd-0f190f4f136c";
    List<MovieModelClass> movieList;
    RecyclerView recyclerView;
    ImageView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        header = findViewById(R.id.header_image);
        //Displaying Image
        Glide.with(this).load(R.drawable.thegodfather).into(header);

        // Creating the Async Task
        GetData getData = new GetData();
        getData.execute();
    }
    // Getting JSON data
    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try{
                URL url;
                HttpsURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpsURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while(data != -1){
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        // analise the string get the json object pass to model class to adapter class which will display in the recyclerview
        @Override
        protected void onPostExecute(String s) {
            // going throught the json (the array is called moviz)  inside of moviz are the json objects id, name, image
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                // go through the array of objects with a for loop
                for (int i = 0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    MovieModelClass model = new MovieModelClass();
                    // Inside the movie model class lets put the objects we have created in the model class into the list we created
                    // For APIS we need to look at the name of what it returns and add it here..
                    model.setId(jsonObject1.getString("vote_average"));
                    model.setImg(jsonObject1.getString("poster_path"));
                    model.setName(jsonObject1.getString("title"));

                    movieList.add(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(movieList);
        }
    }

    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList){
        AdapterClass adapterClass = new AdapterClass(this,movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        recyclerView.setAdapter(adapterClass);
    }

}