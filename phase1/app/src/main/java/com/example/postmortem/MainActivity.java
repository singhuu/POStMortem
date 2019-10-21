package com.example.postmortem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

  /** The spinner menu items */
  public final static String MENU_ACTIVITY = "menu activity";

  /** The spinners array */
  private final String[] activities = {MENU_ACTIVITY};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setUpSpinner();

  }

  private void setUpSpinner(){

    Spinner spinner = findViewById(R.id.spinner);
    ArrayAdapter adapter = new ArrayAdapter(
            this, android.R.layout.simple_spinner_item, activities);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);

  }

  public void transitionToActivity(View view){

    Spinner selector = findViewById(R.id.spinner);
    String choice = selector.getSelectedItem().toString();

    goToActivity(choice);

  }

  private void goToActivity(String activity){

    Intent intent;

    // pick the activity to go to
    switch(activity){

      case MENU_ACTIVITY:
        intent = new Intent(this, MenuActivity.class);
        break;

      default:
        intent = new Intent(this, MenuActivity.class);
        break;

    }

    startActivity(intent);

  }

}
