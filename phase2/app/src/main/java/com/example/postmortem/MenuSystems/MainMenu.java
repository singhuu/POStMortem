package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.GameManager;
import com.example.postmortem.DataTypes.User;

import java.util.List;

class MainMenu extends GameMenu {

    /**
     * GameManager object is declared
     */
    private GameManager manager;

    MainMenu(String title) {

        super(title);

    }

    @Override
    public List<View> buildMenuItems(final AppCompatActivity context) {

        getGameManager(context);
        createTextViews(context);
        createButtons(context);

        return items;

    }

    /**
     * Getter function that initializes game manager
     *
     * @param context the current state of the program
     */
    private void getGameManager(AppCompatActivity context) {
        Intent intent = context.getIntent();
        manager = intent.getParcelableExtra(GameManager.INTENT_NAME);
    }

    /**
     * Sets the properties of Continue, Start, Options, Logout and Exit buttons
     *
     * @param context the current state of the program
     */
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

    /**
     * Sets the properties of the user and title text view
     *
     * @param context the current state of the program
     */
    private void createTextViews(AppCompatActivity context) {

        User user = manager.getActiveUser();
        String username = user.getUsername();

        TextView userText = new TextView(context);
        userText.setText(username);
        userText.setX(832);
        items.add(userText);

        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextSize(32);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setY(64);
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setColours(textView);
        items.add(textView);
    }

    /**
     * Continues the game from previous save
     *
     * @param context the current state of the program
     */
    private void continueGame(AppCompatActivity context) {
        manager.continueFromSave(context);
    }

    /**
     * Starts the game
     *
     * @param context the current state of the program
     */
    private void start(AppCompatActivity context) {
        manager.start(context);
    }

    /**
     * Opens the options menu
     *
     * @param context the current state of the program
     */
    private void openOptions(AppCompatActivity context) {

        Intent intent = GameMenu.openMenu(context, GameMenu.OPTIONS_MENU);
        intent.putExtra(GameManager.INTENT_NAME, manager);
        context.startActivity(intent);
        context.finish();
    }

    /**
     * The Logout button
     *
     * @param context the current state of the program
     */
    private void logout(AppCompatActivity context) {
        Intent intent = GameMenu.openMenu(context, GameMenu.LOGIN_MENU);
        intent.putExtra(GameManager.INTENT_NAME, manager);

        context.startActivity(intent);
        context.finish();
    }

    /**
     * the quit button
     *
     * @param context the current state of the program
     */
    private void quit(AppCompatActivity context) {
        context.finish();
        System.exit(0);
    }

}
