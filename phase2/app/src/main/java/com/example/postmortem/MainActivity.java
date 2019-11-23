package com.example.postmortem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.MenuSystems.GameMenu;



public class MainActivity extends AppCompatActivity {

  private static Context mContext;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mContext = this;

    initializeGame();
    startGame();

  }

  private void startGame() {
    GameManager gameManager = new GameManager();

    Intent intent = GameMenu.openMenu(this, GameMenu.LOGIN_MENU);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);

    startActivity(intent);
  }

  private void initializeGame() {
    UserLoader.findFilePath(this);
    UserLoader.load();
  }

  public static Context getmContext() {
    return mContext;
  }


}
