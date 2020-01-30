package com.example.googlebookssearch.data;

import android.net.Uri;
import android.util.Log;

import com.example.googlebookssearch.objects.Book;
import com.example.googlebookssearch.objects.YTVideo;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

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
import java.util.Arrays;
import java.util.Collection;

class DataUtils {

    //This loads the data from a file in the local device directory
    private static final String TAG = "DataUtils.java";


    private static final String CLIENT_SECRETS= "client_secret.json";
    private static final Collection<String> SCOPES =
            Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl");

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();


    private static final String CLIENT_ID = "790428d9452f4f34a57352bc4cae218b";
    private static final String CLIENT_SECRET = "d80a168938b64fb1b5e1d54bf71da5d7";

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
    public static String loadBookData(String query){

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
    public static ArrayList<Book> parseBookData(String data){

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
                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    String coverImage = imageLinks.getString("smallThumbnail");

                    //DESCRIPTION: "description" JSON String from a JSON object
                    String description = volumeInfo.getString("description");

                    //create Book from JSON
                    books.add(new Book(coverImage, title,
                            authors.getString(0), publishedDate, description));

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            return books;
        } catch (JSONException e){
            return null;
        }
    }

    //https://developers.google.com/apis-explorer/#p/youtube/v3/youtube.search.list?
    //        part=snippet
    //        &order=viewCount
    //        &q=[query]
    //        &type=video

    //This loads data from the API
    public static String loadVideoData(String query){

        HttpURLConnection connection = null;

        try {
            String baseURL =
                "https://developers.google.com/apis-explorer/#p/youtube/v3/youtube.search.list?" +
                        "part=snippet&order=viewCount";

            Uri queryURI = Uri.parse(baseURL).buildUpon()
                    .appendQueryParameter("q", String.format("%s&type=video", query))
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

    //Parses the JSON data into YTVideo objects
    public static ArrayList<YTVideo> parseVideoData(String data){

        try {
            ArrayList<YTVideo> videos = new ArrayList<>();
            //get JSON object
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                try {
                    JSONObject video = itemsArray.getJSONObject(i);

                    //snippet info
                    JSONObject snippet = video.getJSONObject("snippet");

                    //TITLE: JSON String from a JSON object
                    String title = snippet.getString("title");

                    //CHANNEL: JSON Array from a JSON object
                    String channel = snippet.getString("channelTitle");

                    //RELEASE DATE: JSON String from a JSON object
                    DateTime releaseDate =
                            DateTime.parseRfc3339(snippet.get("publishedAt").toString());

                    //VIDEO THUMBNAIL: JSON Array from a JSON object
                    JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                    JSONObject key = thumbnails.getJSONObject("default");
                    String thumbImage = key.getString("url");

                    //VIDEO LENGTH: "videoLength" JSON String from a JSON object
                    String description = snippet.getString("description");

                    //Create YTVideo from JSON
                    videos
                        .add(new YTVideo(thumbImage, title, channel, releaseDate.toStringRfc3339(), description));

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            return videos;
        } catch (JSONException e){
            return null;
        }
    }



}
