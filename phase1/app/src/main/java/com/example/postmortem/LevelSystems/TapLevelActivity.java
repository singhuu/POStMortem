package com.example.postmortem.LevelSystems;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.postmortem.R;

public class TapLevelActivity extends LevelActivity {

  public TapLevelActivity() {
    super(R.layout.tap_level, new TapLevel(difficulty));
  }

  /**
   * Perform initial setup of layout. This includes setting the value of textboxes, buttons, etc.
   */
  @Override
  protected void setup() {
    TextView textView = findViewById(R.id.textViewTapImage);
    textView.setText("0");
  }

  public void pressButton(View target) {
    // Do something in response to imageButton press
    TapLevel tapLevel = (TapLevel) super.level;
    tapLevel.incrementTimesPressed();
    int timesPressed = tapLevel.getTimesPressed();
    TextView textView = findViewById(R.id.textViewTapImage);
    textView.setText(Integer.toString(timesPressed));

    // now switch image in button
    ImageButton imageButton = findViewById(R.id.imageButton);
    if (timesPressed % 2 == 0) {
      // set it to open
      imageButton.setImageResource(R.mipmap.laptop_open_foreground);
    } else {
      // set it to closed
      imageButton.setImageResource(R.mipmap.laptop_closed_foreground);
    }
  }
}
