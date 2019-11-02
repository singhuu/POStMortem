package com.example.postmortem.LevelSystems;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.postmortem.R;
import com.example.postmortem.UserLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PickUpLevelActivity extends LevelActivity {
    PickUpLevel level;
    Button[] selectButtons;
    TextView scoreText;
    TextView timerText;
    ImageView wrongChoiceX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_level);

        gameManager = getIntent().getParcelableExtra("GAME_MANAGER");

        getIntent().getIntExtra("DIFFICULTY", difficulty);
        level = new PickUpLevel(difficulty);

        scoreText = findViewById(R.id.score);
        scoreText.setText("0");

        timeLeft = 30 - (10 * difficulty);
        timerText = findViewById(R.id.timer);
        timerText.setText(timeLeft + 1 + "");

        wrongChoiceX = findViewById(R.id.wrongChoiceX);

        selectButtons = getButtons();
        assignButtonVals(selectButtons);

        updateSearchPrompt();

        startTimer(timeLeft);

    }

    private void updateSearchPrompt() {
        TextView searchPrompt = findViewById(R.id.searchPrompt);
        String searchStr = "Find the " + level.getTarget() + "!";
        searchPrompt.setText(searchStr);
    }

    private void assignButtonVals(Button[] selectButtons) {
        String[] selectables = level.getSelectables();
        for (int i = 0; i < selectButtons.length; i++) {
            selectButtons[i].setContentDescription(selectables[i]);
            assignButtonImage(selectButtons[i], selectables[i]);
        }
    }

    private void assignButtonImage(Button button, String buttonVal) {
        switch (buttonVal) {
            case "Deodorant":
                button.setBackgroundResource(R.mipmap.deodorant);
                break;

            case "Laptop":
                button.setBackgroundResource(R.mipmap.laptop_open);
                break;

            case "Phone":
                button.setBackgroundResource(R.mipmap.phone);
                break;

            case "Pencil":
                button.setBackgroundResource(R.mipmap.pencil);
                break;

            case "TCard":
                button.setBackgroundResource(R.mipmap.tcard);
                break;

            case "Wallet":
                button.setBackgroundResource(R.mipmap.wallet);
                break;

            case "Notebook":
                button.setBackgroundResource(R.mipmap.notebook);
                break;

            case "Gym Bag":
                button.setBackgroundResource(R.mipmap.gymbag);
                break;

            case "Glasses":
                button.setBackgroundResource(R.mipmap.glasses);
                break;

            case "Snack":
                button.setBackgroundResource(R.mipmap.tasty_snack);
                break;

            default:
                button.setBackgroundResource(R.drawable.ic_launcher_foreground);
        }
    }

    private Button[] getButtons() {
        Button[] buttons = new Button[6];

        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);
        buttons[4] = findViewById(R.id.button5);
        buttons[5] = findViewById(R.id.button6);

        return buttons;
    }

    public void clickHandler(View target) {
        if (level.wrongChoiceCountdown == 0) {
            Button clickedButton = (Button) target;
            String clickedVal = (String) clickedButton.getContentDescription();

            boolean correctSelect = level.checkSelectVal(clickedVal);

            if (correctSelect) {
                level.increaseScore();
                scoreText.setText(level.getScore() + "");
                assignButtonVals(selectButtons);
                updateSearchPrompt();
            } else {
                level.wrongChoiceCountdown = PickUpLevel.WRONG_CHOICE_TIME;
                wrongChoiceX.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void countTickHandler() { //TODO image stays when continuously clicking
        timerText.setText(timeLeft + 1 + "");

        if (level.wrongChoiceCountdown != 0)
            level.wrongChoiceCountdown -= 1;
        else
            wrongChoiceX.setVisibility(View.INVISIBLE);
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

