package com.settrade.helloworker;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.work.Result;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotifyWorker extends Worker {
    private WorkerParameters params;

    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        this.params = params;
    }

    @NonNull
    @Override
    public Result doWork() {
        // Method to trigger an instant notification
        triggerNotification();

        Log.d("working", "doWork: " + System.currentTimeMillis());
        Log.d("working", "params: " + toString(params));
        Log.d("working", "work at: " + (new Date()).toString());

        return Result.success();
        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)
    }

    private void triggerNotification() {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
    }

    public String toString(WorkerParameters target) {
        return "WorkerParametersWrapper{" +
                "\ntarget=" + target +
                "{" +
                "id:" + target.getId() +
                "\n,tags:" + target.getTags() +
                "\n,inputData:" + target.getInputData() +
                '}';
    }
}