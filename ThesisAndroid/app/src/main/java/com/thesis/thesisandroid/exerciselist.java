package com.thesis.thesisandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class exerciselist extends AppCompatActivity {
    private Spinner mySpinner;
    private Button buttonstart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerciselist);
         mySpinner = (Spinner) findViewById(R.id.spinnerExercise);



        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(exerciselist.this,R.layout.spinner_item,
                getResources().getStringArray(R.array.Exercise));
        myAdapter.setDropDownViewResource(R.layout.spinner_item);
        mySpinner.setAdapter(myAdapter);
        buttonstart = (Button) findViewById(R.id.buttonstart);
        buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                openActivity_start();
            }
        });

    }
    public void openActivity_start(){
        Intent intent = new Intent(this, legscrolling.class);
        startActivity(intent); }


    }

