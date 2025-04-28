package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SongsAdapter adapter;
    private List<Song> songList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter and set it to the RecyclerView
        adapter = new SongsAdapter(songList, new SongsAdapter.OnSongClickListener() {
            @Override
            public void onPlayClick(Song song) {
                playSong(song.getUrl());
            }

            @Override
            public void onPauseClick() {
                pauseSong();
            }
        });
        recyclerView.setAdapter(adapter);

        // Fetch playlist data from the server
        fetchPlaylistData();
    }

    private void fetchPlaylistData() {
        new Thread(() -> {
            try {
                // Test with a publicly available JSON API (Replace this with your API URL)
                String urlString = "https://jsonplaceholder.typicode.com/posts"; // Testing URL for posts (acting as songs)
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setConnectTimeout(5000);  // Timeout for connection
                connection.setReadTimeout(5000);     // Timeout for reading data
                connection.connect();

                // Log the response code
                int responseCode = connection.getResponseCode();
                Log.d("PlaylistActivity", "Response Code: " + responseCode);

                // If the response code is not 200 (OK), handle the error
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e("PlaylistActivity", "Failed to connect: " + responseCode);
                    runOnUiThread(() -> Toast.makeText(PlaylistActivity.this, "Failed to load playlist", Toast.LENGTH_SHORT).show());
                    return;
                }

                // Get InputStream from the server response
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                StringBuilder stringBuilder = new StringBuilder();
                int data;
                while ((data = reader.read()) != -1) {
                    stringBuilder.append((char) data);
                }

                // Convert the stringBuilder to a String
                String jsonResponse = stringBuilder.toString();

                // Parse the JSON data
                JSONArray jsonArray = new JSONArray(jsonResponse);

                // Loop through the array and add each "song" (post) to the list
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject songObject = jsonArray.getJSONObject(i);
                    Song song = new Song(
                            songObject.getString("title"), // Using title as song title
                            songObject.getString("body"),  // Using body as artist (for example purposes)
                            "",                            // No URL in this example
                            180                            // Arbitrary duration, as this API doesn't provide duration
                    );
                    songList.add(song);
                }

                // Notify the adapter to update the UI after data is loaded
                runOnUiThread(() -> adapter.notifyDataSetChanged());

            } catch (Exception e) {
                // Log error and show a Toast message if the data fetching fails
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(PlaylistActivity.this, "Failed to load playlist", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void playSong(String songUrl) {
        // Implement play song logic here
        Log.d("PlaylistActivity", "Playing song from URL: " + songUrl);
    }

    private void pauseSong() {
        // Implement pause song logic here
        Log.d("PlaylistActivity", "Pausing song");
    }
}
