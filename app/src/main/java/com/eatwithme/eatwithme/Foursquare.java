package com.eatwithme.eatwithme;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
/**
 * Created by ezekielchow on 6/13/15.
 */
public final class Foursquare extends Activity{

    private static String dataToReturn = "";
    private static String FoursquareLog = "";
    private static String basicUrl = "https://api.foursquare.com/v2/";
    private static double searchRadius = 5000;


    private Foursquare(){}

    public static String searchFoursquareVenue(double latitude, double longitude, String query, final Context context)
    {

        // Instantiate the RequestQueue.
        String queryUrl = query;
        RequestQueue queue = Volley.newRequestQueue(context);
        String theCustomUrl = "venues/search?ll=" + latitude + "," + longitude + "&radius=" + searchRadius + "&query=" + queryUrl + "&v=20150613";
        String authTokenUrl = "&client_id=" + "YLKPCQ2G2EEMMZLM32INGH5ZFAKFAXWGQ4PG51XTCJ0WMB3R" + "&client_secret=" + "RLILGI3XA5OP5ZPTHXTUOHQJFXNOD4VSRVC01WY1JFTIRKWR";
        String requestUrl = basicUrl + theCustomUrl + authTokenUrl;
        MySingleton.getInstance(context).getmArrayList().clear();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONObject jResponse = jobj.getJSONObject("response");
                            JSONArray jVenueArray = jResponse.getJSONArray("venues");
                            for(int i = 0; i < jVenueArray.length(); i++) {
                                JSONObject jVenue = jVenueArray.getJSONObject(i);
                                String venue_name = jVenue.getString("name");
                                String venue_id = jVenue.getString("id");
                                String formatted_address = "";
                                JSONArray formatted_address_array = jVenue.getJSONObject("location").getJSONArray("formattedAddress");
                                for(int j = 0; j < formatted_address_array.length(); j++) {
                                    if(j > 0) {
                                        formatted_address = formatted_address + ", " + formatted_address_array.getString(j);
                                    }else
                                        formatted_address = formatted_address + formatted_address_array.getString(j);
                                }
                                JSONArray jCategories = jVenue.getJSONArray("categories");
                                MySingleton.getInstance(context).getmArrayList().add(new RowItem(venue_name, formatted_address, venue_id));
                            }
                            EventBus.getDefault().postSticky(MySingleton.getInstance(context).getmArrayList());
                        } catch (JSONException e) {
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

}
