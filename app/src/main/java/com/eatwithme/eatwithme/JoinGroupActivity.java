package com.eatwithme.eatwithme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class JoinGroupActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        Intent intent = this.getIntent();
        int position = intent.getIntExtra("position", 0);
        final RowItem rowItem = MySingleton.getInstance(this).getmJoinRowArrayList().get(position);
        TextView titleView = (TextView) findViewById(R.id.title_textview);
        TextView descriptionView = (TextView) findViewById(R.id.description_textview);
        TextView groupNameView = (TextView) findViewById(R.id.group_name_textview);
        Button join_button = (Button) findViewById(R.id.join_group_button);
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RECEIVED ", "RECEIVED CLICKING SIGNAL");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("FoodGroup");
                query.getInBackground(rowItem.mObjectId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject gameScore, ParseException e) {
                        if (e == null) {
                            Log.d("READY ", "READY TO SEND");
                            // Now let's update it with some new data. In this case, only cheatMode and score
                            // will get sent to the Parse Cloud. playerName hasn't changed.
                            gameScore.add("GroupMembers", ParseUser.getCurrentUser().getObjectId());
                            gameScore.saveInBackground();0
                            Toast.makeText(JoinGroupActivity.this, "Joined", Toast.LENGTH_SHORT).show();
                        }
                        if (e != null) {
                            e.printStackTrace();
                            Log.d("TEST","\n\n\n\n\n " + rowItem.mObjectId + "\n\n\n\n\n" );
                        }
                    }
                });

            }
        });
        titleView.setText(rowItem.mVenue);
        descriptionView.setText(rowItem.mAddress);
        groupNameView.setText(rowItem.mGroupName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_join_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
