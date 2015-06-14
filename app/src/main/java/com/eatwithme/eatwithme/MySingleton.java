package com.eatwithme.eatwithme;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by Hii on 6/13/15.
 */
public class MySingleton {
    private static MySingleton mInstance;
    private ArrayList<RowItem> mArrayList;
    public ArrayList<RowItem> mJoinArrayList;
    public static Context mContext;
    private RequestQueue mRequestQueue;
    MySingleton(Context context) {
        mArrayList = getmArrayList();
    }
    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        mContext = context;
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
    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }
}
