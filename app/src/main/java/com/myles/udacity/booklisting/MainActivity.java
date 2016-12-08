package com.myles.udacity.booklisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String BOOK_API_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Empty view for listview
        ((ListView) this.findViewById(R.id.list)).setEmptyView((TextView) findViewById(R.id.empty_view));

        //Setup button onclick listener to activate the task
        ((Button) findViewById(R.id.button_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchQuery = ((TextView) findViewById(R.id.text_input)).getText().toString();
                BookAsyncTask task = new BookAsyncTask();

                URL searchUrl = null;
                try {
                    searchUrl = new URL(BOOK_API_REQUEST_URL + searchQuery);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                task.execute(searchUrl);
            }
        });


    }

    private class BookAsyncTask extends AsyncTask<URL, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(URL... urls) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                return null;
            }

            URL url = urls[0];
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            List<Book> books = extractFeatureFromJson(jsonResponse);
            return books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            if (books == null) {
                return;
            }
            BookAdapter adapter = new BookAdapter(MainActivity.this, books);
            ((ListView) findViewById(R.id.list)).setAdapter(adapter);

        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        private List<Book> extractFeatureFromJson(String json) {
            ArrayList<Book> books = new ArrayList<Book>();
            try {
                JSONObject baseJsonResponse = new JSONObject(json);
                JSONArray featureArray = baseJsonResponse.getJSONArray("items");

                // If there are results in the features array
                for (int i = 0; i < featureArray.length(); i++) {
                    JSONObject firstFeature = featureArray.getJSONObject(i);
                    JSONObject properties = firstFeature.getJSONObject("volumeInfo");

                    String title = properties.getString("title");
                    String publisher = properties.getString("publisher");
                    JSONArray authorList = properties.getJSONArray("authors");
                    ArrayList<String> authors = new ArrayList<String>();
                    for (int j = 0; j < authorList.length(); j++) {
                        authors.add(authorList.getString(j));
                    }
                    String date = properties.getString("publishedDate");

                    Book book = new Book();
                    book.setTitle(title);
                    book.setPublisher(publisher);
                    book.setPublishDate(date);
                    book.setAuthors(authors.toArray(new String[]{}));
                    books.add(book);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return books;
        }
    }
}
