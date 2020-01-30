package com.example.googlebookssearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.googlebookssearch.data.FetchData;
import com.example.googlebookssearch.fragments.LoadingFragment;
import com.example.googlebookssearch.fragments.ResultsListFragment;
import com.example.googlebookssearch.objects.Book;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

//TODO: menu won't slide out for this version yet
//TODO: hide YouTube API key
public class MainActivity extends AppCompatActivity
        implements FetchData.SearchListener, TextToSpeech.OnInitListener {


    boolean isConnected = false; //no connection until the below conditions are met

    //ArrayList of Book or Video objects
    String query;
    TextToSpeech mTTS;
    EditText searchEditText;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph()).build();
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        NavigationUI.setupWithNavController(toolbar, navController);
//
//
//        AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph())
//                        .setDrawerLayout(drawerLayout)
//                        .build();

        mTTS = new TextToSpeech(this, this);

        //When the search Button is pressed, a query is run
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listLayout, LoadingFragment.newInstance()).commit();
            query = searchEditText.getText().toString();
            new FetchData(MainActivity.this).execute(query);
            speakText(query);
            }
        });

        //EditText to enter search queries
        searchEditText = findViewById(R.id.searchEditText);
    }

    //Menu to switch between Book or Video Search:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_side, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.nav_books) {
            query = searchEditText.getText().toString();
            searchBooks(query);
            return true;
        }

        if (item.getItemId() == R.id.nav_videos) {
//            query = searchEditText.getText().toString();
//            searchVideos(query);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //FetchBooks.SearchListener required methods
    @Override
    public void OnResult(ArrayList<Book> books) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.listLayout, ResultsListFragment.newInstance(books)).commit();
        if(books.size() == 0){
            mTTS.speak("No Results Found. Please try another search.",
                    TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void ShowMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean hasConnection(){
        isConnected = false; //no connection until the below conditions are met

        ConnectivityManager mgr =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if (mgr != null){
            NetworkInfo info = mgr.getActiveNetworkInfo(); //permission to access network state
            if(info != null){
                isConnected = info.isConnected();
            }
        }
        if (!isConnected) {
            mTTS.speak("Not Connected to the Network",
                    TextToSpeech.QUEUE_FLUSH, null);
        }
        return isConnected;
    }

    @Override
    public File getCacheDirectory() {
        return getCacheDir();
    }

    //Text to Speech for Titles Selected in the spinner
    public void speakText(String text) {
        mTTS.setPitch(1);
        mTTS.setSpeechRate(1);
        mTTS.speak(String.format("Okay! Searching for %s", text),
                TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }

    //For TextToSpeech functionality
    @Override
    public void onInit(int status) {
        // change required.Initialization has to finish first.
        if (status == TextToSpeech.SUCCESS) {
            int result = mTTS.setLanguage(Locale.ENGLISH);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported");
            }
        } else {
            Log.e("TTS", "Initialization failed");
        }
    }

    //This function populates Book results.
    private void searchBooks(final String userQuery){
        mTTS = new TextToSpeech(this, this);

        //When the search Button is pressed, a query is run
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.listLayout, LoadingFragment.newInstance()).commit();
//                query = searchEditText.getText().toString();
                new FetchData(MainActivity.this).execute(userQuery);
                speakText(userQuery);
            }
        });
    }
    //This function populates Video results.
    private void searchVideos(final String userQuery){
        mTTS = new TextToSpeech(this, this);

        //When the search Button is pressed, a query is run
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.listLayout, LoadingFragment.newInstance()).commit();
//                query = searchEditText.getText().toString();
                new FetchData(MainActivity.this).execute(userQuery);
                speakText(userQuery);
            }
        });
    }
}
