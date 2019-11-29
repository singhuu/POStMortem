package com.example.postmortem;

import java.util.ArrayList;
import java.util.Collections;

public class Question {

  /**
   * This class stores the question information for each question in the TypeLevel game.
   * It also randomizes the order of the questions.
   */
  private String question;
  private ArrayList<String> answers;
  private String correctAnswer;

  /**
   * Constructor method that stores initalizes variables
   *
   * @param question String that stores the question
   * @param answers  Stores the possible answers
   */
  public Question(String question, ArrayList<String> answers) {
    this.question = question;
    this.answers = answers;
    this.correctAnswer = answers.get(0);
    randomizeAnswerOrder();

  }

  /**
   * randomizes the order in which the answers appear when starting the level
   */
  private void randomizeAnswerOrder() {
    Collections.shuffle(answers);
  }

  /**
   * Getter function to get the question
   *
   * @return String variable question
   */
  public String getQuestion() {
    return this.question;
  }

  /**
   * Getter function that returns possible answers
   *
   * @return answer ArrayList
   */
  public ArrayList<String> getAnswers() {
    return this.answers;
  }

  /**
   * Getter function that returns the correct answer
   *
   * @return correctAnswer String
   */
  public String getCorrectAnswer() {
    return this.correctAnswer;
  }

  /**
   * Setter function that sets the correct answer
   *
   * @param correctAnswer the correct Answer
   */
  public void setCorrectAnswer(String correctAnswer) {
    this.correctAnswer = correctAnswer;

  }

}
