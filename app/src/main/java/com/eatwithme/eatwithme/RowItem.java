package com.eatwithme.eatwithme;

import android.app.Activity;
import android.graphics.Bitmap;

/**
 * Created by Hii on 6/13/15.
 */
public class RowItem extends Activity{
    public String mVenue;
    public String mVenueID;
    public String mAddress;
    public String mImageLink;
    public String mLatitude;
    public String mLongitude;
    public Bitmap mImage;

    RowItem(String venue, String address, String venue_id, String latitude, String longitude) {
        mVenue = venue;
        mAddress = address;
        mVenueID = venue_id;
        mLatitude = latitude;
        mLongitude = longitude;
    }
    RowItem(String title, Bitmap image) {
        mVenue = title;
        mImage = image;
    }

}
