package com.example.postmortem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.LevelSystems.PickUpLevel;
import com.example.postmortem.LevelSystems.PickUpLevelActivity;
import com.example.postmortem.LevelSystems.TapLevelActivity;
import com.example.postmortem.MenuSystems.GameMenu;

public class MainActivity extends AppCompatActivity {

  /**
   * The spinner menu items
   */
  public final static String MENU_ACTIVITY = "Menu Activity";
  public final static String TAP_LEVEL = "Tap Level";
  public final static String PICKUP_LEVEL = "Pickup Level";

  /**
   * The spinners array
   */
  private final String[] activities = {MENU_ACTIVITY, TAP_LEVEL, PICKUP_LEVEL};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setUpSpinner();
  }

  private void setUpSpinner() {

    Spinner spinner = findViewById(R.id.spinner);
    ArrayAdapter adapter = new ArrayAdapter(
            this, android.R.layout.simple_spinner_item, activities);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);

  }

  public void transitionToActivity(View view) {

    Spinner selector = findViewById(R.id.spinner);
    String choice = selector.getSelectedItem().toString();

    goToActivity(choice);

  }

  private void goToActivity(String activity) {

    Intent intent;

    // pick the activity to go to
    switch (activity) {

      case MENU_ACTIVITY:
        UserLoader.findFilePath(this);
        UserLoader.load();
        intent  = GameMenu.openMenu(this, GameMenu.LOGIN_MENU);
        break;

      case PICKUP_LEVEL:
        intent = new Intent(this, PickUpLevelActivity.class);
        break;

      case TAP_LEVEL:
        intent = new Intent(this, TapLevelActivity.class);
        break;

      default:
        intent = new Intent(this, MainActivity.class);
        break;

    }

    startActivity(intent);

  }

}
