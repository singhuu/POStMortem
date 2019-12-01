package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;

import com.example.postmortem.GameManager;
import com.example.postmortem.R;

public class OptionMenuActivity extends MenuActivity {

    GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_menu);
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        gameManager = (GameManager) extras.get(GameManager.INTENT_NAME);
    }

    /**
     * return to main menu
     *
     * @param target the current view of the game
     */
    public void cancel(View target) {

        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra(GameManager.INTENT_NAME, gameManager);
        startActivity(intent);
    }

    /**
     * Apply level changes to game manager
     *
     * @param target the current view of the game
     */
    public void apply(View target) {
        // first, get value of levels
        EditText levelsToPlay = findViewById(R.id.levelsToPlay);
        int levels = Integer.valueOf(levelsToPlay.getText().toString());

        // check if levels >= 4
        if (levels < 4) {
            invalidLevelNum(target);
            return;
        }

        //then get remaining values
        Switch adsEnabled = findViewById(R.id.adsEnabled);
        boolean ads = adsEnabled.isChecked();
        Spinner difficultySelect = findViewById(R.id.difficultySelect);
        String difficulty = difficultySelect.getSelectedItem().toString();
        // write option changes to gamemanager

        writeOptions(levels, ads, difficulty);
        gameManager.start(this);
    }

    /**
     * Options menu of the user
     *
     * @param levels     the current level
     * @param ads        checks if ads are turned on
     * @param difficulty the current difficulty
     */
    private void writeOptions(int levels, boolean ads, String difficulty) {
        gameManager.setLevels(levels);
        gameManager.setRunningAds(ads);
        if (difficulty.equals("CS Minor")) {
            gameManager.setDifficulty(1);
        } else if (difficulty.equals("CS Major")) {
            gameManager.setDifficulty(2);
        } else {
            gameManager.setDifficulty(3);
        }
    }

    /**
     * Checks if value entered is invalid and displays a message
     *
     * @param target the current view of the game
     */
    public void invalidLevelNum(View target) {
        // code modded from https://medium.com/@suragch/making-an-alertdialog-in-android-2045381e2edb
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Must play at least 4 levels.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
