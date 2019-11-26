package com.example.postmortem.LevelSystems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.postmortem.R;
import com.example.postmortem.UserLoader;

public class SwipeLevelActivity extends LevelActivity{
    SwipeLevel level;

    TextView scoreText;
    TextView timerText;

    TextView[][] obstacleTiles;
    TextView[] playerTiles;

    CountDownTimer tileCheckTimer;

    GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_level);

        mDetector = new GestureDetectorCompat(this, new SwipeLevelGestureListener());

        //gameManager = getIntent().getParcelableExtra("GAME_MANAGER");

        getIntent().getIntExtra("DIFFICULTY", difficulty);
        level = new SwipeLevel(difficulty);

        scoreText = findViewById(R.id.score);
        scoreText.setText("0");

        timeLeft = 30 - (10 * difficulty);
        timerText = findViewById(R.id.timer);
        timerText.setText(timeLeft + 1 + "");

        obstacleTiles = getObstacleTiles();
        updateObstacleTiles();

        playerTiles = getPlayerTiles();

        startTileCheckTimer(timeLeft);
        startTimer(timeLeft);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //Timer for checking if lane is open immediately after swipe advance
    private void startTileCheckTimer(int cTimeInSeconds) {
        tileCheckTimer = new CountDownTimer(cTimeInSeconds * 1000, 400) {
            @Override
            public void onTick(long l) {
                boolean checkResult = level.checkOpenLane();
                if(checkResult){
                    updateObstacleTiles();
                    scoreText.setText(Integer.toString(level.getScore()));
                }
            }

            @Override
            public void onFinish() {
                //Do nothing
            }
        };

        tileCheckTimer.start();
    }

    private TextView[][] getObstacleTiles() {
        TextView[][] obstacleTiles = new TextView[3][3];

        obstacleTiles[0][0] = findViewById(R.id.obstacleTile1);
        obstacleTiles[0][1] = findViewById(R.id.obstacleTile2);
        obstacleTiles[0][2] = findViewById(R.id.obstacleTile3);

        obstacleTiles[1][0] = findViewById(R.id.obstacleTile4);
        obstacleTiles[1][1] = findViewById(R.id.obstacleTile5);
        obstacleTiles[1][2] = findViewById(R.id.obstacleTile6);

        obstacleTiles[2][0] = findViewById(R.id.obstacleTile7);
        obstacleTiles[2][1] = findViewById(R.id.obstacleTile8);
        obstacleTiles[2][2] = findViewById(R.id.obstacleTile9);

        for(int i = 0; i <  obstacleTiles.length; i++)
            level.updateObstacles(i);

        return  obstacleTiles;
    }

    private TextView[] getPlayerTiles(){
        playerTiles = new TextView[3];

        playerTiles[0] = findViewById(R.id.playerTile1);
        playerTiles[1] = findViewById(R.id.playerTile2);
        playerTiles[2] = findViewById(R.id.playerTile3);

        playerTiles[1].setText("P");

        return playerTiles;
    }

    private void updateObstacleTiles(){
        for(int i = 0; i < obstacleTiles.length; i++){
            for(int j = 0; j < obstacleTiles[i].length; j++){
                String routeSpotValue = Integer.toString(level.obstacleTiles[i][j]);
                obstacleTiles[i][j].setText(routeSpotValue);
            }
        }
    }

    private void updatePlayerTiles(){
        for(int i = 0; i < 3; i++){
            if(level.currPlayerCol == i)
                playerTiles[i].setText("P");
            else
                playerTiles[i].setText("");
        }
    }

    @Override
    public void countTickHandler() {
        timerText.setText(timeLeft + 1 + "");
    }

    @Override
    public void countFinishHandler() {
        gameManager.totalScore += level.getScore();
        gameManager.play(this);
    }

    @Override
    public void saveScore() {
        UserLoader.getUser(curr_username).setScore(level.getScore(), LevelType.PICKUP);
    }

    //Class to register swipe events
    public class SwipeLevelGestureListener extends GestureDetector.SimpleOnGestureListener {

        //Here because all gestures start with an onDown()
        @Override
        public boolean onDown(MotionEvent event){
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            level.checkSwipe(velocityX);
            updatePlayerTiles();
            return true;
        }

    }
}
