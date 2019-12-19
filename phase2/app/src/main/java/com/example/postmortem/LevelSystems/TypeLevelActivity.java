package com.example.postmortem.LevelSystems;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.postmortem.MainActivity;
import com.example.postmortem.R;
import com.example.postmortem.SoundManager;

import java.util.Locale;


public class TypeLevelActivity extends LevelActivity {
  /**
   * List that stores the buttons
   */
  private Button[] selectButtons;

  /**
   * object of TypeLevel
   */
  private TypeLevel level = new TypeLevel(difficulty);
  private SoundManager sm = new SoundManager(MainActivity.getMContext());
  /**
   * Checks if answer is clicked
   */
  private boolean clickedAnswer = false;

  /**
   * Chooses how to display this level when it is created in the app
   */
  @SuppressLint("DefaultLocale")
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.type_level);

    gameManager = getIntent().getParcelableExtra("GAME_MANAGER");
    getIntent().getIntExtra("DIFFICULTY", difficulty);

    level.setDifficulty(difficulty);

    level.createQuestions();

    selectButtons = getButtons();
    assignButtonVals(selectButtons);

    TextView title = findViewById(R.id.textView4);
    TextView question_text = findViewById(R.id.textView5);

    String a = "Question " + (level.getCurrentQuestionNum() + 1);
    title.setText(a);
    String text_1 = level.getCurrentQuestion().getQuestion();
    question_text.setText(text_1);

    assignTimerLength();

    scoreText = findViewById(R.id.score);
    scoreText.setText(String.format("%d", level.getScore()));

  }

  /**
   * Assigns the timer length
   */
  @SuppressLint("DefaultLocale")
  private void assignTimerLength() {
    //need to change based off of difficulty
    timeLeft = 30 - (difficulty * 10);
    timerText = findViewById(R.id.timer);
    timerText.setText(String.format("%d", timeLeft + 1));
    startTimer(timeLeft);
  }

  /**
   * Assigns ids to various buttons
   *
   * @return list of buttons
   */
  private Button[] getButtons() {
    Button[] buttons = new Button[5];

    buttons[0] = findViewById(R.id.button_answer1);
    buttons[1] = findViewById(R.id.button_answer2);
    buttons[2] = findViewById(R.id.button_answer3);
    buttons[3] = findViewById(R.id.button_answer4);
    buttons[4] = findViewById(R.id.button_next);

    return buttons;
  }

  /**
   * Assigns the button to various choices
   *
   * @param selectButtons list of button choices
   */
  private void assignButtonVals(Button[] selectButtons) {

    selectButtons[0].setText(level.getCurrentQuestion().getAnswers().get(0));
    selectButtons[1].setText(level.getCurrentQuestion().getAnswers().get(1));
    selectButtons[2].setText(level.getCurrentQuestion().getAnswers().get(2));
    selectButtons[3].setText(level.getCurrentQuestion().getAnswers().get(3));
    String a = "next";
    selectButtons[4].setText(a);
  }

  /**
   * Listens and handles for a click
   */
  @SuppressLint("DefaultLocale")
  public void clickHandler(View target) {
    Button clickedButton = (Button) target;
    String clickedVal = (String) clickedButton.getText();
    // if the button is one of the answers, show the color to indicate which button is correct
    if (clickedButton != selectButtons[4]) {
      clickedAnswer = true;
      if (level.checkAnswer(clickedVal)) {
        level.score += 25;

        scoreText.setText(String.format("%d", level.getScore()));
        sm.playWowEffect();
      } else {
        sm.playBooEffect();
      }
      changeButtonColor();
    }
    // goes to the next question
    else {
      if (clickedAnswer) {
        goToNextQuestion();

        clickedAnswer = false;
      }
    }
  }


  /**
   * helper method that moves to the next question in the level
   */
  private void goToNextQuestion() {
    level.nextQuestion();
    assignButtonVals(selectButtons);
    TextView title = findViewById(R.id.textView4);
    TextView question_text = findViewById(R.id.textView5);

    String a = "Question " + (level.getCurrentQuestionNum() + 1);
    title.setText(a);
    String text_1 = level.getCurrentQuestion().getQuestion();
    question_text.setText(text_1);
    for (int i = 0; i < selectButtons.length - 1; i++) {
      selectButtons[i].setEnabled(true);
      selectButtons[i].setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
    }
  }

  /**
   * helper method that changes the colors of the buttons correctly after being pressed
   */
  private void changeButtonColor() {
    for (int i = 0; i < selectButtons.length - 1; i++) {
      //button shouldn't be clickable anymore
      selectButtons[i].setEnabled(false);
      if (selectButtons[i].getText() != level.getCurrentQuestion().getCorrectAnswer()) {
        selectButtons[i].setBackgroundColor(ContextCompat.getColor(this, R.color.supremeRed));
      } else {
        selectButtons[i].setBackgroundColor(ContextCompat.getColor(this, R.color.green));
      }
    }

  }


  /**
   * Handles the count tick
   */
  @SuppressLint("DefaultLocale")
  public void countTickHandler() {
    timerText.setText(String.format("%d", timeLeft + 1));
  }

  /**
   * updates total score
   */
  public void countFinishHandler() {
    gameManager.totalScore += level.getScore();
    gameManager.play(this);
  }

  /**
   * Saves the score
   */
  @Override
  public void saveScore() {
    gameManager.getActiveUser().setScore(level.getScore(), LevelType.TYPE);
  }

  public void cheatClickHandler(View view) {
    level.score += 100;
    scoreText.setText(String.format(Locale.CANADA, "%d", level.getScore()));
  }
}
