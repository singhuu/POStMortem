package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.GameManager;
import com.example.postmortem.User;

import java.util.List;

class MainMenu extends GameMenu {

    MainMenu(String title){
        super(title);
    }

    @Override
    public List<View> buildMenuItems(final AppCompatActivity context){

        createTextViews(context);
        createButtons(context);

        return items;

    }

    private void createButtons(final AppCompatActivity context) {
        //set the properties of the continue button
        Button continueButton = new Button(context);
        continueButton.setText("Continue");
        continueButton.setTextSize(18);
        continueButton.setY(256);
        continueButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continueGame(context);
            }
        });
        setColours(continueButton);
        items.add(continueButton);

        //set the properties of the start button
        Button startButton = new Button(context);
        startButton.setText("Start");
        startButton.setTextSize(18);
        startButton.setY(416);
        startButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(context);
            }
        });
        setColours(startButton);
        items.add(startButton);

        //create and set the properties of the options button
        Button optionsButton = new Button(context);
        optionsButton.setText("Options");
        optionsButton.setTextSize(18);
        optionsButton.setY(576);
        optionsButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOptions(context);
            }
        });
        setColours(optionsButton);
        items.add(optionsButton);

        //set the properties of the logout button
        Button logoutButton = new Button(context);
        logoutButton.setText("Logout");
        logoutButton.setTextSize(18);
        logoutButton.setY(736);
        logoutButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(context);
            }
        });
        setColours(logoutButton);
        items.add(logoutButton);

        //set the properties of the exit button
        Button exitButton = new Button(context);
        exitButton.setText("Quit");
        exitButton.setTextSize(18);
        exitButton.setY(896);
        exitButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quit(context);
            }
        });
        setColours(exitButton);
        items.add(exitButton);
    }

    private void createTextViews(AppCompatActivity context) {
        Intent intent = context.getIntent();
        User user = (User) intent.getSerializableExtra("user");
        String username = user.getUsername();

        //set the properties of the user text view
        TextView userText = new TextView(context);
        userText.setText(username);
        userText.setX(832);
        items.add(userText);

        //set the properties of the title text view
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextSize(32);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setY(64);
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setColours(textView);
        items.add(textView);
    }

    private void continueGame(AppCompatActivity context){
        //TODO: once the game manager has been completed this can be implemented
    }

    private void start(AppCompatActivity context){
        //TODO: once the game manager has been completed this can be implemented

        /*
        //currently this just send the program to the game over menu

        Intent oldIntent = context.getIntent();
        User user = (User) oldIntent.getSerializableExtra("user");

        Intent newIntent = GameMenu.openMenu(context, GameMenu.GAME_OVER_MENU);
        newIntent.putExtra("user", user);

        context.startActivity(newIntent);
        context.finish();*/

        //Creates gameManager and start first round of random levels
        GameManager manager = new GameManager();
        manager.createLevel(1, context);

    }

    private void openOptions(AppCompatActivity context){
        Intent intent = GameMenu.openMenu(context, GameMenu.OPTIONS_MENU);
        context.startActivity(intent);
    }

    private void logout(AppCompatActivity context){
        Intent intent = GameMenu.openMenu(context, GameMenu.LOGIN_MENU);
        context.startActivity(intent);
    }

    private void quit(AppCompatActivity context){
        context.finish();
        System.exit(0);
    }

}
