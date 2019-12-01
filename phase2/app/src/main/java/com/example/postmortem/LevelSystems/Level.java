package com.example.postmortem.LevelSystems;

import android.app.Activity;

public abstract class Level extends Activity {

    /**
     * The difficulty of the level
     */
    protected int difficulty;

    /**
     * variable that stores wrong choice
     */
    protected int wrongChoiceCountdown = 0;

    protected int score = 0;

    /**
     * Constructor that stores the difficulty
     * Easy = 1
     * Medium = 2
     * Hard = 3
     * No more than 3 levels
     */
    Level(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Abstract getter method that returns high score
     */
    public abstract int getScore();
}
