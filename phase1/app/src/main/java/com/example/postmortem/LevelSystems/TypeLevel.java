package com.example.postmortem.LevelSystems;

import com.example.postmortem.Question;

import java.util.ArrayList;

public class TypeLevel extends Level {
    private String timerString = "00:30";

    private Question currentQuestion;

    public int score = 0;

    private ArrayList<Question> allQuestions;

    /** Initiates the TypeLevel and sets the difficulty using the Level constructor*/
    public TypeLevel(int difficulty) {
        super(difficulty);
    }



    /** not implemented yet */
    public int getScore() {
        return score;
    }

    /** using data from a text file pull the questions and answers and create those questions
     * only pull 4 questions randomly from the file  */
    public void createQuestions() {
        /** currently testing with one question, will figure out how to implement with more using
         * a text file soon
         */
        ArrayList<String> a = new ArrayList<>();
        a.add("stack overflow");
        a.add("david");
        a.add("francois");
        a.add("nancy");
        Question q = new Question("Who is the best cs prof?", a);
        q.setCorrectAnswer("stack overflow");


        ArrayList<Question> ques = new ArrayList<>();
        ques.add(q);
        this.currentQuestion = ques.get(0);
        ques.get(0).setCorrectAnswer("stack overflow");
        this.allQuestions = ques;
    }

    public boolean checkAnswer(String answer) {
        if (answer.equals(getCurrentQuestion().getCorrectAnswer())) {
            return true;
        }
        return false;
    }

    public Question getCurrentQuestion() {
        return this.currentQuestion;
    }
}
