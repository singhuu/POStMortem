package com.example.postmortem;
import java.util.*;

public class Question {

    /** This class stores the question information for each question in the typeLevel game.
     * It also randomizes the order of the questions.*/
    private String question;
    private ArrayList<String> answers;
    private String correctAnswer;

    public Question(String question, ArrayList<String> answers) {
        this.question = question;
        this.answers = answers;

    }

    /** randomizes the order in which the answers appear when starting the level */
    public void randomizeAnswerOrder() {
        Collections.shuffle(answers);
    }

    public String getQuestion() {
        return this.question;
    }

    public ArrayList<String> getAnswers() {
        return this.answers;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;

    }

}
