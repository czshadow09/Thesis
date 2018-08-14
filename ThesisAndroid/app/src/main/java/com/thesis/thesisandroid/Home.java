package com.thesis.thesisandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Set;

public class Home extends AppCompatActivity {

    private Button buttonBMI;
    private Button buttonPower;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mmDevice;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        buttonBMI = (Button) findViewById(R.id.buttonBMI);
        buttonBMI.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                openActivity_BMI();
            }
        });
        buttonPower = (Button) findViewById(R.id.buttonPower);

        buttonPower.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                openActivity_power_exercise();
            }
        });
    }

    public void openActivity_BMI(){
        Intent intent = new Intent(this, BMI.class);
        startActivity(intent);
    }
    public void openActivity_power_exercise(){
        Intent intent = new Intent(this, exerciselist.class);
        startActivity(intent);
    }


}
