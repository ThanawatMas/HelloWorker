package com.settrade.helloworker;

import android.os.Bundle;
import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupSampleNotifyTest();
    }

    //we set a tag to be able to cancel all work of this type if needed
    public static final String workTag = "notificationWork";
    public static final String DBEventIDTag = "TAG";
    public static final int DBEventID = 1;

    private void setupSampleNotifyTest() {
        //store DBEventID to pass it to the PendingIntent and open the appropriate event page on notification click
        Data inputData = new Data.Builder().putInt(DBEventIDTag, DBEventID).build();
        // we then retrieve it inside the NotifyWorker with:
//         final int DBEventID = getInputData().getInt(DBEventIDTag, ERROR_VALUE);

        long time = 3000;

        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotifyWorker.class)
                .setInitialDelay(/*calculateDelay(event.getDate())*/ time, TimeUnit.MILLISECONDS)
                .setInputData(inputData)
                .addTag(workTag)
                .build();


        WorkManager.getInstance().enqueue(notificationWork);

        Log.d("working", "start at: " + (new Date()).toString());

        //alternatively, we can use this form to determine what happens to the existing stack
        //WorkManager.getInstance().beginUniqueWork(workTag, ExistingWorkPolicy.REPLACE, notificationWork);

        //ExistingWorkPolicy.REPLACE - Cancel the existing sequence and replace it with the new one
        //ExistingWorkPolicy.KEEP - Keep the existing sequence and ignore your new request
        //ExistingWorkPolicy.APPEND - Append your new sequence to the existing one,
                //running the new sequence's first task after the existing sequence's last task finishes

    }
}
