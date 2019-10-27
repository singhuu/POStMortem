package com.example.postmortem.LevelSystems;

public class TypeLevel extends Level {
    private String timerString = "00:30";

    /**
     * Initiates the TypeLevel and sets the difficulty using the Level constructor
     */
    public TypeLevel(int difficulty) {
        super(difficulty);
    }

    @Override
    public int getScore() {
        return 0;
    }
}
