package com.eatwithme.eatwithme;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ezekielchow on 6/13/15.
 */
public final class Foursquare extends Activity {

    private static ArrayList<String> dataToReturn = new ArrayList<String>();
    private static String FoursquareLog = "";
    private static String basicUrl = "https://api.foursquare.com/v2/";
    private static double searchRadius = 5000;

    private Foursquare() {
    }

    public static ArrayList<String> searchFoursquareVenue(double latitude, double longitude, String query, Context context)
    {
        // Instantiate the RequestQueue.
        String queryUrl = query;
        RequestQueue queue = Volley.newRequestQueue(context);
        String theCustomUrl = "venues/search?ll=" + latitude + "," + longitude + "&radius=" + searchRadius + "&query=" + queryUrl + "&v=20150613";
        String authTokenUrl = "&client_id=" + "YLKPCQ2G2EEMMZLM32INGH5ZFAKFAXWGQ4PG51XTCJ0WMB3R" + "&client_secret=" + "RLILGI3XA5OP5ZPTHXTUOHQJFXNOD4VSRVC01WY1JFTIRKWR";
        String requestUrl = basicUrl + theCustomUrl + authTokenUrl;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject reponseJson = jsonObject.getJSONObject("reponse");
                            JSONArray arrayVenues = reponseJson.getJSONArray("venues");

                            if (arrayVenues != null) {
                                int len = arrayVenues.length();
                                for (int i=0;i<len;i++){
                                    dataToReturn.add(arrayVenues.get(i).toString());
                                }
                            }

                            Log.d(FoursquareLog, arrayVenues.toString());
                            dataToReturn.add(arrayVenues);

                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(FoursquareLog, error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        return dataToReturn;
    }

    public static
    {

    }
}
