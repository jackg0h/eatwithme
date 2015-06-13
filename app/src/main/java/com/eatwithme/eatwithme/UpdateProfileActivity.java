/**
 * Created by Jack on 6/13/2015.
 */
package com.eatwithme.eatwithme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UpdateProfileActivity extends Activity{
    private ProfilePictureView userProfilePictureView;
    private TextView userNameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_updateprofile);

        userProfilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        userNameView = (TextView) findViewById(R.id.userName);


        //Fetch Facebook user info if it is logged
        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && currentUser.isAuthenticated()) {
            makeMeRequest();
        }
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
            startLoginActivity();
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
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
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
                    });

                    // userNameView.setText(userProfile.getString("name"));

                } else {
                    userNameView.setText("");
                }


            } catch (JSONException e) {
                //Log.d(IntegratingFacebookTutorialApplication.TAG, "Error parsing saved user data.");
            }
        }
    }

    public void onLogoutClick(View v) {
        logout();
    }

    private void logout() {
        // Log the user out
        ParseUser.logOut();

        // Go to the login view
        startLoginActivity();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}