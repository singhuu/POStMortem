package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.postmortem.DataTypes.Hiscore;
import com.example.postmortem.DataTypes.HiscoreManager;
import com.example.postmortem.DataTypes.User;
import com.example.postmortem.GameManager;
import com.example.postmortem.R;

import java.util.List;

public class GameOverMenuActivity extends MenuActivity {

    /**
     * Game Manager object
     */
    GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_menu);
        Bundle extras = getIntent().getExtras();
        gameManager = (GameManager) extras.get(GameManager.INTENT_NAME);
        populateCurrentScores();
        populateHighScores();
    }

    /**
     * Appends the current score of the user
     */
    private void populateCurrentScores() {
        User user = gameManager.getActiveUser();
        StringBuilder scores = new StringBuilder();
        scores.append("Tap Level Score: " + user.getTapScore() + "\n");
        scores.append("Type Level Score: " + user.getTypeScore() + "\n");
        scores.append("Pickup Level Score: " + user.getPickupScore() + "\n");
        scores.append("Swipe Level Score: " + user.getSwipeScore() + "\n");
        scores.append("Total Score: " + user.getScore());

        TextView currentScores = findViewById(R.id.currentScores);
        currentScores.setText(scores.toString());
    }

    /**
     * Appends the current high score of the user
     */
    private void populateHighScores() {
        StringBuilder scores = new StringBuilder();

        List<Hiscore> hiscores = HiscoreManager.getManager().getHiscores();
        int i = 0;
        for (Hiscore hiscore : hiscores) {
            scores.append("#");
            scores.append(i + 1);
            scores.append(": ");
            scores.append(hiscore);
            scores.append("\n");
            i++;
        }

        TextView hiScores = findViewById(R.id.highScores);
        hiScores.setText(scores.toString());
    }

    /**
     * Restarts the game if called
     *
     * @param target the current view of the application
     */
    public void restart(View target) {
        Intent intent = new Intent(this, OptionMenuActivity.class);
        intent.putExtra(GameManager.INTENT_NAME, gameManager);
        startActivity(intent);
    }

    /**
     * Return to main menu.
     */
    public void quit(View target) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra(GameManager.INTENT_NAME, gameManager);
        startActivity(intent);
    }
}
