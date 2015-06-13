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
