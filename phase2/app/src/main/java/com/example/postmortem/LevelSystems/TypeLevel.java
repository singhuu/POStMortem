package com.example.postmortem.LevelSystems;


import com.example.postmortem.MainActivity;
import com.example.postmortem.Question;
import com.example.postmortem.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class TypeLevel extends Level {
  /**
   * Question object that stores the current Question
   */
  private Question currentQuestion;
  /**
   * Integer variable that stores the id of the current question
   */
  private int currentQuestionNum = 0;
  /**
   * ArrayList that stores all the questions
   */
  private ArrayList<Question> allQuestions;

  /**
   * Initiates the TypeLevel and sets the difficulty using the Level constructor
   */
  public TypeLevel(int difficulty) {
    super(difficulty);
  }

  /**
   * Getter method that the returns score
   *
   * @return score variable
   */
  public int getScore() {
    return this.score;
  }

  /**
   * Setter method that changes the level score value
   * @param s score that will be changed to
   */
  public void setScore(int s) {
    this.score = s;
  }
  /**
   * using data from a text file pull the questions and answers and create those questions
   */
  public void createQuestions() {
    //arraylist holding all questions to cycle through
    ArrayList<Question> ques = new ArrayList<>();

    ArrayList<String> formatQues = readFile();

    //shuffling questions so the user doesn't always get the same questions
    Collections.shuffle(formatQues);
    //cycles through questions in formatQues when they are in string form and adds them to database of questions

    for (int i = 0; i < formatQues.size(); i++) {
      String[] l = formatQues.get(i).split(",", -1);
      ArrayList<String> e = new ArrayList<>();
      e.add(l[1]);
      e.add(l[2]);
      e.add(l[3]);
      e.add(l[4]);

      Question o = new Question(l[0], e);
      o.setCorrectAnswer(l[1]);
      ques.add(o);
    }
    this.allQuestions = ques;
    this.currentQuestion = ques.get(currentQuestionNum);
  }

  /** helper method which reads the file data and formats it
   *
   * @return a String ArrayList containing the questions from the file */
  private ArrayList<String> readFile() {
    ArrayList<String> formatQues = new ArrayList<>();

    String s;
    InputStream is;
    BufferedReader reader;

    is = MainActivity.getMContext().getResources().openRawResource(R.raw.all_questions);
    reader = new BufferedReader(new InputStreamReader(is));
    try {
      //cycles all lines of text file if possible
      while ((s = reader.readLine()) != null) {
        formatQues.add(s);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return formatQues;

  }


  /**
   * Functions checks if the answer is true
   *
   * @param answer Answer selected by the user
   * @return true if the answer is true and false if not
   */
  public boolean checkAnswer(String answer) {
    return answer.equals(getCurrentQuestion().getCorrectAnswer());
  }

  /**
   * Getter method that returns the current Question
   *
   * @return currentQuestion Question object that returns the current Question
   */
  public Question getCurrentQuestion() {
    return this.currentQuestion;
  }

  /**
   * Goes to the next Question
   */
  public void nextQuestion() {
    if (currentQuestionNum == (allQuestions.size() - 1)) {
      currentQuestionNum = 0;
    } else {
      currentQuestionNum += 1;
    }
    this.currentQuestion = allQuestions.get(currentQuestionNum);
  }

  /**
   * Getter method that gets the question number
   *
   * @return the current question that we are on
   */
  public int getCurrentQuestionNum() {
    return currentQuestionNum;
  }

  /**
   * Getter method that gets your difficulty
   *
   * @param difficulty integer variable that stores difficulty
   */
  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }
}
