package com.eatwithme.eatwithme;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MeFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    VenueAdapter mAdapter;
    ArrayList<RowItem> todoItems;
    TextView userNameView;
    Button fbLoginButton;

    private ProfilePictureView userProfilePictureView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MeFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_me, container, false);
//        Button addArrayButton = (Button) rootView.findViewById(R.id.array_add_button);
        userProfilePictureView = (ProfilePictureView) v.findViewById(R.id.profilePicture);
        userNameView = (TextView) v.findViewById(R.id.userName);
        fbLoginButton = (Button) v.findViewById(R.id.fb_login_button);

        fbLoginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                logout();
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && currentUser.isAuthenticated()) {
            makeMeRequest();
        }

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Override
    public void onResume() {
        super.onResume();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // Check if the user is currently logged
            // and show any cached content
            updateViewsWithProfileInfo();
        } else {
            // If the user is not logged in, go to the
            // activity showing the login view.
            // startLoginActivity();
        }
    }


    private void makeMeRequest() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        if (jsonObject != null) {
                            JSONObject userProfile = new JSONObject();

                            try {
                                userProfile.put("facebookId", jsonObject.getLong("id"));
                                userProfile.put("name", jsonObject.getString("name"));

                                // Save the user profile info in a user property
                                ParseUser currentUser = ParseUser.getCurrentUser();
                                currentUser.put("profile", userProfile);
                                currentUser.saveInBackground();

                                // Show the user info
                                updateViewsWithProfileInfo();
                            } catch (JSONException e) {
                                //Log.d(IntegratingFacebookTutorialApplication.TAG,"Error parsing returned user data. " + e);
                            }
                        } else if (graphResponse.getError() != null) {
                            switch (graphResponse.getError().getCategory()) {
                                case LOGIN_RECOVERABLE:
                                    //Log.d(IntegratingFacebookTutorialApplication.TAG,"Authentication error: " + graphResponse.getError());
                                    break;

                                case TRANSIENT:
                                    //Log.d(IntegratingFacebookTutorialApplication.TAG,"Transient error. Try again. " + graphResponse.getError());
                                    break;

                                case OTHER:
                                    //Log.d(IntegratingFacebookTutorialApplication.TAG,"Some other error: " + graphResponse.getError());
                                    break;
                            }
                        }
                    }
                });

        request.executeAsync();
    }

    private void updateViewsWithProfileInfo() {

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.has("profile")) {
            JSONObject userProfile = currentUser.getJSONObject("profile");
            try {

                if (userProfile.has("facebookId")) {
                    userProfilePictureView.setProfileId(userProfile.getString("facebookId"));
                } else {
                    // Show the default, blank user profile picture
                    userProfilePictureView.setProfileId(null);
                }

                if (userProfile.has("name")) {
                    //to get first time login
                   /* ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                    query.whereEqualTo("name", "Jack Goh");
                    query.findInBackground(new FindCallback<ParseObject>() {

                        public void done(List<ParseObject> scoreList, ParseException e) {
                            if (e == null) {
                                Log.d("score", "Retrieved " + scoreList.size() + " scores");

                                if(scoreList.size()>=1){

                                }

                            } else {
                                Log.d("score", "Error: " + e.getMessage());
                            }
                        }
                    });*/

                    userNameView.setText(userProfile.getString("name"));

                } else {
                    userNameView.setText("");
                }


            } catch (JSONException e) {
                //Log.d(IntegratingFacebookTutorialApplication.TAG, "Error parsing saved user data.");
            }
        }
    }

    private void logout() {
        // Log the user out
        ParseUser.logOut();
        // Go to the login view
        Intent intent = new Intent(getActivity(), SplashScreen.class);
        startActivity(intent);
        //startLoginActivity();
    }
}
