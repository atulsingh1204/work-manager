package com.atul.workmanagerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

public class WorkManagerChaining extends AppCompatActivity {

    WorkManager workManager;
    WorkRequest workRequest1, workRequest2, workRequest3;
    Button btn_start_service, btn_stop_service;
    private boolean mStopLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager_chaining);

        btn_start_service = findViewById(R.id.btn_start_service);
        btn_stop_service = findViewById(R.id.btn_stop_service);

        workManager = WorkManager.getInstance(getApplicationContext());
        workRequest1 = new OneTimeWorkRequest.Builder(RandomNumberGenerator1.class).addTag("worker1").build();
        workRequest2 = new OneTimeWorkRequest.Builder(RandomNumberGenerator2.class).addTag("worker2").build();
        workRequest3 = new OneTimeWorkRequest.Builder(RandomNumberGenerator3.class).addTag("worker3").build();


        btn_stop_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                workManager.cancelAllWorkByTag("worker2");
            }
        });

        btn_start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mStopLoop = true;
                workManager.beginWith((OneTimeWorkRequest) workRequest1).then((OneTimeWorkRequest) workRequest2).then((OneTimeWorkRequest) workRequest3).enqueue();
//                workManager.beginWith(Arrays.asList(workRequest1, workRequest2)).then(workRequest3).enqueue();

            }
        });


    }
}