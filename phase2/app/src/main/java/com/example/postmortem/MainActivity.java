package com.example.postmortem;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.DataTypes.HiscoreManager;
import com.example.postmortem.DataTypes.UserManager;
import com.example.postmortem.MenuSystems.UserSelectMenuActivity;

public class MainActivity extends AppCompatActivity {

  /**
   * Main Context variable that modifies the current context if necessary
   */
  private static Context mContext;
  /**
   * Decides the time after which the notification is triggered when the app is left idle
   */
  final int notificationTime = 60 * 60 * 1000;

  /**
   * Getter function that returns the context
   *
   * @return mContext, the context in the main activity
   */
  public static Context getMContext() {
    return mContext;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mContext = this;
    initializeNotifications();
    SoundManager sm = new SoundManager(mContext);
    initializeGame();
    startGame();
    sm.startMainMusic();
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
   * Creates notification channels, intents and defines conditions for notifications to be called.
   */
  private void initializeNotifications() {
    NotificationChannel notificationChannel;
    notificationChannel = new NotificationChannel("primary",
            "the_only_one", NotificationManager.IMPORTANCE_HIGH);

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    if (notificationManager != null) {
      notificationManager.createNotificationChannel(notificationChannel);
    }
    AlarmManager mAlarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1234,
            new Intent(getApplicationContext(), NotificationUpdate.class), 0);
    if (mAlarmManager != null) {
      mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + notificationTime, pendingIntent);
    }
  }


}
