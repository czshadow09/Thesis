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
            bmilabel = getString(R.string.very_severely_underweight);
        }else if(Float.compare(bmi,15f)>0 && Float.compare (bmi, 16f)<=0){
            bmilabel = getString(R.string.severely_underweight);
        }else if(Float.compare(bmi,16f)>0 && Float.compare (bmi, 18.5f)<=0){
            bmilabel = getString(R.string.underweight);
        }else if(Float.compare(bmi,18.5f)>0 && Float.compare (bmi, 25f)<=0){
            bmilabel = getString(R.string.normal);
        }else if(Float.compare(bmi,25f)>0 && Float.compare (bmi, 30f)<=0){
            bmilabel = getString(R.string.overweight);
        }else if(Float.compare(bmi,30f)>0 && Float.compare (bmi, 35f)<=0){
            bmilabel = getString(R.string.obese_class_1);
        }else if(Float.compare(bmi,35f)>0 && Float.compare (bmi, 40f)<=0) {
            bmilabel = getString(R.string.obese_class_2);
        }else {
            bmilabel = getString(R.string.obese_class_3);
        }
        bmilabel = bmi + "\n\n" + bmilabel;
        result.setText(bmilabel);
    }
}
