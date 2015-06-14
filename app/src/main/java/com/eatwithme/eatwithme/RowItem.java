package com.eatwithme.eatwithme;

import android.app.Activity;
import android.graphics.Bitmap;

import java.util.ArrayList;

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
    public String mObjectId;
    public Bitmap mImage;
    public ArrayList mGroupMembers;


    String mPartyMax ;

    String mGroupName ;

    RowItem(String venue, String address, String venue_id, String party_max, ArrayList group_members, String group_name, String objectID) {
        mVenue = venue;
        mAddress = address;
        mVenueID = venue_id;
        mPartyMax = party_max;
        mGroupName = group_name;
        mGroupMembers = group_members;
        mObjectId = objectID;
    }

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
