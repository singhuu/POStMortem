package com.example.postmortem.LevelSystems;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.postmortem.R;



public class TypeLevelActivity extends LevelActivity {


  Button[] selectButtons;
  private boolean clickedAnswer = false;
  TextView timerText;
  TypeLevel level = new TypeLevel(difficulty);
  /** Chooses how to display this level when it is created in the app */
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

    TextView textView = findViewById(R.id.score);
    textView.setText(level.getScore() + "");

  }

  private void assignTimerLength() {
    //need to change based off of difficulty
    timeLeft = 30;
    if(difficulty == 1) {
      timeLeft = 30;
    }
    else if(difficulty == 2) {
      timeLeft = 25;
    }
    else if(difficulty == 3) {
      timeLeft = 20;
    }

    timerText = findViewById(R.id.timer);
    timerText.setText(timeLeft + 1 + "");
    startTimer(timeLeft);
  }

  private Button[] getButtons() {
    Button[] buttons = new Button[5];

    buttons[0] = findViewById(R.id.button_answer1);
    buttons[1] = findViewById(R.id.button_answer2);
    buttons[2] = findViewById(R.id.button_answer3);
    buttons[3] = findViewById(R.id.button_answer4);
    buttons[4] = findViewById(R.id.button_next);

    return buttons;
  }

  private void assignButtonVals(Button[] selectButtons) {

    selectButtons[0].setText(level.getCurrentQuestion().getAnswers().get(0));
    selectButtons[1].setText(level.getCurrentQuestion().getAnswers().get(1));
    selectButtons[2].setText(level.getCurrentQuestion().getAnswers().get(2));
    selectButtons[3].setText(level.getCurrentQuestion().getAnswers().get(3));
    String a = "next";
    selectButtons[4].setText(a);
  }

  public void clickHandler(View target) {
    Button clickedButton = (Button) target;
    String clickedVal = (String) clickedButton.getText();

    /** if the button is one of the answers, show the color to indicate which button is correct */
   if (clickedButton != selectButtons[4]) {
     clickedAnswer = true;
     if(level.checkAnswer(clickedVal)) {
       level.score = level.score + 25;
       TextView textView = findViewById(R.id.score);
       textView.setText(level.getScore() + "");
     }
     for (int i = 0; i < selectButtons.length - 1; i++) {
       //button shouldn't be clickable anymore
       selectButtons[i].setEnabled(false);
       if (selectButtons[i].getText() != level.getCurrentQuestion().getCorrectAnswer()) {
         selectButtons[i].setBackgroundColor(getResources().getColor(R.color.supremeRed));
       } else {
         selectButtons[i].setBackgroundColor(getResources().getColor(R.color.green));
       }
     }
   }
    /** goes to the next question */
   else {
      if(clickedAnswer) {
        level.nextQuestion();
        assignButtonVals(selectButtons);
        TextView title = findViewById(R.id.textView4);
        TextView question_text = findViewById(R.id.textView5);

        String a = "Question " + level.getCurrentQuestionNum();
        title.setText(a);
        String text_1 = level.getCurrentQuestion().getQuestion();
        question_text.setText(text_1);
        for(int i =0; i < selectButtons.length - 1; i++) {
          selectButtons[i].setEnabled(true);
          selectButtons[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        clickedAnswer = false;
      }
   }
  }


/** have to implement*/
  public void countTickHandler() {
    timerText.setText(timeLeft + 1 + "");
  }

  public void countFinishHandler() {
    gameManager.play(this);
  }
}
