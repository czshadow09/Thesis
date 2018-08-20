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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class duringexercise extends AppCompatActivity implements View.OnClickListener{
    private ProgressBar progressbarReps;
    private exerciselist mlist;
    private Handler mhandler;
    ConnectedThread mConnectedThread;
    BarChart barChart;
    BluetoothSocket mmSocket;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Random random;
    TextView tv;
    TextView weigh;
    TextView rep;
    TextView pow;
    TextView oR;
    TextView timer;
    double times = 0;
    double reps;
    double repets = 0;
    double iniDis = 1.52;
    double finDis = 2;
    double sumDis = 0;
    double kilos = 0;
    double velocity = 0;
    double power = 0;
    double repmax = 0;
    int index = 0;
    StringBuilder sb = new StringBuilder();

    private final static int MESSAGE_READ = 0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duringexercise);
        mlist = new exerciselist();
        tv = findViewById(R.id.textViewExercise);
        weigh = findViewById(R.id.weightp);
        timer = findViewById(R.id.time);
        pow = findViewById(R.id.velo);
        rep = findViewById(R.id.textViewReps);
        oR = findViewById(R.id.oneRM);
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

        mhandler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if(msg.what == MESSAGE_READ) {
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    sb.append(readMessage);
                    int endOfLineIndex = sb.indexOf(";");
                    if(endOfLineIndex > 0) {
                        String dataInPrint = sb.substring(0, endOfLineIndex);

                        if(sb.charAt(0) == '#')
                        {
                            String acc = sb.substring(1,5);

                            rep.setText(acc);
                        }
                        sb.delete(0, sb.length());
                    }
                }
            }
        };
    }
    @Override
    public void onBackPressed() {
    }
    public void getData() {
        if(mConnectedThread != null){
            mConnectedThread.write("1");
        }
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

    }
    private void initListeners1() {
        progressbarReps.setOnClickListener(this);

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

    private class ConnectedThread extends Thread {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream mmInputStream;
        //private final OutputStream mmOutputStream;

        public ConnectedThread(BluetoothSocket socket) {
            bluetoothSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                //tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInputStream = tmpIn;
            //mmOutputStream = tmpOut;
        }//endofConnectedThread(BluetoothSocket socket)


        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInputStream.read(buffer);

                    // Send the obtained bytes to the UI Activity
                    mhandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }//end of void run
        //write method
        public void write(String input) {
            byte[] bytes = input.getBytes();//converts entered String into bytes
            try {
                mmInputStream.read(bytes);//write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failed", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }//end of connectecthread


}
