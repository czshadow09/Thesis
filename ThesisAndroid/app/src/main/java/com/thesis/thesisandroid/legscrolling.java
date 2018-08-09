package com.thesis.thesisandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.UUID;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import android.os.Handler;
import android.provider.SyncStateContract.Constants;

import org.w3c.dom.Text;

public class legscrolling extends AppCompatActivity {
    String[] exerciselistleg = {"Barbel Squat","Deadlift","Dumbbell Squat","Dumbbell Deadlift" };
    TextView mySpeed;
    TextView myReps;
    EditText editText;
    StringBuilder sb;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legscrolling);
        Spinner dropdown = findViewById(R.id.spinner3);
        mySpeed = (TextView)findViewById(R.id.dataspeed);
        myReps = (TextView)findViewById(R.id.datareps);
        editText = (EditText)findViewById(R.id.weightp);
        ImageButton starting = (ImageButton)findViewById(R.id.legstart);
        ImageButton ending = (ImageButton)findViewById(R.id.legend);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, exerciselistleg);
        dropdown.setAdapter(adapter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();

        starting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Toast.makeText(getBaseContext(), "Connecting...", Toast.LENGTH_SHORT).show();
                    openBT();
                    sendData("1");
                }
                catch (IOException ex)
                {
                    Toast.makeText(getBaseContext(), "Power Gloves connection failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    beginListenForData();
                    sendData("0");
                    closeBT();
                }
                catch (IOException ex)
                {
                    Toast.makeText(getBaseContext(), "Connection error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void openBT() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();

        Toast.makeText(getApplicationContext(), "Power Gloves connected.", Toast.LENGTH_SHORT).show();
    }

    void beginListenForData()
    {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = mmInputStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            final String string=new String(packetBytes,"UTF-8");
                            handler.post(new Runnable()
                            {
                                public void run()
                                {
                                    sb.append(string);
                                    int endOfLineIndex = sb.indexOf(";");
                                    if(endOfLineIndex > 0) {
                                        if(sb.charAt(0) == '#')
                                        {
                                            String reps = sb.substring(1,3);

                                            mySpeed.setText(reps);
                                        }
                                    }
                                }
                            });
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    void checkBTState()
    {
        if(mBluetoothAdapter == null)
        {
            Toast.makeText(getBaseContext(), "Bluetooth not supported in this device.", Toast.LENGTH_LONG).show();
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
                    mmDevice = device;
                    break;
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Power Gloves not found.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }

    }

    public void sendData(String input)
    {
        byte[] msgBuffer = input.getBytes();
        try {
            mmOutputStream.write(msgBuffer);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    void closeBT() throws IOException
    {
        stopWorker = true;
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
        Toast.makeText(getApplicationContext(), "Workout ended.", Toast.LENGTH_LONG).show();
    }
}
