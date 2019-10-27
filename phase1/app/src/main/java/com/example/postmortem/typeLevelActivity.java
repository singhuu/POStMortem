package com.example.postmortem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class typeLevelActivity extends AppCompatActivity {

  typeLevel level = new typeLevel(1);
  Button[] selectButtons;
  /** Chooses how to display this level when it is created in the app */
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.type_level);

    level.createQuestions();

    selectButtons = getButtons();
    assignButtonVals(selectButtons);

    TextView title = findViewById(R.id.textView4);
    TextView question_text = findViewById(R.id.textView5);

    String a = "question 1/1";
    title.setText(a);
    String text_1 = level.getCurrentQuestion().getQuestion();
    question_text.setText(text_1);
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
    System.out.println(clickedVal);

    /** if the button is one of the answers, show the color to indicate which button is correct */
   if (clickedButton != selectButtons[4]) {
     if(level.checkAnswer(clickedVal)) {
       level.score = level.score + 1;
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

    /** have to implement the next button */
   else {

   }
  }
}
