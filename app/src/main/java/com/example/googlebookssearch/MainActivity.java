package com.example.googlebookssearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.googlebookssearch.data.FetchBooks;
import com.example.googlebookssearch.fragments.LoadingFragment;
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

    }

    @Override
    public void ShowMessage(String message) {

    }

    @Override
    public boolean hasConnection() {
        return false;
    }

    @Override
    public File getCacheDirectory() {
        return null;
    }
}
