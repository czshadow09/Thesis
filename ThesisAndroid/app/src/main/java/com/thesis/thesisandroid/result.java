package com.thesis.thesisandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class result extends AppCompatActivity{
    private Button buttongo1;
    private Button buttonreset1;
    TextView exercise;
    TextView weight;
    TextView rep;
    TextView oneRepMax;
    TextView power;
    String st;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        exercise = (TextView) findViewById(R.id.exreciseName);
        weight = (TextView) findViewById(R.id.Weightdata);
        rep = (TextView) findViewById(R.id.Repdata);
        oneRepMax = (TextView) findViewById(R.id.data1rm);
        power = (TextView) findViewById(R.id.datapower);
        buttongo1 = (Button) findViewById(R.id.buttongo);
        buttongo1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                openActivity_go();
            }
        });
        buttonreset1 = (Button) findViewById(R.id.buttonreset);
        buttonreset1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                openActivity_reset();
            }
        });
        getExerciseName();
        getWeight();
        getReps();
        getOneRepMax();
        getPower();
    }
    public void getExerciseName(){
        Bundle bundle = getIntent().getExtras();
        st = bundle.getString("Exercise name");
        exercise.setText(st);
    }
    public void getWeight(){
        Bundle bundle = getIntent().getExtras();
        st = bundle.getString("Weight");
        weight.setText(st);
    }
    public void getReps(){
        Bundle bundle = getIntent().getExtras();
        st = bundle.getString("reps");
        rep.setText(st);
    }
    public void getOneRepMax(){
        Bundle bundle = getIntent().getExtras();
        st = bundle.getString("RepMax");
        oneRepMax.setText(st + " kg");
    }
    public void getPower(){
        Bundle bundle = getIntent().getExtras();
        st = bundle.getString("power");
        power.setText(st);
    }
    public void openActivity_go(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void openActivity_reset(){
        Intent intent = new Intent(this, exerciselist.class);
        startActivity(intent);
    }
}


