package com.example.googlebookssearch.data;

import android.os.AsyncTask;

import com.example.googlebookssearch.objects.Book;

import java.io.File;
import java.util.ArrayList;

public class FetchData extends AsyncTask<String, Void, String> {

    public interface SearchListener{
        void OnResult(ArrayList<Book> books);
        void ShowMessage(String message);
        boolean hasConnection();
        File getCacheDirectory();
    }

    private final SearchListener listener;

    public FetchData(SearchListener listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String queryString = strings[0];

        File file = new File (this.listener.getCacheDirectory(), queryString + ".json");
        if (!this.listener.hasConnection()){
            String fileContents = DataUtils.loadFile(file);
            if (fileContents != null){
                this.listener.ShowMessage("You are offline. Showing cached data.");
            } else {
                this.listener.ShowMessage("No Internet Connection.");
            }
            return fileContents;
        }


        String response = DataUtils.loadBookData(queryString);
        if (response != null){
            DataUtils.saveFile(file, response);
        } else {
            this.listener.ShowMessage("An error occurred.");
        }
        return response;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null){
            return;
        }

        ArrayList<Book> books = DataUtils.parseBookData(s);
        if(books != null){
            this.listener.OnResult(books);
        } else {
            this.listener.ShowMessage("An error occurred.");
        }

    }

}
