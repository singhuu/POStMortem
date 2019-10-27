package com.example.postmortem.LevelSystems;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.LevelSystems.TapLevel;
import com.example.postmortem.R;

public class TapLevelActivity extends AppCompatActivity {
  private TapLevel tapLevel = new TapLevel(-1);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tap_level);

    TextView textView = findViewById(R.id.textViewTapImage);
    textView.setText(Integer.toString(tapLevel.getTimesPressed()));
  }


  public void pressButton(View target) {
    // Do something in response to imageButton press
    tapLevel.incrementTimesPressed();
    int timesPressed = tapLevel.getTimesPressed();
    TextView textView = findViewById(R.id.textViewTapImage);
    textView.setText(Integer.toString(timesPressed));

    // now switch image in button
    ImageButton imageButton = findViewById(R.id.imageButton);
    if (timesPressed % 2 == 0) {
      //set it to open
      imageButton.setImageResource(R.mipmap.laptop_open_foreground);
    } else {
      //set it to closed
      imageButton.setImageResource(R.mipmap.laptop_closed_foreground);
    }
  }
}
