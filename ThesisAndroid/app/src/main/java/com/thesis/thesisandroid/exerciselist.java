package com.thesis.thesisandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class exerciselist extends AppCompatActivity implements View.OnClickListener {
    private Spinner mySpinner;
    private EditText editText;
    private long timeCountInMilliSeconds = 1 * 60000;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }
    private TimerStatus timerStatus = TimerStatus.STOPPED;

    private ProgressBar progressBarCircle1;
    private TextView textViewTime1;
    private ImageView imageViewReset1;
    private ImageView imageViewStartStop1;
    private CountDownTimer countDownTimer;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerciselist);
        getSupportActionBar().hide();
         mySpinner = (Spinner) findViewById(R.id.spinnerExercise);
         editText = (EditText) findViewById(R.id.weightp);

        // method call to initialize the views
        initViews();
        // method call to initialize the listeners
        initListeners();


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(exerciselist.this,R.layout.spinner_item,
                getResources().getStringArray(R.array.Exercise));
        myAdapter.setDropDownViewResource(R.layout.spinner_item);
        mySpinner.setAdapter(myAdapter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();
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
    public void openActivity_start(){
        Intent intent = new Intent(this, duringexercise.class);
        intent.putExtra("Exercise name", mySpinner.getSelectedItem().toString());
        intent.putExtra("Weight", editText.getText().toString());
        startActivity(intent);
    }

    private void initViews() {
        progressBarCircle1 = (ProgressBar) findViewById(R.id.progressBarCircle);
        textViewTime1 = (TextView) findViewById(R.id.textViewTime);
        imageViewReset1 = (ImageView) findViewById(R.id.imageViewReset);
        imageViewStartStop1 = (ImageView) findViewById(R.id.imageViewStartStop);
    }

    /**
     * method to initialize the click listeners
     */
    private void initListeners() {
        imageViewReset1.setOnClickListener(this);
        imageViewStartStop1.setOnClickListener(this);
    }

    /**
     * implemented method to listen clicks
     *
     * @param view
     */
   public void onClick(View view) {
           switch (view.getId()) {
               case R.id.imageViewReset:
                   reset();
                   break;
               case R.id.imageViewStartStop:
                   startStop();
                   break;
           }
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

    /**
     * method to reset count down timer
     */
    private void reset() {
        stopCountDownTimer();
        startCountDownTimer();
    }


    /**
     * method to start and stop count down timer
     */
    private void startStop() {
        if (timerStatus == TimerStatus.STOPPED) {

            // call to initialize the timer values
            setTimerValues();
            // call to initialize the progress bar values
            setProgressBarValues();
            // showing the reset icon
            imageViewReset1.setVisibility(View.VISIBLE);
            // changing play icon to stop icon
            imageViewStartStop1.setImageResource(R.drawable.icon_cancel);
            // making edit text not editable
            // changing the timer status to started
            timerStatus = TimerStatus.STARTED;
            // call to start the count down timer
            startCountDownTimer();

        } else {

            // hiding the reset icon
            imageViewReset1.setVisibility(View.GONE);
            // changing stop icon to start icon
            imageViewStartStop1.setImageResource(R.drawable.icon_start);
            // making edit text editable
            // changing the timer status to stopped
            timerStatus = TimerStatus.STOPPED;
            stopCountDownTimer();

        }

    }

    /**
     * method to initialize the values for count down timer
     */
    private void setTimerValues() {
        int time = 5;
        // assigning values after converting to milliseconds
        timeCountInMilliSeconds = time * 1000;
    }

    /**
     * method to start count down timer
     */
    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime1.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle1.setProgress((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {

                textViewTime1.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                // call to initialize the progress bar values
                setProgressBarValues();
                // hiding the reset icon
                imageViewReset1.setVisibility(View.GONE);
                // changing stop icon to start icon
                imageViewStartStop1.setImageResource(R.drawable.icon_start);
                // making edit text editable
                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED;

                openActivity_start();
            }

        }.start();
        countDownTimer.start();
    }

    /**
     * method to stop count down timer
     */
    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    /**
     * method to set circular progress bar values
     */
    private void setProgressBarValues() {

        progressBarCircle1.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle1.setProgress((int) timeCountInMilliSeconds / 1000);
    }


    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d",
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));


        return hms;


    }

    }

