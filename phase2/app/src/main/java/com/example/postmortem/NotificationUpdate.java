package com.example.postmortem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationUpdate extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        updateNotification(context);
    }

    /**
     * Creates the notifications and the contents in it and notifies the asystem
     * @param context the current state of the app
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateNotification(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,1234,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context.getApplicationContext(), "primary")
                .setContentTitle("Why don't you come play again")
                .setContentText("We miss you, we do.")
                .setSmallIcon(R.drawable.meric)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(manager!=null) manager.notify(123, notification);
    }

}