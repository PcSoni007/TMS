package com.prakhar.tms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.prakhar.tms.R;
import com.prakhar.tms.utils.Tools;

public class splash extends AppCompatActivity {

    public static int SPLASH_SCREEN_TIMEOUT=2000;
    Thread timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        Tools.setSystemBarColor(this, R.color.blue_50);

        ImageView i1=findViewById(R.id.img);


        Animation animation= AnimationUtils.loadAnimation(this,R.anim.mytransition);
        i1.startAnimation(animation);
        final Intent intent=new Intent(splash.this, HomePage.class);
        timer=new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(splash.this, HomePage.class));
                    finish();
                }
            }
        };
        timer.start();
    }

}
