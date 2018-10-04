package com.thesis.thesisandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BMI extends AppCompatActivity {

    private EditText height;
    private EditText weight;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        getSupportActionBar().setTitle("BMI");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);


    }
    public void calculateBMI(View v){
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();


        if(heightStr !=null && !"".equals(heightStr)&&
             weightStr !=null && !"".equals(weightStr)){
            float heightValue = Float.parseFloat(heightStr) / 100;
            float weightValue = Float.parseFloat(weightStr);
            float bmi = weightValue / (heightValue * heightValue);

            displayBMI(bmi);
        }
    }

    private void displayBMI(float bmi){
        String bmilabel="";

        if(Float.compare(bmi, 15f)<=0) {
            bmilabel = getString(R.string.underweight);
        }else if(Float.compare(bmi,18.5f)>0 && Float.compare (bmi, 22.9f)<=0){
            bmilabel = getString(R.string.normal);
        }else if(Float.compare(bmi,23f)>0 && Float.compare (bmi, 24.9f)<=0){
            bmilabel = getString(R.string.overweight);
        }else {
            bmilabel = getString(R.string.obese_class_1);
        }
        bmilabel = bmi + "\n\n" + bmilabel;
        result.setText(bmilabel);
    }
}
