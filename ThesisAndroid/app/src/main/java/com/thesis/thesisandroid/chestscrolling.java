package com.thesis.thesisandroid;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Set;

public class chestscrolling extends Activity {
    String[] exerciselistchest = {"Dumbell Benchpress","Dumbell Incline Benchpress","Barbell Benchpress","Barbell Incline Benchpress","Dumbell Fly","Cable Fly" };
    TextView myLabel;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chestscrolling);
        Spinner dropdown = findViewById(R.id.spinner1);
        ImageButton starting = (ImageButton)findViewById(R.id.cheststart);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, exerciselistchest);
        dropdown.setAdapter(adapter);

        starting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    findBT();
                }
                catch (IOException ex)
                {
                    Toast.makeText(getBaseContext(), "Bluetooth connection failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void findBT() throws IOException
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null)
        {
            Toast.makeText(getBaseContext(), "Bluetooth device not found.", Toast.LENGTH_LONG).show();
        }

        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getAddress().equals("00:21:13:01:3C:EC"))
                {
                    Toast.makeText(getBaseContext(), "Power Gloves connected.", Toast.LENGTH_SHORT).show();
                    mmDevice = device;
                    break;
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Power Gloves not found.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
