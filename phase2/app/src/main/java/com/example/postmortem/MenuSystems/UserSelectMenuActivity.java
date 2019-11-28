package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.DataTypes.AccountException;
import com.example.postmortem.DataTypes.User;
import com.example.postmortem.DataTypes.UserCredentialValidator;
import com.example.postmortem.DataTypes.UserManager;
import com.example.postmortem.GameManager;
import com.example.postmortem.R;

import java.util.Optional;

public class UserSelectMenuActivity extends AppCompatActivity {

  GameManager gameManager;
  UserManager userManager;
  private UserCredentialValidator validator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_select_menu);
    Bundle extras = getIntent().getExtras();
    gameManager = (GameManager) extras.get(GameManager.INTENT_NAME);
    userManager = UserManager.getManager();
    validator = new UserCredentialValidator();
  }

  public void login(View target) {
    EditText usernameField = findViewById(R.id.usernameField);
    EditText passwordField = findViewById(R.id.passwordField);

    String username = usernameField.getText().toString();
    String password = passwordField.getText().toString();

    tryLogin(username, password);
  }

  private void tryLogin(String username, String password){
    try{
      User user = validator.login(username, password);
      acceptLogin(user);
    } catch(AccountException e){
      String message = e.getMessage();
      constructDialog("Error", message);
    }
  }

  public void acceptLogin(User user) {
    // login accepted, advance to main menu screen
    gameManager.setActiveUser(user);
    Intent intent = new Intent(this, MainMenuActivity.class);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);
    startActivity(intent);
  }

  public void createUser(View target) {
    EditText usernameField = findViewById(R.id.usernameField);
    EditText passwordField = findViewById(R.id.passwordField);

    String username = usernameField.getText().toString();
    String password = passwordField.getText().toString();

    tryCreateUser(username, password);
  }

  private void tryCreateUser(String username, String password){
    try{
      validator.createAccount(username, password);
      constructDialog("success", "New user created.");
    } catch(AccountException e){
      String message = e.getMessage();
      constructDialog("Error", message);
    }
  }

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