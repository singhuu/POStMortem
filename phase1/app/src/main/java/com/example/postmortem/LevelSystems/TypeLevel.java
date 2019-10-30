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
    private Question currentQuestion;
    private int currentQuestionNum = 0;

    public int score = 0;

    private ArrayList<Question> allQuestions;

    //pulls from text file
    //android.content.res.Resources res = getBaseContext().getResources();

    /** Initiates the TypeLevel and sets the difficulty using the Level constructor*/
    public TypeLevel(int difficulty) {
        super(difficulty);
    }


    public int getScore() {
        return score;
    }

    /** using data from a text file pull the questions and answers and create those questions
     * only pull 4 questions randomly from the file  */
    public void createQuestions() {
        /** pulls questions from text file and creates the question objects, then stores them in the class
         */
        //arraylist holding all questions to cycle through
        ArrayList<Question> ques = new ArrayList<>();

        ArrayList<String> formatQues = new ArrayList<>();

        String s;
        InputStream is;
        BufferedReader reader;

        is = MainActivity.getmContext().getResources().openRawResource(R.raw.all_questions);
        reader = new BufferedReader(new InputStreamReader(is));
        if (is != null) {
            try {
                //cycles all lines of text file if possible
                while((s = reader.readLine()) != null) {
                    formatQues.add(s);
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        //reads the text file

        //shuffling questions so the user doesn't always get the same questions
        Collections.shuffle(formatQues);
        //cycles through questions in formatques when they are in string form and adds them to database of questions

        for(int i =0; i < formatQues.size(); i ++) {
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

    public boolean checkAnswer(String answer) {
        if (answer.equals(getCurrentQuestion().getCorrectAnswer())) {
            return true;
        }
        return false;
    }

    public Question getCurrentQuestion() {
        return this.currentQuestion;
    }

    public void nextQuestion() {
        if(currentQuestionNum == (allQuestions.size() - 1)){
            currentQuestionNum = 0;
        }
        else {
            currentQuestionNum+=1;
        }
        this.currentQuestion = allQuestions.get(currentQuestionNum);
    }

    public int getCurrentQuestionNum() {
        return currentQuestionNum;
    }
}
