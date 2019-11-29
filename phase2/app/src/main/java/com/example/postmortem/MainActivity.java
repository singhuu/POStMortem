package com.example.postmortem;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.DataTypes.HiscoreManager;
import com.example.postmortem.DataTypes.UserManager;
import com.example.postmortem.MenuSystems.UserSelectMenuActivity;
import com.example.postmortem.notifications.NotificationUpdate;


public class MainActivity extends AppCompatActivity {

    /**
     * Main Context variable that modifies the current context if necessary
     */
    private static Context mContext;
    AlarmManager mAlarmManager;
    final long intervalPeriod=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel("default",
                    "primary", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null)
            {
                manager.createNotificationChannel(notificationChannel);
            }

            mAlarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
            PendingIntent intent = PendingIntent.getBroadcast(getApplicationContext(), 1234,
                    new Intent(getApplicationContext(), NotificationUpdate.class), 0);

            mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + intervalPeriod, intent);
        }
        initializeGame();
        startGame();

    }

    /**
     * Creates and initializes the intents to start the game
     */
    private void startGame() {
        GameManager gameManager = new GameManager();
        Intent intent = new Intent(this, UserSelectMenuActivity.class);
        intent.putExtra(GameManager.INTENT_NAME, gameManager);
        startActivity(intent);
    }

    /**
     * Finds the File Path and Loads the game
     */
    private void initializeGame() {
        String path = getFilesDir().getPath();
        UserManager.initialize(path);
        HiscoreManager.initialize(path);
    }

    /**
     * Getter function that returns the context
     *
     * @return mContext, the context in the main activity
     */
    public static Context get_m_Context() {
        return mContext;
    }


}
