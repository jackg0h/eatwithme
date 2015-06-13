package com.eatwithme.eatwithme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class InviteFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    VenueAdapter mAdapter;
    ArrayList<RowItem> todoItems;
    ListView mListView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static InviteFragment newInstance() {
        InviteFragment fragment = new InviteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public InviteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_invite, container, false);
//        Button addArrayButton = (Button) rootView.findViewById(R.id.array_add_button);
        mListView = (ListView) rootView.findViewById(R.id.venue_listview);
        Log.d("ONCREATEVIEW", "ONCREATEVIEW CALLED");

        mAdapter = new VenueAdapter(getActivity(),
                R.layout.list_item, MySingleton.getInstance(getActivity()).getmArrayList());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                RowItem rowItem = (RowItem) mListView.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
                intent.putExtra("venue_id",rowItem.mVenueID);
                intent.putExtra("latitude", rowItem.mLatitude);
                intent.putExtra("longitude", rowItem.mLongitude);
                intent.putExtra("venue_name", rowItem.mVenue);
                intent.putExtra("venue_address", rowItem.mAddress);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("ONRESUME", "ONRESUME");
        Log.d("ONRESUME ARRAY LIST", String.valueOf(MySingleton.getInstance(getActivity()).getmArrayList().size()));
        mAdapter.notifyDataSetChanged();
    }

    public void onEventMainThread(ArrayList<RowItem> arrayList){
        Log.d("REEIVED", "RECEVEIDE EVENT MEIN THREAD " + arrayList.size());
        mAdapter.notifyDataSetChanged();
    }
    public void onEventMainThread(int i) {
        mAdapter.notifyDataSetChanged();
    }

}
