package com.knally0045.mykitchencounter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
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
    private String mName;
    private String mNutrientName;
    private String mNutrientValue;
    private String mCalories;
    private String mProtein;
    private String mFat;

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
            String urlPrefix = (context.getResources().getString(R.string.ndb_number_url_prefix));
            String urlSuffix = (context.getResources().getString(R.string.ndb_number_url_suffix));
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

            parseResults(nutrition, jsonString);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items. ", ioe);
        }
        return nutrition;
    }

    public void parseResults(ArrayList<IngredientNutrition> nutritions, String returnedString) throws IOException, JSONException {
        JSONObject returnedObject = new JSONObject(returnedString);
        JSONArray foodsObject = returnedObject.getJSONArray("foods");

        for (int i = 0; i < foodsObject.length(); i++) {
            JSONObject foodIndex = foodsObject.getJSONObject(i);
            JSONObject foodObject = foodIndex.getJSONObject("food");
            JSONObject descObject = foodObject.getJSONObject("desc");
            String name = descObject.getString("name");
            JSONArray nutrients = foodObject.getJSONArray("nutrients");
            for (int j = 0; j < nutrients.length(); j++) {
                JSONObject nutrientsEntry = nutrients.getJSONObject(j);
                mNutrientName = nutrientsEntry.getString("name");
                mNutrientValue = nutrientsEntry.getString("value");
                if (mNutrientName == "Energy") {
                    mCalories = nutrientsEntry.getString("value");

                }

                if (mNutrientName == "Total lipid (fat)") {
                    mFat = nutrientsEntry.getString("value");
                }

                if (mNutrientName == "Protein") {
                    mProtein = nutrientsEntry.getString("value");
                }
            }


        }
//        JSONObject zeroObject = foodsObject.getJSONObject(0);
//        JSONObject foodObject = zeroObject.getJSONObject("food");
//        for (int i = 0; i < foodsObject.length(); i++) {
//
//        }



    }

}

