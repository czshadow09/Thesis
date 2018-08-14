package com.thesis.thesisandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class duringexercise extends AppCompatActivity implements View.OnClickListener{
    private ProgressBar progressbarReps;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duringexercise);
        getSupportActionBar().hide();
        initViews1();
        initListeners1();
        onBackPressed();


    }
    @Override
    public void onBackPressed() {
    }
    public void openresult(){
        Intent intent = new Intent(this, result.class);
        startActivity(intent);
    }
    private void initViews1() {
        progressbarReps = (ProgressBar) findViewById(R.id.progressBarCircleReps);

    }
    private void initListeners1() {
        progressbarReps.setOnClickListener(this);

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.progressBarCircleReps:
                openresult();
                break;

        }
    }



}
