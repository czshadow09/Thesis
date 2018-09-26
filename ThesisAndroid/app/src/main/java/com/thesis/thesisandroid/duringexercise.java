package com.thesis.thesisandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class duringexercise extends AppCompatActivity implements View.OnClickListener{
    private ProgressBar progressbarReps;
    BarChart barChart;
    private final String DEVICE_ADDRESS="00:18:E5:04:81:A5";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    BluetoothDevice mmDevice;
    BluetoothSocket mmSocket;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;
    boolean deviceConnected;
    Random random;
    TextView tv;
    TextView weigh;
    TextView rep;
    TextView pow;
    TextView oR;
    TextView timer;
    TextView textView;
    Button buttonStart;
    double times = 0;
    double reps;
    double repets = 0;
    double sumDis = 0;
    double kilos = 0;
    double velocity = 0;
    double power = 0;
    double repmax = 0;
    int index = 0;
    StringBuilder sb = new StringBuilder();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duringexercise);
        tv = findViewById(R.id.textViewExercise);
        weigh = findViewById(R.id.weightp);
        timer = findViewById(R.id.time);
        pow = findViewById(R.id.velo);
        rep = findViewById(R.id.textViewReps);
        oR = findViewById(R.id.oneRM);
        textView = (TextView) findViewById(R.id.Sensor);
        barChart = (BarChart)findViewById(R.id.Powergraph);
        final ArrayList<BarEntry> barEntries = new ArrayList<>();
        BarDataSet barDataSet = new BarDataSet(barEntries, "Reps");
        final ArrayList<String> barReps = new ArrayList<>();
        BarData barData = new BarData(barReps, barDataSet);
        barChart.setData(barData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        getSupportActionBar().hide();
        initViews1();
        initListeners1();
        onBackPressed();
        getExercise();
        getWeight();
    }

    public void chooseExercise() {
        String exercise = tv.getText().toString();
        if(exercise == "Benchpress (chest)") {
            onBenchPress();
        }
        else if(exercise == "Deadlift (back)") {
            onDeadLift();
        }
        else if(exercise == "Barbell row (back)") {
            onBarbellRow();
        }
        else if(exercise == "Barbell Curl (arm)") {
            onBarbellCurl();
        }
    }

    public void onBenchPress() {
        final double iniDis = 2;
        final double finDis = 3;
        Thread t = new Thread(){
            @Override
            public void run(){
                while(!isInterrupted()){

                    try {
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                times++;
                                sumDis = finDis - iniDis;
                                velocity = sumDis / times;
                                kilos = Double.parseDouble(weigh.getText().toString());
                                double newt = kilos * 9.8066500286389;
                                power = newt * velocity;
                                double round = Math.round(power);
                                int result = (int) round;
                                pow.setText(String.valueOf(result));
                                times = 0;
                                double accel = Double.parseDouble(textView.getText().toString());
                                if(accel >= 2) {
                                    repets++;
                                    rep.setText(String.valueOf((int)repets));
                                    try {
                                        Thread.sleep(1500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
    public void onDeadLift() {
        final double iniDis = 2;
        final double finDis = 4;
        Thread t = new Thread(){
            @Override
            public void run(){
                while(!isInterrupted()){

                    try {
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                times++;
                                if(times == 2){
                                    sumDis = finDis - iniDis;
                                    velocity = sumDis / times;
                                    kilos = Double.parseDouble(weigh.getText().toString());
                                    double newt = kilos * 9.8066500286389;
                                    power = newt * velocity;
                                    double round = Math.round(power);
                                    int result = (int) round;
                                    pow.setText(String.valueOf(result));
                                    times = 0;
                                }
                                repets++;
                                rep.setText(String.valueOf((int)repets));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
    public void onBarbellRow() {
        final double iniDis = 2;
        final double finDis = 5;
        Thread t = new Thread(){
            @Override
            public void run(){
                while(!isInterrupted()){

                    try {
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                times++;
                                if(times == 2){
                                    sumDis = finDis - iniDis;
                                    velocity = sumDis / times;
                                    kilos = Double.parseDouble(weigh.getText().toString());
                                    double newt = kilos * 9.8066500286389;
                                    power = newt * velocity;
                                    double round = Math.round(power);
                                    int result = (int) round;
                                    pow.setText(String.valueOf(result));
                                    times = 0;
                                }
                                repets++;
                                rep.setText(String.valueOf((int)repets));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
    public void onBarbellCurl() {
        final double iniDis = 2;
        final double finDis = 6;
        Thread t = new Thread(){
            @Override
            public void run(){
                while(!isInterrupted()){

                    try {
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                times++;
                                if(times == 2){
                                    sumDis = finDis - iniDis;
                                    velocity = sumDis / times;
                                    kilos = Double.parseDouble(weigh.getText().toString());
                                    double newt = kilos * 9.8066500286389;
                                    power = newt * velocity;
                                    double round = Math.round(power);
                                    int result = (int) round;
                                    pow.setText(String.valueOf(result));
                                    times = 0;
                                }
                                repets++;
                                rep.setText(String.valueOf((int)repets));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
    public boolean BTinit()
    {
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    mmDevice=iterator;
                    found=true;
                    break;
                }
            }
        }
        return found;
    }

    public boolean BTconnect()
    {
        boolean connected=true;
        try {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(PORT_UUID);
            mmSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                mmOutputStream=mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mmInputStream=mmSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return connected;
    }

    void beginListenForData()
    {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {
                    try
                    {
                        int byteCount = mmInputStream.available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            mmInputStream.read(rawBytes);
                            final String string=new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run()
                                {
                                    sb.append(string);
                                    String[] sent = sb.toString().split(";");
                                    for(String sensor : sent) {
                                        textView.setText(sensor);
                                    }
                                }

                            });

                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }
    @Override
    public void onBackPressed() {
    }
    public void openresult(){
        double weight = Double.parseDouble(weigh.getText().toString());
        repmax = weight * (1 + repets / 30);
        double roundOff = Math.round(repmax);
        int result = (int) roundOff;
        oR.setText(String.valueOf(result));
        Intent intent = new Intent(this, result.class);
        intent.putExtra("Exercise name", tv.getText().toString());
        intent.putExtra("Weight", weigh.getText().toString() + " kg");
        intent.putExtra("reps", rep.getText().toString());
        intent.putExtra("RepMax", oR.getText().toString());
        intent.putExtra("power", pow.getText().toString());
        startActivity(intent);
    }
    private void initViews1() {
        progressbarReps = (ProgressBar) findViewById(R.id.progressBarCircleReps);
        buttonStart = findViewById(R.id.startButton);

    }
    private void initListeners1() {
        progressbarReps.setOnClickListener(this);
        buttonStart.setOnClickListener(this);

    }
    public void getExercise() {
        Bundle bundle = getIntent().getExtras();
        String exerciseName = bundle.getString("Exercise name");
        tv.setText(exerciseName);
    }
    public void getWeight(){
        Bundle bundle = getIntent().getExtras();
        String weight = bundle.getString("Weight");
        weigh.setText(weight);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.progressBarCircleReps:
                openresult();
                break;
            case R.id.startButton:
                if(BTinit())
                {
                    if(BTconnect())
                    {
                        deviceConnected=true;
                        beginListenForData();
                        onBenchPress();
                        break;
                    }
                }
        }
    }

    void onChartView() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        random = new Random();
        float max = 100f;
        float value = 0f;
        value = random.nextFloat();
        barEntries.add(new BarEntry(value, 0));
        barEntries.add(new BarEntry(value, 1));
        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");

        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("April");
        theDates.add("May");

        BarData theData = new BarData(theDates, barDataSet);
        barChart.setData(theData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
    }
    void closeBT() throws IOException
    {
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
        Toast.makeText(getApplicationContext(), "Power Gloves disconnected.", Toast.LENGTH_SHORT).show();
    }


}
