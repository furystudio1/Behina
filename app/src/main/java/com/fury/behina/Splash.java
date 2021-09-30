package com.fury.behina;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fury.behina.R;
import com.fury.behina.views.FlowingGradientClass;

/**
 * Created by FURY on 11/30/2017.
 */

public class Splash extends Activity {

    // Get Save
    SharedPreferences one_play_preferences;
    SharedPreferences.Editor one_play_editor;

    int oneplay;

    //Time Splash (Millisecond)
    int _splashTime = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set Layout
        setContentView(R.layout.splashapp);

        RelativeLayout rl1 = findViewById(R.id.rl1);

        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onRelativeLayout(rl1)
                .setTransitionDuration(1000)
                .start();

        /*//font
        TextView text = (TextView) findViewById(R.id.icon) ;

        //Get file font
        Typeface typeface = Typeface.createFromAsset(getAssets(),"IRANSansMobile.ttf");

        text.setTypeface(typeface);*/

        // Get Save
        one_play_preferences = getApplicationContext().getSharedPreferences("Behina", android.content.Context.MODE_PRIVATE);
        one_play_editor = one_play_preferences.edit();
        oneplay = one_play_preferences.getInt("one_play_app", 3);

        one_play_editor.putBoolean("ONE", false);
        one_play_editor.apply();


    }

    @Override
    protected void onStart() {
        super.onStart();

            try {

                new Handler().postDelayed(new Thread() {

                    @Override
                    public void run() {
                        super.run();

                        //Go to Page 1
                        Intent uou = new Intent(Splash.this, Login.class);
                        startActivity(uou);
                        Splash.this.finish();
                    }
                }, _splashTime);

            } catch (Exception e) {
            }



    }
}
