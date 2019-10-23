package com.example.postmortem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PickUpLevelActivity extends AppCompatActivity {
    PickUpLevel level = new PickUpLevel(-1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_level);

        level.difficulty = 1; //TODO temp value to be passed in as extra

        Button[] selectButtons = getButtons();
        assignButtonVals(selectButtons);

        TextView searchPrompt = findViewById(R.id.searchPrompt);
        String searchStr = level.createSearchString();
        searchPrompt.setText(searchStr);

    }

    private void assignButtonVals(Button[] selectButtons) {
        String[] possibleButtonVals = PickUpLevel.possibleObjects;
        for(Button button : selectButtons){
            button.setText(possibleButtonVals[(int)(Math.random() * possibleButtonVals.length)]);
        }
    }

    private Button[] getButtons(){
        Button[] buttons = new Button[5];

        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);
        buttons[4] = findViewById(R.id.button5);

        return buttons;
    }

    public void clickHandler(View target){
        Button clickedButton = (Button) target;
        String clickedVal = (String) clickedButton.getText();

        boolean correctSelect = level.checkSelectVal(clickedVal);

        if(correctSelect){
            level.numCorrect += 1;
            //TODO change image
        }else{
            //TODO display x on screen
        }

    }

}
