package com.example.postmortem.LevelSystems;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.postmortem.MainActivity;
import com.example.postmortem.R;
import com.example.postmortem.SoundManager;

public class PickUpLevelActivity extends LevelActivity {

  /**
   * PickUpLevel Object that stores data of level
   */
  PickUpLevel level;
  /**
   * List that stores the buttons
   */
  Button[] selectButtons;

  /**
   * ImageView that displays the wrongChoiceX
   */
  ImageView wrongChoiceX;
  private SoundManager sm = new SoundManager(MainActivity.get_m_Context());

  @SuppressLint("DefaultLocale")
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
    timerText.setText(String.format("%d", timeLeft + 1));

    wrongChoiceX = findViewById(R.id.wrongChoiceX);

    selectButtons = getButtons();
    assignButtonVals(selectButtons);

    updateSearchPrompt();

    startTimer(timeLeft);

  }

  /**
   * Updates the name of the object to be found out
   */
  private void updateSearchPrompt() {
    TextView searchPrompt = findViewById(R.id.searchPrompt);
    String searchStr = "Find the " + level.getTarget() + "!";
    searchPrompt.setText(searchStr);
  }

  /**
   * Assigns the button to various choices
   *
   * @param selectButtons An array of possible buttons to select from
   */
  private void assignButtonVals(Button[] selectButtons) {
    String[] selectables = level.getSelectables();
    for (int i = 0; i < selectButtons.length; i++) {
      selectButtons[i].setContentDescription(selectables[i]);
      assignButtonImage(selectButtons[i], selectables[i]);
    }
  }

  /**
   * Assigns the image to the choices
   *
   * @param button    Button object
   * @param buttonVal Value of the button
   */
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

  /**
   * Assigns ids to various buttons
   *
   * @return list of buttons
   */
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

  /**
   * Listens and handles for a click
   *
   * @param target The view for the X to appear on
   */
  @SuppressLint("DefaultLocale")
  public void clickHandler(View target) {
    if (level.wrongChoiceCountdown == 0) {
      Button clickedButton = (Button) target;
      String clickedVal = (String) clickedButton.getContentDescription();

      boolean correctSelect = level.checkSelectVal(clickedVal);

      if (correctSelect) {
        level.increaseScore();
        scoreText.setText(String.format("%d", level.getScore()));
        assignButtonVals(selectButtons);
        updateSearchPrompt();
      } else {
        sm.playBooEffect();
        level.wrongChoiceCountdown = PickUpLevel.WRONG_CHOICE_TIME;
        wrongChoiceX.setVisibility(View.VISIBLE);
      }
    }
  }

  /**
   * Handles the count tick
   */
  @SuppressLint("DefaultLocale")
  @Override
  public void countTickHandler() {
    timerText.setText(String.format("%d", timeLeft + 1));

    if (level.wrongChoiceCountdown != 0)
      level.wrongChoiceCountdown -= 1;
    else
      wrongChoiceX.setVisibility(View.INVISIBLE);
  }

  /**
   * updates total score
   */
  @Override
  public void countFinishHandler() {
    gameManager.totalScore += level.getScore();
    gameManager.play(this);

  }

  /**
   * Saves the score
   */
  @Override
  public void saveScore() {
    gameManager.getActiveUser().setScore(level.getScore(), LevelType.PICKUP);
  }

  public void cheatClickHandler(View view) {
    level.score += 100;
    scoreText.setText(String.format("%d", level.getScore()));
  }

}

