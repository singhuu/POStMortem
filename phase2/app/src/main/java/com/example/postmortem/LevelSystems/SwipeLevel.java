package com.example.postmortem.LevelSystems;

public class SwipeLevel extends Level {
    int[][] obstacleTiles;
    int currPlayerCol;

    private int rowsPassed = 0;

    public SwipeLevel(int difficulty) {
        super(difficulty);

        obstacleTiles = new int[3][3];
        currPlayerCol = 1;
    }

    public boolean checkOpenLane(){
        if(obstacleTiles[2][currPlayerCol] == 0){
            moveForward();
            rowsPassed++;
            return true;
        }else
            return false;
    }

    private void moveForward(){
        obstacleTiles[2] = obstacleTiles[1].clone();
        obstacleTiles[1] = obstacleTiles[0].clone();

        updateObstacles(0);
    }

    public void updateObstacles(int rowNum){
        int obstacleLocation1 = (int) (Math.random() * 4);
        int obstacleLocation2 = (int) (Math.random() * 4);

        for(int i = 0; i < obstacleTiles[rowNum].length; i++) {
            if(i == obstacleLocation1)
                obstacleTiles[rowNum][obstacleLocation1] = 1;

            else if(i == obstacleLocation2)
                obstacleTiles[rowNum][obstacleLocation2] = 1;

            else
                obstacleTiles[rowNum][i] = 0;

        }
    }

    //Method to adjust player position based on swipe direction
    public void checkSwipe(float velX){
        if(velX < 0 && currPlayerCol != 0)
            currPlayerCol -= 1;

        else if(velX > 0 && currPlayerCol != 2)
            currPlayerCol += 1;
    }

    @Override
    public int getScore() {
        return this.rowsPassed * 10;
    }
}
