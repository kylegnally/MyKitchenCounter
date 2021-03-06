package com.knally0045.mykitchencounter;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

/**
 * Created by kyleg on 11/20/2017.
 */

public class NDBFetcher {

    // String to hold our JSON data
    private String mJsonString;

    // set up an HTTPS connection and try to open it
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    // get the URL string and return it
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    // fetch the NDB numbers, using the search string provided as a search term
    public ArrayList<PossibleIngredientMatch> fetchMatches(String search, Context context) {
        ArrayList<PossibleIngredientMatch> matches = new ArrayList<>();
        // check for spaces and replace with + sign for single ingredients
        // with multiple words (ex.: "frozen cod")
        if (search.contains(" ")) {
            search = search.replace(" ", "+");
        }

        try {

            // concatenate the base URL, search term, and API key
            String url = Uri.parse(context.getResources().getString(R.string.ndb_search_url_prefix)
                    + search
                    + context.getResources().getString(R.string.ndb_search_url_suffix))
                    .buildUpon().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);

            // send
            parseMatches(matches, jsonString);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
        Log.e(TAG, "Failed to fetch items. ", ioe);
    }
        return matches;
    }

    public void parseMatches(ArrayList<PossibleIngredientMatch> matches, String returnedString) throws IOException, JSONException{

        // drill down to what we want from this request: NDB numbers and names of ingredients
        JSONObject returnedObject = new JSONObject(returnedString);
        JSONObject listObject = returnedObject.getJSONObject("list");
        JSONArray itemArray = listObject.getJSONArray("item");

        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject itemObject = itemArray.getJSONObject(i);
            String name = itemObject.getString("name");
            String ndbno = itemObject.getString("ndbno");

            // create a new PossibleIngredientMatch and set the name and NDB number
            PossibleIngredientMatch match = new PossibleIngredientMatch(name, ndbno);
            match.setIngredientName(name);
            match.setIngredientNDB(ndbno);
            matches.add(match);
        }
    }
}
