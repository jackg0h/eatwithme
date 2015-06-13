package com.eatwithme.eatwithme;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
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

    private static String basicUrl = "https://api.foursquare.com/v2/";
    private static double searchRadius = 5000;


    private Foursquare(){}

    public static void searchFoursquareVenue(double latitude, double longitude, String query, final Context context)
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
                                JSONObject jLocation = jVenue.getJSONObject("location");
                                String latitude = jLocation.getString("lat");
                                String longitude = jLocation.getString("lng");
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
                                MySingleton.getInstance(context).getmArrayList().add(new RowItem(venue_name, formatted_address, venue_id, latitude, longitude));
                            }
                            fillInImageLinks(context);
                            EventBus.getDefault().postSticky(MySingleton.getInstance(context).getmArrayList());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public static void fillInImageLinks(final Context context) {
        for (int i = 0; i < MySingleton.getInstance(context).getmArrayList().size(); i++) {
            String venue_id = MySingleton.getInstance(context).getmArrayList().get(i).mVenueID;
            String url = "https://api.foursquare.com/v2/venues/" + venue_id + "?" + "oauth_token=" + "2J1RKF3B5YTS1D1TLGZZWUN4HJ1L2IPGOUVIE440MQXN5YSI" + "&v=20150614";
            final int finalI = i;
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        int resolution_x = 335;
                        int resolution_y = 211;
                        JSONObject jobj = new JSONObject(response);
                        JSONObject jResponse = jobj.getJSONObject("response");
                        JSONObject jVenue = jResponse.getJSONObject("venue");


                        try {
                            JSONObject jPhotos = jVenue.getJSONObject("photos");
                            JSONArray jPhotosGroups = jPhotos.getJSONArray("groups");
                            JSONArray items = jPhotosGroups.getJSONObject(0).getJSONArray("items");
                            String prefix =  items.getJSONObject(0).getString("prefix");
                            String suffix = items.getJSONObject(0).getString("suffix");
                            String image_url = prefix + resolution_x + "x" + resolution_y + suffix;
                            Log.d("IMAGE URL", image_url);
                            MySingleton.getInstance(context).getmArrayList().get(finalI).mImageLink = image_url;
                            makeImageRequest(finalI,context);
                        }catch(JSONException e) {
                            Log.e("NO IMAGES FOUND", "NO IMAGES FOUND");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(stringRequest);
        }
    }
    public static void makeImageRequest(final int position, final Context context) {
        String url = MySingleton.getInstance(context).getmArrayList().get(position).mImageLink;
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Log.d("IMAGAE REQUEST", "DONE");
                        MySingleton.getInstance(context).getmArrayList().get(position).mImage = bitmap;
                        EventBus.getDefault().postSticky(5);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.d("WTF", "WTF THIS SHOUDLNT BE HAPPENING");
                    }
                });
        queue.add(request);
    }
    public static void createGroupRequest(int number_of_people, String group_name, String venue_id) {
        ParseUser user = ParseUser.getCurrentUser();
        ParseObject createGroup = new ParseObject("Group");
        createGroup.put("max_party", number_of_people);
        createGroup.put("name", group_name);
        createGroup.put("group_admin", user.getObjectId());
        createGroup.saveInBackground();
    }

}
