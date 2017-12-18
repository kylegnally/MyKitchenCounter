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

    private String mNutrientName;
    private double mCalories;
    private double mProtein;
    private double mFat;
    private RecipeNutrition mNutrition;
    private int mCount = 0;

    // set up a HTTPS connection
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

    // return the URL string
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    // fetch the nutrition information
    public ArrayList<IngredientNutrition> fetchNutrition(ArrayList<String> ndbs, Context context) {
        ArrayList<IngredientNutrition> nutrition = new ArrayList<>();
        try {

            // set up prefixes and suffixes in the URL. These are the
            // base URL and the API key, respectively
            String urlPrefix = (context.getResources().getString(R.string.ndb_number_url_prefix));
            String urlSuffix = (context.getResources().getString(R.string.ndb_number_url_suffix));

            // use StringBuilder to concatenate our NDB search numbers
            StringBuilder ndbNumbers = new StringBuilder();
            for (String numbers : ndbs) {
                ndbNumbers.append(context.getResources().getString(R.string.ndb_number_label)
                        + numbers
                        + context.getResources().getString(R.string.ndb_number_amp));
            }

            // set a string to hold the completed NDB string
            String urlNDBNumbers = ndbNumbers.toString();

            // now put the prefix, NDB numbers, and API key together into a single string
            String url = Uri.parse(urlPrefix + urlNDBNumbers + urlSuffix).buildUpon().toString();

            // get the JSON response string from the server
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);

            // send the ArrayList and the JSON string off to be parsed
            parseResults(nutrition, jsonString, context);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items. ", ioe);
        }
        return nutrition;
    }

    // parse the results. This method is not yet complete and requires more work - need to pull correct values out of JSON
    // and then add them to a RecipeNutrition object
    public void parseResults(ArrayList<IngredientNutrition> nutritions, String returnedString, Context context) throws IOException, JSONException {


        // drill down through the JSON object to get to what we want: nutrition information for each ingredient
        JSONObject returnedObject = new JSONObject(returnedString);
        JSONArray foodsObject = returnedObject.getJSONArray("foods");
        mNutrition = RecipeNutrition.get(context);

        for (int i = 0; i < foodsObject.length(); i++) {
            JSONObject foodIndex = foodsObject.getJSONObject(i);
            JSONObject foodObject = foodIndex.getJSONObject("food");
            JSONObject descObject = foodObject.getJSONObject("desc");
            mNutrientName = descObject.getString("name");
            JSONArray nutrients = foodObject.getJSONArray("nutrients");
            for (int j = 0; j < nutrients.length(); j++) {
                //mCount = 0;
                JSONObject nutrientsEntry = nutrients.getJSONObject(j);
                String nutrientName = nutrientsEntry.getString("name");
                //mNutrientValue = nutrientsEntry.getString("value");

                // this section is supposed to extract the three nutrients by name from each
                // nutrition object. This does not yet work correctly
                if (nutrientName.contains("Energy")) {
                    mCalories = nutrientsEntry.getDouble("value");
                    mCount++;
                }

                if (nutrientName.contains("Total lipid (fat)")) {
                    mFat = nutrientsEntry.getDouble("value");
                    mCount++;
                }

                if (nutrientName.contains("Protein")) {
                    mProtein = nutrientsEntry.getDouble("value");
                    mCount++;
                }

                if (mCount == 3) {
                    IngredientNutrition nutrition = new IngredientNutrition(mNutrientName, mCalories, mProtein, mFat);
                    nutrition.setName(mNutrientName);
                    nutrition.setCalories(mCalories);
                    nutrition.setProtein(mProtein);
                    nutrition.setFat(mFat);
                    nutritions.add(nutrition);
                    mCount = 0;
                }
                mNutrition.setIngredientNutritions(nutritions);
            }
        }

    }

}

