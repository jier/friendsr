package com.example.jier.friendsr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    Friend retrievedFriend;
    private EditText userEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        retrievedFriend = (Friend) intent.getSerializableExtra("clicked_friend");

        String name = retrievedFriend.getName();
        String bio = retrievedFriend.getBio();
        int id = retrievedFriend.getDrawableID();
        ImageView v = (ImageView) findViewById(R.id.imageView);
        v.setImageResource(id);
        TextView textName = (TextView) findViewById(R.id.textView);
        textName.setText(name);


        final SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        float rated = prefs.getFloat("rating"+name,0.0f);

        if (rated != 0.0f) {
            retrievedFriend.setRating(rated);
        }

        RatingBar rate = (RatingBar) findViewById(R.id.ratingBar);
        float rating = retrievedFriend.getRating();
        rate.setRating(rating);
        TextView textBio = (TextView) findViewById(R.id.textView4);
        textBio.setText(bio);
        userEdit = (EditText) findViewById(R.id.comId);
        userEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
                String name = retrievedFriend.getName();
                String comment  = prefs.getString("comment" + name,retrievedFriend.getComment());
                if (comment != "") {
                    retrievedFriend.setComment(comment);
                }
                userEdit.setText(comment);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
                String name = retrievedFriend.getName();
                String value = retrievedFriend.getComment();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("comment"+ name,value);
                editor.apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
                String name = retrievedFriend.getName();
                String  comment  = prefs.getString("comment" + name,retrievedFriend.getComment());
                if (comment != "") {
                    retrievedFriend.setComment(comment);
                }
                userEdit.setText(comment);
            }
        });

        rate.setOnRatingBarChangeListener(new OnRatingBarChangeListener());
    }

    private class OnRatingBarChangeListener implements RatingBar.OnRatingBarChangeListener {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            String name = retrievedFriend.getName();
            editor.putFloat("rating"+ name,v);
            editor.apply();

        }

    }


}
