package com.example.postmortem.LevelSystems;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.postmortem.R;
import com.example.postmortem.UserLoader;

import java.util.ArrayList;

public class SwipeLevelActivity extends LevelActivity {
    SwipeLevel level;

    TextView scoreText;
    TextView timerText;

    Button[][] routeButtons;
    Button[] playerButtons;

    CountDownTimer tileCheckTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_level);

        //gameManager = getIntent().getParcelableExtra("GAME_MANAGER");

        getIntent().getIntExtra("DIFFICULTY", difficulty);
        level = new SwipeLevel(difficulty);

        scoreText = findViewById(R.id.score);
        scoreText.setText("0");

        timeLeft = 30 - (10 * difficulty);
        timerText = findViewById(R.id.timer);
        timerText.setText(timeLeft + 1 + "");

        routeButtons = getRouteButtons();
        updateRouteButtons();

        playerButtons = getPlayerButtons();

        startTileCheckTimer(timeLeft);
        startTimer(timeLeft);
    }

    //Timer for checking if lane is open immediately after swipe advance
    private void startTileCheckTimer(int cTimeInSeconds) {
        tileCheckTimer = new CountDownTimer(cTimeInSeconds * 1000, 400) {
            @Override
            public void onTick(long l) {
                boolean checkResult = level.checkOpenLane();
                if(checkResult){
                    updateRouteButtons();
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

    private Button[][] getRouteButtons() {
        Button[][] buttons = new Button[3][3];

        buttons[0][0] = findViewById(R.id.button1);
        buttons[0][1] = findViewById(R.id.button2);
        buttons[0][2] = findViewById(R.id.button3);

        buttons[1][0] = findViewById(R.id.button4);
        buttons[1][1] = findViewById(R.id.button5);
        buttons[1][2] = findViewById(R.id.button6);

        buttons[2][0] = findViewById(R.id.button7);
        buttons[2][1] = findViewById(R.id.button8);
        buttons[2][2] = findViewById(R.id.button9);

        for(int i = 0; i < buttons.length; i++)
            level.updateObstacles(i);

        return buttons;
    }

    private Button[] getPlayerButtons(){
        playerButtons = new Button[3];

        playerButtons[0] = findViewById(R.id.playerButton1);
        playerButtons[1] = findViewById(R.id.playerButton2);
        playerButtons[2] = findViewById(R.id.playerButton3);

        playerButtons[1].setText("P");

        return playerButtons;
    }

    private void updateRouteButtons(){
        for(int i = 0; i < routeButtons.length; i++){
            for(int j = 0; j < routeButtons[i].length; j++){
                String routeSpotValue = Integer.toString(level.obstacleTiles[i][j]);
                routeButtons[i][j].setText(routeSpotValue);
            }
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
}
