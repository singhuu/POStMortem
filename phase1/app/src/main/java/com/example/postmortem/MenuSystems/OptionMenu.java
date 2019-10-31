package com.example.postmortem.MenuSystems;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.GameManager;

import java.util.List;

class OptionMenu extends GameMenu {

    private final int ADS_BOX = 3;
    private final int DIFFICULTY_SELECTOR = 2;
    private final int LEVEL_CHOICE = 1;

    private final String EASY_DIFFICULTY = "minor";
    private final String MEDIUM_DIFFICULTY = "major";
    private final String HARD_DIFFICULTY =  "specialist";
    private final String[] DIFFICULTIES = {EASY_DIFFICULTY, MEDIUM_DIFFICULTY, HARD_DIFFICULTY};

    private GameManager manager;

    OptionMenu(String title){
        super(title);
    }

    @Override
    public List<View> buildMenuItems(final AppCompatActivity context){

        getGameManager(context);
        createTextViews(context);
        createSpinner(context);
        createButtons(context);

        return items;
    }

    private void getGameManager(AppCompatActivity context){
        Intent intent = context.getIntent();
        manager = intent.getParcelableExtra(GameManager.INTENT_NAME);
    }

    private void createTextViews(AppCompatActivity context) {
        //create and set the properties of the title
        TextView titleText = new TextView(context);
        titleText.setText(title);
        titleText.setTextSize(32);
        titleText.setY(64);
        titleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        titleText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
                LayoutParams.WRAP_CONTENT));
        setColours(titleText);
        items.add(titleText);
        
        //create and set the properties of the levels field
        EditText levelsField = new EditText(context);
        levelsField.setY(680);
        levelsField.setHint("How many levels to play, min 3");
        levelsField.setTextSize(18);
        levelsField.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        levelsField.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        levelsField.setInputType(InputType.TYPE_CLASS_NUMBER);
        items.add(levelsField);
    }

    private void createSpinner(AppCompatActivity context) {
        //create and set the properties of the difficulty spinner
        Spinner difficultySpinner = new Spinner(context);

        ArrayAdapter adapter = new ArrayAdapter(
                context, android.R.layout.simple_spinner_item, DIFFICULTIES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        difficultySpinner.setAdapter(adapter);
        difficultySpinner.setY(360);
        difficultySpinner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
               LayoutParams.WRAP_CONTENT));
        difficultySpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        items.add(difficultySpinner);
    }

    private void createButtons(final AppCompatActivity context) {

        //create and set the properties of the ads checkbox
        CheckBox adsBox = new CheckBox(context);
        adsBox.setY(520);
        adsBox.setText("Ads");
        adsBox.setChecked(true);
        adsBox.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        adsBox.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        adsBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) { toggleAds(context, b);
            }
        });
        items.add(adsBox);

        //create and set the properties of the apply button
        Button applyButton = new Button(context);
        applyButton.setText("Apply");
        applyButton.setTextSize(18);
        applyButton.setY(840);
        applyButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
               LayoutParams.WRAP_CONTENT));
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applySettings(context);
            }
        });
        setColours(applyButton);
        items.add(applyButton);

        //create and set the properties of the donate button
        Button donateButton = new Button(context);
        donateButton.setText("Help Feed The Devs");
        donateButton.setTextSize(18);
        donateButton.setY(1000);
        donateButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donate(context);
            }
        });
        setColours(donateButton);
        items.add(donateButton);


        //create and set the properties of the cancel button
        Button exitButton = new Button(context);
        exitButton.setText("Cancel");
        exitButton.setTextSize(18);
        exitButton.setY(1160);
        exitButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
               LayoutParams.WRAP_CONTENT));
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel(context);
            }
        });
        setColours(exitButton);
        items.add(exitButton);
    }

    private void applySettings(AppCompatActivity context){

        int levels = getLevels();
        boolean runningAds = getAdsEnabled();
        int difficulty = getDifficulty();

        if(levels < 3){
            displayPopupMessage(context);
        } else {
            proceedToApplication(context, levels, runningAds, difficulty);
        }


    }

    private void proceedToApplication(AppCompatActivity context, int levels, boolean runningAds, int difficulty){
        manager.setDifficulty(difficulty);
        manager.setLevels(levels);
        manager.setRunningAds(runningAds);
        startActivity(context);
    }

    private void startActivity(AppCompatActivity context){
        Intent intent = GameMenu.openMenu(context, GameMenu.MAIN_MENU);
        intent.putExtra(GameManager.INTENT_NAME, manager);

        context.startActivity(intent);
        context.finish();
    }

    private void displayPopupMessage(AppCompatActivity context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Must have 3 or more levels").setTitle("Problem");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //left intentionally blank
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int getLevels(){
        EditText levelField = (EditText) items.get(LEVEL_CHOICE);
        int levels = Integer.parseInt(levelField.getText().toString());
        return levels;
    }

    private boolean getAdsEnabled(){
        CheckBox adsBox = (CheckBox) items.get(ADS_BOX);
        return adsBox.isChecked();
    }

    private int getDifficulty(){
        Spinner difficultySpinner = (Spinner) items.get(DIFFICULTY_SELECTOR);
        String diffString = difficultySpinner.getSelectedItem().toString();
        int difficulty = 1;
        switch(diffString){
            case EASY_DIFFICULTY:
                difficulty = 1;
                break;

            case MEDIUM_DIFFICULTY:
                difficulty = 2;
                break;

            case HARD_DIFFICULTY:
                difficulty = 3;
                break;

        }

        return difficulty;

    }

    private void donate(AppCompatActivity context){
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.paypal.com/ca/home"));
        context.startActivity(intent);
    }

    private void cancel(AppCompatActivity context){
        Intent intent = GameMenu.openMenu(context, GameMenu.MAIN_MENU);
        intent.putExtra(GameManager.INTENT_NAME, manager);

        context.startActivity(intent);
        context.finish();
    }

    private void toggleAds(AppCompatActivity context, boolean checked){

        if(!checked){
            //make it difficult to disable the ads
            tryDisableAds(context, 0);
        }

    }

    private void tryDisableAds(final AppCompatActivity context, final int timesPressed){

        if(timesPressed < 10){
            showConfirmDialog(context, timesPressed);
        }else {
            disableAds();
        }

    }

    private void showConfirmDialog(final AppCompatActivity context, final int timesPressed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String message = buildMessageString(timesPressed);
        builder.setMessage(message).setTitle("Confirm");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tryDisableAds(context, timesPressed + 1);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                unDisableAds();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String buildMessageString(int repititions){
        StringBuilder message = new StringBuilder();
        message.append("Are you sure ");
        for(int i = 0; i < repititions; i++){
            message.append("you're sure ");
        }
        message.append("that you want to disable ads?");

        return message.toString();
    }

    private void disableAds(){

    }

    private void unDisableAds(){
        CheckBox adsBox = (CheckBox) items.get(ADS_BOX);
        adsBox.setChecked(true);
    }

}
