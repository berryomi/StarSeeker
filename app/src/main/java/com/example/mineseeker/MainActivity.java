package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAnimation();
        setupSkipAnimationButton();
    }

    private void setupSkipAnimationButton() {
        ImageButton button = findViewById(R.id.skipButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launch the Main Menu
                Intent intent = MainMenuActivity.makeIntent(MainActivity.this);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    //https://stackoverflow.com/questions/4213393/translate-animation
    //https://stackoverflow.com/questions/2032304/android-imageview-animation
    private void startAnimation() {
        ImageView view = findViewById(R.id.shootingstar); //Initialize ImageView via FindViewById or programatically

        TranslateAnimation anim = new TranslateAnimation(0, -1800, 0, 500);

        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
        anim.setDuration(7000); //Put desired duration per anim cycle here, in milliseconds

        view.startAnimation(anim);
    }


}





