package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.postmortem.DataTypes.AccountException;
import com.example.postmortem.DataTypes.User;
import com.example.postmortem.DataTypes.UserCredentialValidator;
import com.example.postmortem.DataTypes.UserManager;
import com.example.postmortem.GameManager;
import com.example.postmortem.R;

public class UserSelectMenuActivity extends MenuActivity {

    GameManager gameManager;
    UserManager userManager;
    private UserCredentialValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_select_menu);
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        gameManager = (GameManager) extras.get(GameManager.INTENT_NAME);
        userManager = UserManager.getManager();
        validator = new UserCredentialValidator();
    }

    /**
     * Enters login information
     *
     * @param target the current view of the game
     */
    public void login(View target) {
        EditText usernameField = findViewById(R.id.usernameField);
        EditText passwordField = findViewById(R.id.passwordField);

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        tryLogin(username, password);
    }

    /**
     * Displays error message with wrong credentials
     *
     * @param username the username entered by the client
     * @param password the password entered by the client
     */
    private void tryLogin(String username, String password) {
        try {
            User user = validator.login(username, password);
            acceptLogin(user);
        } catch (AccountException e) {
            String message = e.getMessage();
            constructDialog("Error", message);
        }
    }

    /**
     * Accepts login and advances to the main menu
     *
     * @param user the User object
     */
    public void acceptLogin(User user) {
        gameManager.setActiveUser(user);
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra(GameManager.INTENT_NAME, gameManager);
        startActivity(intent);
    }

    /**
     * Creates a new user
     *
     * @param target the current view of the game
     */
    public void createUser(View target) {
        EditText usernameField = findViewById(R.id.usernameField);
        EditText passwordField = findViewById(R.id.passwordField);

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        if (tryCreateUser(username, password))
            login(target);
    }

    /**
     * Displays an error while creating user
     *
     * @param username the username entered by the client
     * @param password the password entered by the client
     * @return
     */
    private boolean tryCreateUser(String username, String password) {
        try {
            validator.createAccount(username, password);
            Toast userCreated = Toast.makeText(this, "User Created", Toast.LENGTH_LONG);
            userCreated.show();
            return true;
        } catch (AccountException e) {
            String message = e.getMessage();
            constructDialog("Error", message);
            return false;
        }
    }

    /**
     * Constructs a dialog when showing error
     *
     * @param title   the title of the error message
     * @param message the body of the message
     */
    public void constructDialog(String title, String message) {
        // code modded from https://medium.com/@suragch/making-an-alertdialog-in-android-2045381e2edb
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}