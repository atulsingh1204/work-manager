package com.atul.workmanagerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button btn_start_service, btn_stop_service, btn_chaining;
    WorkManager workManager;
    WorkRequest workRequest;

    private boolean mStopLoop;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start_service = findViewById(R.id.btn_start_service);
        btn_stop_service = findViewById(R.id.btn_stop_service);
        btn_chaining = findViewById(R.id.btn_chaining);

        workManager = WorkManager.getInstance(getApplicationContext());
        workRequest = new PeriodicWorkRequest.Builder(RandomNumberGenerationWorker.class, 15, TimeUnit.MINUTES).build();


        btn_stop_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                workManager.cancelWorkById(workRequest.getId());
            }
        });

        btn_start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mStopLoop = true;
                workManager.enqueue(workRequest);

            }
        });


        btn_chaining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, WorkManagerChaining.class);
                startActivity(intent);
            }
        });


    }

}