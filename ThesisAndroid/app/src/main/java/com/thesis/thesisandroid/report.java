package com.thesis.thesisandroid;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class report extends AppCompatActivity {

    DbHandler myDB;
    ArrayList<data> dataList;
    ListView mListView;
    data progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getSupportActionBar().setTitle("Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onBackPressed();
        myDB = new DbHandler(this);

        dataList = new ArrayList<>();
        Cursor items = myDB.getListContents();
        int numRows = items.getCount();
        if(numRows == 0) {
            Toast.makeText(this, "There is nothing in the database!", Toast.LENGTH_LONG).show();
        }
        else {

            while(items.moveToNext()) {
                progress = new data(items.getString(1), items.getString(2), items.getString(4), items.getString(3), items.getString(5));
                dataList.add(progress);
            }
            datalistAdapter adapter = new datalistAdapter(this, R.layout.adapter_view_layout, dataList);
            mListView = (ListView) findViewById(R.id.listview);
            mListView.setAdapter(adapter);
        }
    }
    public void onBackPressed() {
    }
}
