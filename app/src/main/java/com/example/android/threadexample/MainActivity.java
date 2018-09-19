package com.example.android.threadexample;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button buttonStartThread;

    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartThread = findViewById(R.id.button_start_thread);
    }

    public void startThread(View view) {
        stopThread = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10; i++) {
                    if (stopThread) {
                        return;
                    }
                    if (i == 5) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buttonStartThread.setText("50%");
                            }
                        });
                    }
                    Log.d(TAG, "run: " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stopThread(View view) {
        stopThread = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                buttonStartThread.setText("START");
            }
        });
    }
}
