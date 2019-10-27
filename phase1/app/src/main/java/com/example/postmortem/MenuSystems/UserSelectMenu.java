package com.example.postmortem.MenuSystems;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

class UserSelectMenu extends GameMenu {

    UserSelectMenu(String title){
        super(title);
    }

    @Override
    public List<View> buildMenuItems(final AppCompatActivity context) {

        createMenuTitle(context);
        createUserFields(context);
        createButtons(context);

        return items;

    }

    private void createUserFields(AppCompatActivity context) {
        //set the properties of the username edittext
        EditText usernameBar = new EditText(context);
        usernameBar.setText("username");
        usernameBar.setY(256);
        usernameBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        items.add(usernameBar);

        //set the properties of the password edittext
        EditText passwordBar = new EditText(context);
        passwordBar.setText("Password");
        passwordBar.setY(384);
        passwordBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        items.add(passwordBar);
    }

    private void createMenuTitle(AppCompatActivity context) {
        //set the properties of the title
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextSize(32);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setY(64);
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setColours(textView);
        items.add(textView);
    }

    private void createButtons(final AppCompatActivity context) {
        //set the properties of the login button
        Button loginButton = new Button(context);
        loginButton.setText("Login");
        loginButton.setTextSize(18);
        loginButton.setY(512);
        loginButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(context);
            }
        });
        setColours(loginButton);
        items.add(loginButton);

        //set the properties of the create user button
        Button createButton = new Button(context);
        createButton.setText("Create New User");
        createButton.setTextSize(18);
        createButton.setY(672);
        createButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptCreateAccount(context);
            }
        });
        setColours(createButton);
        items.add(createButton);
    }

    private void attemptLogin(AppCompatActivity context){
        EditText usernameBar = (EditText) items.get(1);
        String username = usernameBar.getText().toString();

        Intent intent = GameMenu.openMenu(context, GameMenu.MAIN_MENU);
        intent.putExtra("username", username);

        context.startActivity(intent);
        context.finish();
    }

    private void attemptCreateAccount(AppCompatActivity context){
        attemptLogin(context);
    }

}
