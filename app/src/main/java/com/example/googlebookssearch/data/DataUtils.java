package com.example.googlebookssearch.data;

import android.net.Uri;
import android.util.Log;

import com.example.googlebookssearch.objects.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class DataUtils {

    //This loads the data from a file in the local device directory
    private static final String TAG = "DataUtils.java";
    public static String loadFile(File file){
        if(!file.exists()){
            return null;
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            if (stringBuilder.length() == 0){
                return null;
            }
            return stringBuilder.toString();
        } catch (IOException e){
            return null;
        }

    }

    //This saves the data to a file in the local device directory
    public static void saveFile(File file, String data){

        if (file.getParentFile().mkdirs()){
            //TRUE: if and only if the directory was created, along with all necessary parent directories
            Log.d(TAG, "directory created");
        } else {
            //FALSE: if said directories already exist
            Log.d(TAG, "directory exists");
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This loads data from the API
    public static String loadData(String query){

        HttpURLConnection connection = null;

        try {
            String baseURL = "https://www.googleapis.com/books/v1/volumes";

            Uri queryURI = Uri.parse(baseURL).buildUpon()
                    .appendQueryParameter("q", query)
                    .build();
            URL requestURL = new URL(queryURI.toString());
            connection = (HttpURLConnection) requestURL.openConnection();

            connection.connect();

            if (connection.getResponseCode() >= 300) {
                return null;
            }

            try (BufferedReader bufferedReader =
                         new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                //read into the string builder
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }

                if (stringBuilder.length() == 0){
                    return null;
                }
                return stringBuilder.toString(); //returns contents of Http response
            }
        } catch (IOException e) {
            return null;
        } finally {
            //close connection when not being used
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    //Parses the JSON data into Book objects
    public static ArrayList<Book> parseData(String data){

        try {
            ArrayList<Book> books = new ArrayList<>();
            //get JSON object
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                try {
                    JSONObject book = itemsArray.getJSONObject(i);

                    //volume info
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                    //TITLE: JSON String from a JSON object
                    String title = volumeInfo.getString("title");

                    //AUTHOR: JSON Array from a JSON object
                    JSONArray authors = volumeInfo.getJSONArray("authors");

                    //PUBLISHED DATE: JSON String from a JSON object
                    String publishedDate = volumeInfo.getString("publishedDate");

                    //COVER THUMBNAIL: JSON Array from a JSON object
                    JSONArray imageLinks = volumeInfo.getJSONArray("imageLinks");

                    //create Book from JSON
                    books.add(new Book(coverImage, title, authors.getString(0), publishedDate));

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            return books;
        } catch (JSONException e){
            return null;
        }
    }
}
