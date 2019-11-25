package com.example.postmortem.MenuSystems;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.R;

public class MainMenuActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_menu);
  }

  public void continueGame(View target) {
    // TODO ADD FUNCTIONALITY
  }

  public void start(View target) {
    // TODO ADD FUNCTIONALITY
  }

  public void openOptions(View target) {
    // TODO ADD FUNCTIONALITY
  }

  public void logout(View target) {
    // TODO ADD FUNCTIONALITY
  }

  /**
   * Exit the game, return to Android.
   */
  public void quit(View target) {
    this.finish();
    System.exit(0);
  }
}
