package com.thesis.thesisandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "exerreport.db";
    public static final String TABLE_NAME = "report_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "exername";
    public static final String COL3 = "power";
    public static final String COL4 = "reps";
    public static final String COL5 = "weight";
    public static final String COL6 = "onerepmax";
    public static final String COL7 = "date";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " exername TEXT, power TEXT, reps TEXT, weight TEXT, onerepmax TEXT, date DATETIME)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name, String power, String reps, String weight, String onerepmax) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, power);
        contentValues.put(COL4, reps);
        contentValues.put(COL5, weight);
        contentValues.put(COL6, onerepmax);
        contentValues.put(COL7, date);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
}
