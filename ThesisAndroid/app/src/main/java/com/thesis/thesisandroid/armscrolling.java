package com.thesis.thesisandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import android.widget.Spinner;
import java.util.Map;
import java.util.UUID;
import java.util.Set;
import android.widget.Toast;

public class armscrolling extends AppCompatActivity {
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
    String[] exerciselistarm = {"Hammer Curl","Dumbbell Curl","Incline Dumbbell Curl","Barbell Curl","Cable Triceps Ext." };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armscrolling);
        Spinner dropdown = findViewById(R.id.spinner2);
        ImageButton starting = (ImageButton)findViewById(R.id.armstart);
        myLabel = (TextView)findViewById(R.id.avrpower);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, exerciselistarm);
        dropdown.setAdapter(adapter);

        starting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    findBT();
                }
                catch (IOException ex)
                {
                    Toast.makeText(getBaseContext(), "Bluetooth connection failed.", Toast.LENGTH_LONG).show();
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
