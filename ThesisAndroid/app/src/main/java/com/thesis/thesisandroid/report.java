package com.thesis.thesisandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class report extends AppCompatActivity {

    private static final String TAG = "report";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Log.d(TAG, "OnCreate : Started. ");
        ListView mListView = (ListView) findViewById(R.id.listview);

        //Create exercise data//
        data data1 = new data("Bicep Curls", "120 watts", "20 kg", "12", "8/20/2018");
        data data2 = new data("Bench press", "180 watts", "25 kg", "10", "8/20/2018");
        data data3 = new data("Squat", "130 watts", "30 kg", "8", "8/20/2018");
        data data4 = new data("Deadlift", "200 watts", "50 kg", "12", "8/20/2018");
        data data5 = new data("Barbell Row", "140 watts", "40 kg", "15", "8/20/2018");

        ArrayList<data> datalist = new ArrayList<>();
        datalist.add(data1);
        datalist.add(data2);
        datalist.add(data3);
        datalist.add(data4);
        datalist.add(data5);

        datalistAdapter adapter = new  datalistAdapter(this, R.layout.adapter_view_layout, datalist);
        mListView.setAdapter(adapter);
    }
}
