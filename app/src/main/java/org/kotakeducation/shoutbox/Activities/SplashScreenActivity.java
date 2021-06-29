package org.kotakeducation.shoutbox.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.kotakeducation.shoutbox.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Write whatever to want to do after delay specified (1 sec)
                Intent intent=new Intent(SplashScreenActivity.this, LoginScreenActivity.class);
                startActivity(intent);
                finish();
                //Log.d("Handler", "Running Handler");
            }
        }, 3000);
    }
}