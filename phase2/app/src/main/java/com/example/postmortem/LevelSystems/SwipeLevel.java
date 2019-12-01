package com.example.postmortem.LevelSystems;

public class SwipeLevel extends Level {

    /**
     * 2-D array that stores the position of obstacle trees
     */
    int[][] obstacleTiles;
    /**
     * Stores current position of the player
     */
    int currPlayerCol;

    public SwipeLevel(int difficulty) {
        super(difficulty);

        obstacleTiles = new int[5][3];
        currPlayerCol = 1;
    }

    /**
     * Checks if any lane is open
     *
     * @return true if there is on open lane, false otherwise
     */
    public boolean checkOpenLane() {
        int finalRowNum = obstacleTiles.length - 1;
        if (obstacleTiles[finalRowNum][currPlayerCol] == 0) {
            moveForward();
            score += 2;
            return true;
        } else
            return false;
    }

    /**
     * Updates the location of obstacles when the player moves forward
     */
    private void moveForward() {
        obstacleTiles[4] = obstacleTiles[3].clone();
        obstacleTiles[3] = obstacleTiles[2].clone();
        obstacleTiles[2] = obstacleTiles[1].clone();
        obstacleTiles[1] = obstacleTiles[0].clone();

        updateObstacles(0);
    }

    /**
     * Updates the location of obstacle tiles
     *
     * @param rowNum the index of the row
     */
    public void updateObstacles(int rowNum) {
        int obstacleLocation1 = (int) (Math.random() * 4);
        int obstacleLocation2 = (int) (Math.random() * 4);

        for (int i = 0; i < obstacleTiles[rowNum].length; i++) {
            if (i == obstacleLocation1)
                obstacleTiles[rowNum][obstacleLocation1] = 1;

            else if (i == obstacleLocation2)
                obstacleTiles[rowNum][obstacleLocation2] = 1;

            else
                obstacleTiles[rowNum][i] = 0;

        }
    }

    /**
     * Method to adjust player position based on swipe direction
     */
    public void checkSwipe(float velX) {
        if (velX < 0 && currPlayerCol != 0)
            currPlayerCol -= 1;

        else if (velX > 0 && currPlayerCol != 2)
            currPlayerCol += 1;
    }

    /**
     * Getter method that returns the score
     *
     * @return the score
     */
    @Override
    public int getScore() {
        return this.score;
    }
}
