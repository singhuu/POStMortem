package com.example.postmortem.LevelSystems;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.postmortem.R;

public class PickUpLevelActivity extends AppCompatActivity {
    PickUpLevel level;
    Button[] selectButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_level);

        level = new PickUpLevel(1); //TODO temp value to be passed in as extra

        selectButtons = getButtons();
        assignButtonVals(selectButtons);

        updateSearchPrompt();

    }

    private void updateSearchPrompt(){
        TextView searchPrompt = findViewById(R.id.searchPrompt);
        String searchStr = "Find the " + level.getTarget() + "!";
        searchPrompt.setText(searchStr);
    }

    //TODO need to make sure at least one button has a target
    private void assignButtonVals(Button[] selectButtons) {
        String[] selectables = level.getSelectables();
        for(int i = 0; i < selectButtons.length; i++){
            String newButtonVal = selectables[i];
            selectButtons[i].setText(newButtonVal);
        }
    }

    private Button[] getButtons(){
        Button[] buttons = new Button[6];

        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);
        buttons[4] = findViewById(R.id.button5);
        buttons[5] = findViewById(R.id.button6);

        return buttons;
    }

    public void clickHandler(View target){
        Button clickedButton = (Button) target;
        String clickedVal = (String) clickedButton.getText();

        boolean correctSelect = level.checkSelectVal(clickedVal);

        if(correctSelect){
            level.numCorrect += 1;
            assignButtonVals(selectButtons);
            updateSearchPrompt();
        }else{
            //TODO display x on screen
        }

    }

}
