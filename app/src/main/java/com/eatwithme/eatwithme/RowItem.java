package com.eatwithme.eatwithme;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Hii on 6/13/15.
 */
public class RowItem extends Activity{
    public String mVenue;
    public String mVenueID;
    public String mAddress;
    public Bitmap mImage;
    public VenueAdapter venueAdapter;
    public Context mContext;

    RowItem(String venue, String address, String venue_id, Context context) {
        mVenue = venue;
        mAddress = address;
        mVenueID = venue_id;
        mContext = context;
    }
    RowItem(String title, Bitmap image) {
        mVenue = title;
        mImage = image;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void loadImage(VenueAdapter venueAdapter) {
        // HOLD A REFERENCE TO THE ADAPTER
        this.venueAdapter = venueAdapter;
        String imgUrl = "https://api.foursquare.com/v2/venues/" + this.mVenueID;

        if (imgUrl != null && !imgUrl.equals("")) {
            getFoursquareImage(imgUrl, this);
            venueAdapter.notifyDataSetChanged();
        }
    }

    private void getFoursquareImage(String imageUrl, Context context)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        ImageRequest imageRequest = new ImageRequest(imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImage = bitmap;
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        //TODO return error image
                    }
                });

        queue.add(imageRequest);
    }

}
