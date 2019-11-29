package com.example.postmortem.LevelSystems;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.postmortem.MainActivity;
import com.example.postmortem.R;
import com.example.postmortem.SoundManager;

public class TapLevelActivity extends LevelActivity {

    /**
     * TextView object that stores the timer text
     */
    TextView timerText;
    private SoundManager sm = new SoundManager(MainActivity.get_m_Context());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tap_level);

        gameManager = getIntent().getParcelableExtra("GAME_MANAGER");

        getIntent().getIntExtra("DIFFICULTY", difficulty);
        level = new TapLevel(difficulty);

        TextView textView = findViewById(R.id.score);
        textView.setText("0");

        timeLeft = 30 - (difficulty * 10);
        timerText = findViewById(R.id.timer);
        timerText.setText(timeLeft + 1 + " Seconds Remaining");

        startTimer(timeLeft); // TODO temp until timeLeft passed in

        ImageView meric = findViewById(R.id.mericImage);
        meric.setVisibility(View.INVISIBLE);
        TextView mericQuote = findViewById(R.id.mericSpeech);
        mericQuote.setVisibility(View.INVISIBLE);
    }

    /**
     * Displays an image when the button is pressed a certain number of times
     *
     * @param target
     */
    public void pressButton(View target) {
        // Do something in response to imageButton press
        TapLevel tapLevel = (TapLevel) super.level;
        tapLevel.incrementTimesPressed();
        int timesPressed = tapLevel.getTimesPressed();
        TextView textView = findViewById(R.id.score);
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

        if (timesPressed == 100) {
            displayMeric();
            sm.playWowEffect();
        }
    }

    /**
     * Display an image when called
     */
    private void displayMeric() {
        ImageView meric = findViewById(R.id.mericImage);
        meric.setVisibility(View.VISIBLE);
        TextView mericQuote = findViewById(R.id.mericSpeech);
        mericQuote.setVisibility(View.VISIBLE);
    }

    /**
     * Handles the count tick
     */
    @Override
    public void countTickHandler() {
        timerText.setText(timeLeft + 1 + " Seconds Remaining");
    }

    /**
     * updates total score
     */
    @Override
    public void countFinishHandler() {
        gameManager.totalScore += level.getScore();
        gameManager.play(this);
    }

    /**
     * Saves the score
     */
    @Override
    public void saveScore() {
        gameManager.getActiveUser().setScore(level.getScore(), LevelType.TAP);
    }

}
