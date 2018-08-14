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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
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


