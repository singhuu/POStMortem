package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.DataTypes.UserManager;
import com.example.postmortem.GameManager;
import com.example.postmortem.DataTypes.User;
import com.example.postmortem.UserLoader;

import java.util.List;
import java.util.Optional;

class UserSelectMenu extends GameMenu {

    private final int USERNAME_BAR = 1;
    private final int PASSWORD_BAR = 2;

    UserSelectMenu(String title) {
        super(title);
    }

    @Override
    public List<View> buildMenuItems(final AppCompatActivity context) {

        createMenuTitle(context);
        createUserFields(context);
        createButtons(context);

        return items;

    }

    /**
     * Sets the properties of the username and password with edit text
     *
     * @param context the current state of the program
     */
    private void createUserFields(AppCompatActivity context) {
        EditText usernameBar = new EditText(context);
        usernameBar.setHint(EditText.AUTOFILL_HINT_NAME);
        usernameBar.setY(256);
        usernameBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        items.add(usernameBar);

        EditText passwordBar = new EditText(context);
        passwordBar.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordBar.setHint(EditText.AUTOFILL_HINT_PASSWORD);
        passwordBar.setY(384);
        passwordBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        items.add(passwordBar);
    }

    /**
     * Set the properties of the title
     *
     * @param context the current state of the program
     */
    private void createMenuTitle(AppCompatActivity context) {

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
     * set the properties of the login and the create user button
     *
     * @param context the current state of the program
     */
    private void createButtons(final AppCompatActivity context) {

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
                attemptCreateAccount();
            }
        });
        setColours(createButton);
        items.add(createButton);
    }

    /**
     * Checks if user can login or not
     *
     * @param context the current state of the program
     */
    private void attemptLogin(AppCompatActivity context) {

        Optional<User> user = getUserLoggedIn();
        if (user.isPresent()) {
            loginSuccess(context, user.get());
        } else {
            loginFail();
        }
    }

    /**
     * Gets the user to enter username and password
     *
     * @return method to check if user has logged in or not
     */
    private Optional<User> getUserLoggedIn() {
        EditText usernameBar = (EditText) items.get(USERNAME_BAR);
        EditText passwordBar = (EditText) items.get(PASSWORD_BAR);

        String username = usernameBar.getText().toString();
        String password = passwordBar.getText().toString();

        UserManager userManager = UserManager.getManager();

        return userManager.attemptLogin(username, password);
    }

    /**
     * Method that displays activity if login is success
     *
     * @param context the current state of the program
     * @param user    User object that stores user properties
     */
    private void loginSuccess(AppCompatActivity context, User user) {

        Intent oldIntent = context.getIntent();
        GameManager manager = oldIntent.getParcelableExtra("manager");

        Intent newIntent = GameMenu.openMenu(context, GameMenu.MAIN_MENU);
        manager.setActiveUser(user);
        newIntent.putExtra(GameManager.INTENT_NAME, manager);

        context.startActivity(newIntent);
        context.finish();

    }

    /**
     * Login fails
     */
    private void loginFail() {

        EditText usernameBar = (EditText) items.get(USERNAME_BAR);
        EditText passwordBar = (EditText) items.get(PASSWORD_BAR);

        usernameBar.setText("");
        usernameBar.setHint("Login failed, try  again");

        passwordBar.setText("");

    }

    /**
     * Creating a new account and checks if username is taken
     */
    private void attemptCreateAccount() {

        EditText usernameBar = (EditText) items.get(USERNAME_BAR);
        EditText passwordBar = (EditText) items.get(PASSWORD_BAR);

        String username = usernameBar.getText().toString();
        String password = passwordBar.getText().toString();

        UserManager manager = UserManager.getManager();

        boolean userCreated = manager.createUser(username, password);

        if (userCreated) {
            usernameBar.setHint("Account created");
            usernameBar.setText("");
            passwordBar.setText("");
            manager.saveState();
        } else {
            usernameBar.setHint("Username Taken");
            usernameBar.setText("");
            passwordBar.setText("");
        }

    }

}
