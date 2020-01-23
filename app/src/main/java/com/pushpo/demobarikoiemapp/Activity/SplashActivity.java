package com.pushpo.demobarikoiemapp.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pushpo.demobarikoiemapp.R;


public class SplashActivity extends AppCompatActivity {
    private TextView tvTitle;
    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),
                "Roboto-Regular.ttf"));
        imageView1 = (ImageView) findViewById(R.id.imageView1);


        Animation animTop = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
//
//        Animation animBottom = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.enter_from_bottom);
//
//        // start the animation
        imageView1.startAnimation(animTop);
//        imageView2.startAnimation(animBottom);
//        //tvTitle.startAnimation(animRight);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
                startActivity(intent);

                finish();
            }
        }, 2000);
    }
}
