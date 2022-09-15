package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    private static int SCREEN = 5000;

    Animation top , bottom;
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ConstraintLayout cl = findViewById(R.id.spl);
        AnimationDrawable am = (AnimationDrawable) cl.getBackground();
        am.setEnterFadeDuration(2500);
        am.setExitFadeDuration(5000);
        am.start();

        top = AnimationUtils.loadAnimation(Splash.this,R.anim.anim);
        bottom = AnimationUtils.loadAnimation(Splash.this,R.anim.anim1);

        im = findViewById(R.id.bnk);

        im.setAnimation(top);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SCREEN);


    }
}