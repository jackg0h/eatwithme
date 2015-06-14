package com.eatwithme.eatwithme;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JoinFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JoinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String JoingFragmentLog = "";

    private OnFragmentInteractionListener mListener;
    private VenueAdapter mAdapter;
    // TODO: Rename and change types and number of parameters
    public static JoinFragment newInstance() {
        JoinFragment fragment = new JoinFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public JoinFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_join, container, false);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("FoodGroup");
        ListView mListView = (ListView) rootView.findViewById(R.id.join_listview);
        mAdapter = new VenueAdapter(getActivity(),
                R.layout.list_item, MySingleton.getInstance(getActivity()).getmJoinRowArrayList());
        mListView.setAdapter(mAdapter);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");

                    if(scoreList.size()>=1){
                        MySingleton.getInstance(getActivity()).getmJoinRowArrayList().clear();
                        for(int i = 0 ; i < scoreList.size(); i++) {
                            String party_max = (String) scoreList.get(i).get("GroupMaxParty");
//                            Array group_members = (Array) scoreList.get(i).get("GroupMembers");
                            String group_name = (String) scoreList.get(i).get("GroupName");
                            String venue_id = (String) scoreList.get(i).get("GroupVenueID");
                            String venue_name = (String ) scoreList.get(i).get("GroupVenueName");
                            String venue_address = (String) scoreList.get(i).get("GroupVenueAddress");
                            MySingleton.getInstance(getActivity()).getmJoinRowArrayList().add(new RowItem(venue_name, venue_address, venue_id, party_max, group_name, true));
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
        return rootView;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


}
