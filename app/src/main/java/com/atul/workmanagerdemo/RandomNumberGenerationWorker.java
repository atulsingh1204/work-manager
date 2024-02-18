package com.atul.workmanagerdemo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;

public class RandomNumberGenerationWorker extends Worker {

    Context context;
    WorkerParameters workerParameters;

    private int mRandomNumber;
    private boolean mIsRandomGenerationOn;

    private final int MIN = 0;
    private final int MAX = 100;

    public RandomNumberGenerationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.context = context;
        this.workerParameters = workerParams;
        mIsRandomGenerationOn = true;
    }


    private void startRandomGenerator() {

        int i = 0;
//        while (i < 5 && isStopped()) {
        while (i < 5 ) {
            try {

                Thread.sleep(1000);
                if (mIsRandomGenerationOn) {
                    mRandomNumber = new Random().nextInt(MAX) + MIN;

                    Log.e("check", "Thread id: " + Thread.currentThread().getId() + "Random Number: " + mRandomNumber);
                    i++;
                }

            } catch (Exception e) {
                Log.e("check", "exception: " + e);
            }
        }
    }

    @NonNull
    @Override
    public Result doWork() {
        startRandomGenerator();
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.e("check", "Worker has been cancelled");
    }
}
