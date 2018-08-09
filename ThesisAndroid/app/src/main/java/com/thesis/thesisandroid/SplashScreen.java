package com.thesis.thesisandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Set;


public class SplashScreen extends AppCompatActivity {

    ImageView motto;
    ImageView logo;
    Animation frombottom, fromtop;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mmDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        motto = (ImageView) findViewById(R.id.motto);
        logo = (ImageView) findViewById(R.id.logo1);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        motto.setAnimation(frombottom);
        final Intent i = new Intent(this,Home.class);
        Thread timer = new Thread(){
            public void run (){
                try {
                    sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
                timer.start();
    }

}
