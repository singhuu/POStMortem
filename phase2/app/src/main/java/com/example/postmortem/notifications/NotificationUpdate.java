package com.example.postmortem.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.postmortem.R;

public class NotificationUpdate extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        //NEED A RESCHEDULE?
        updateNotification(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateNotification(Context context){
        Notification notification = new Notification.Builder(context.getApplicationContext(), "default")
                .setContentTitle("Why don't you come play again")
                .setContentText("I miss you")
                .setSmallIcon(R.drawable.meric)
                .setAutoCancel(true)
                .build();
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(manager!=null) manager.notify(123, notification);
    }
}