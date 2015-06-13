package com.eatwithme.eatwithme;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Hii on 6/13/15.
 */
public class MySingleton {
    private static MySingleton mInstance;
    private ArrayList<RowItem> mArrayList;
    public ArrayList<RowItem> mJoinArrayList;
    MySingleton(Context context) {
        mArrayList = getmArrayList();
    }
    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }
    public ArrayList<RowItem> getmArrayList(){
        if(mArrayList == null) {
            mArrayList = new ArrayList<RowItem>();
        }
        return mArrayList;
    }
    public ArrayList<RowItem> getmJoinRowArrayList() {
        if(mJoinArrayList == null) {
            mJoinArrayList = new ArrayList<RowItem>();
        }
        return mJoinArrayList;
    }
}
