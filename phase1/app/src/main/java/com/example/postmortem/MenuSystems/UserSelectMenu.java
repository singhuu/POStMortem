package com.example.postmortem.MenuSystems;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class UserSelectMenu extends GameMenu {

    UserSelectMenu(String title){
        super(title);
    }

    @Override
    public List<View> buildMenuItems(Context context) {

        ArrayList<View> items = new ArrayList<>();

        //set the properties of the textview
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextSize(32);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setY(64);
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        items.add(textView);

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

        //set the properties of the login button
        Button loginButton = new Button(context);
        loginButton.setText("Login");
        loginButton.setTextSize(18);
        loginButton.setY(512);
        loginButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        items.add(loginButton);

        //set the properties of the create user button
        Button createButton = new Button(context);
        createButton.setText("Create New User");
        createButton.setTextSize(18);
        createButton.setY(640);
        createButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        items.add(createButton);

        return items;

    }
}
