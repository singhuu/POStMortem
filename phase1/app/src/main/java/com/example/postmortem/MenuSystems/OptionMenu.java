package com.example.postmortem.MenuSystems;

import android.content.DialogInterface;
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

import java.util.List;

class OptionMenu extends GameMenu {

    private final int ADS_BOX = 3;

    OptionMenu(String title){
        super(title);
    }

    @Override
    public List<View> buildMenuItems(final AppCompatActivity context){

        createTextViews(context);
        createSpinner(context);
        createButtons(context);

        return items;
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
        String[] difficulties = {"easy", "medium", "hard"};

        ArrayAdapter adapter = new ArrayAdapter(
                context, android.R.layout.simple_spinner_item, difficulties);
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
                donate();
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

    }

    private void donate(){

    }

    private void cancel(AppCompatActivity context){
        context.finish();
    }

    private void toggleAds(AppCompatActivity context, boolean checked){

        if(checked){
            enableAds();
        }else {
            tryDisableAds(context, 0);
        }

    }

    private void enableAds(){


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
        builder.setMessage("Are you sure you wish to disable ads").setTitle("Confirm");
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

    private void disableAds(){

    }

    private void unDisableAds(){
        CheckBox adsBox = (CheckBox) items.get(ADS_BOX);
        adsBox.setChecked(true);
    }

}
