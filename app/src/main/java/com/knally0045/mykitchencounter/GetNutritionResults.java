package com.knally0045.mykitchencounter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

/**
 * Created by kyleg on 12/15/2017.
 */

public class GetNutritionResults {

    private ArrayList<String> mNDBArray;

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

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public ArrayList<IngredientNutrition> fetchNutrition(ArrayList<String> ndbs, Context context) {
        ArrayList<IngredientNutrition> nutrition = new ArrayList<>();
        try {
            String urlPrefix = (context.getResources().getString(R.string.ndb_search_url_prefix));
            String urlSuffix = (context.getResources().getString(R.string.ndb_search_url_suffix));
            StringBuilder ndbNumbers = new StringBuilder();
            for (String numbers : ndbs) {
                ndbNumbers.append(context.getResources().getString(R.string.ndb_number_label)
                        + numbers
                        + context.getResources().getString(R.string.ndb_number_amp));
            }
            String urlNDBNumbers = ndbNumbers.toString();

            String url = Uri.parse(urlPrefix + urlNDBNumbers + urlSuffix).buildUpon().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);

            //parseResults(nutrition, jsonString);
//        } catch (JSONException je) {
//            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items. ", ioe);
        }
        return nutrition;
    }
}

