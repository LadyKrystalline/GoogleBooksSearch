package com.example.googlebookssearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.googlebookssearch.data.FetchBooks;
import com.example.googlebookssearch.fragments.LoadingFragment;
import com.example.googlebookssearch.fragments.ResultsListFragment;
import com.example.googlebookssearch.objects.Book;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, FetchBooks.SearchListener {

    //ArrayList of Book objects
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Spinner for Book Query selection options
        Spinner querySelectSpinner = findViewById(R.id.querySelectSpinner);
        querySelectSpinner.setOnItemSelectedListener(this);

        //Spinner Adapter:
        arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.queryArray));
        querySelectSpinner.setAdapter(arrayAdapter);


    }

    //Spinner required methods
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.listLayout, LoadingFragment.newInstance()).commit();
        String query = arrayAdapter.getItem(i);
        new FetchBooks(this).execute(query);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //if nothing is selected, do nothing.
    }


    //FetchBooks.SearchListener required methods
    @Override
    public void OnResult(ArrayList<Book> books) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.listLayout, ResultsListFragment.newInstance(books)).commit();
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
        boolean isConnected = false; //no connection until the below conditions are met

        ConnectivityManager mgr =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if (mgr != null){
            NetworkInfo info = mgr.getActiveNetworkInfo(); //permission to access network state
            if(info != null){
                isConnected = info.isConnected();
            }
        }
        return isConnected;

    }

    @Override
    public File getCacheDirectory() {
        return getCacheDir();
    }
}
