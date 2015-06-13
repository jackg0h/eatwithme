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

    RowItem(String venue, String address, String venue_id) {
        mVenue = venue;
        mAddress = address;
        mVenueID = venue_id;
    }
    RowItem(String title, Bitmap image) {
        mVenue = title;
        mImage = image;
    }

}
