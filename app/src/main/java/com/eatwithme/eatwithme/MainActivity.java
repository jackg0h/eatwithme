/*
 *  Copyright (c) 2014, Parse, LLC. All rights reserved.
 *
 *  You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 *  copy, modify, and distribute this software in source code or binary form for use
 *  in connection with the web services and APIs provided by Parse.
 *
 *  As with any software that integrates with the Parse platform, your use of
 *  this software is subject to the Parse Terms of Service
 *  [https://www.parse.com/about/terms]. This copyright notice shall be
 *  included in all copies or substantial portions of the software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.eatwithme.eatwithme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static final int LOGIN_REQUEST = 0;

    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private Button loginOrLogoutButton;
    private FloatingActionButton floatingActionButton;


    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    private ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        setContentView(R.layout.activity_main);
//        titleTextView = (TextView) findViewById(R.id.profile_titl//e);
//        emailTextView = (TextView) findViewById(R.id.profile_emai//l);
//        nameTextView = (TextView) findViewById(R.id.profile_nam//e);
//        loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);


//        titleTextView.setText(R.string.profile_title_logged_in);

//        loginOrLogoutButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentUser != null) {
//                    // User clicked to log out.
//                    ParseUser.logOut();
//                    currentUser = null;
//                    showProfileLoggedOut();
//                } else {
//                    // User clicked to log in.
//                    ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
//                            MainActivity.this);
//                    startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);
//                }
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();

       currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            setContentView(R.layout.activity_main);
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            // Bind the tabs to the ViewPager
            PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
            tabs.setViewPager(mViewPager);


            floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mViewPager.getCurrentItem() == 1) {
                        Intent intent = new Intent(MainActivity.this, CreateGroupActivity.class);
                        startActivity(intent);
                    }
                }
            });


        } else {
            showProfileLoggedOut();
        }

        if(currentUser == null){
            ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
                    MainActivity.this);
            startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);
        }
    }

    /**
     * Shows the profile of the given user.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return JoinFragment.newInstance("Join Fragment", "First Fragment");
                case 1:
                    return InviteFragment.newInstance();
                default:
                    return PlaceholderFragment.newInstance();
            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
//        loginOrLogoutButton.setText(R.string.profile_logout_button_label);
    }

    /**
     * Show a message asking the user to log in, toggle login/logout button text.
     */
    private void showProfileLoggedOut() {
//        titleTextView.setText(R.string.profile_title_logged_out);
//        emailTextView.setText("");
//        nameTextView.setText("");
//        loginOrLogoutButton.setText(R.string.profile_login_button_label);
    }
}
