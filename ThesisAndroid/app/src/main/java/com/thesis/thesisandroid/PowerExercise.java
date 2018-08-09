package com.thesis.thesisandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

public class PowerExercise extends AppCompatActivity {

    private Button buttonchest1;
    private Button buttonarms;
    private Button buttonback1;
    private Button buttonleg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_exercise);
        buttonchest1 = (Button) findViewById(R.id.buttonchest);
        buttonchest1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                openActivity_chestperform();
            }
        });
        buttonarms = (Button) findViewById(R.id.buttonarms);
        buttonarms.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                openActivity_armperform();
            }
        });
        buttonback1 = (Button) findViewById(R.id.buttonback);
        buttonback1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                openActivity_backperform();
            }
        });
        buttonleg = (Button) findViewById(R.id.buttonleg);
        buttonleg.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                openActivity_legperform();
            }
        });
    }
    public void openActivity_chestperform(){
        Intent intent = new Intent(this, chestscrolling.class);
        startActivity(intent);
    }
    public void openActivity_armperform(){
        Intent intent = new Intent(this, armscrolling.class);
        startActivity(intent);
    }
    public void openActivity_backperform(){
        Intent intent = new Intent(this, backscrolling.class);
        startActivity(intent);
    }
    public void openActivity_legperform(){
        Intent intent = new Intent(this, legscrolling.class);
        startActivity(intent);
    }

}