package com.example.postmortem.DataTypes;

public class Hiscore {

    private String username;
    private int score;

    public Hiscore(String username, int score){
        this.score = score;
        this.username = username;
    }

    /**
     * Gets the value of the hiscore
     * @return the value of the hiscore
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the username of the user who the hiscore was achieved by
     * @return the username of the user who the hiscore was achieved by
     */
    public String getUsername() {
        return username;
    }
}
