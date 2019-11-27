package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.DataTypes.User;
import com.example.postmortem.DataTypes.UserManager;
import com.example.postmortem.GameManager;
import com.example.postmortem.R;

import java.util.Optional;

public class UserSelectMenuActivity extends AppCompatActivity {

  GameManager gameManager;
  UserManager userManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_select_menu);
    Bundle extras = getIntent().getExtras();
    gameManager = (GameManager) extras.get(GameManager.INTENT_NAME);
    userManager = UserManager.getManager();
  }

  public void login(View target) {
    EditText uname = (EditText) findViewById(R.id.usernameField);
    EditText pword = (EditText) findViewById(R.id.passwordField);

    if (uname.getText().toString().isEmpty() | pword.getText().toString().isEmpty()) {
      constructDialog(target, "Error","Username or password blank.");
    } else {
      // check login credentials
      Optional<User> user = userManager.attemptLogin(uname.getText().toString(), pword.getText().toString());
      if (user.isPresent()) {
        acceptLogin(user.get());
      } else {
        constructDialog(target, "Error","Username or password incorrect.");
      }
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
    EditText uname = (EditText) findViewById(R.id.usernameField);
    EditText pword = (EditText) findViewById(R.id.passwordField);

    if (uname.getText().toString().isEmpty() | pword.getText().toString().isEmpty()) {
      constructDialog(target, "Error","Username or password blank.");
    } else {
      if (userManager.createUser(uname.getText().toString(), pword.getText().toString())) {
        //create user, since not already created
        constructDialog(target, "Success", "New user created.");
      } else {
        constructDialog(target, "Error","User already created.");
      }
    }
  }

  public void constructDialog(View target, String title, String message) {
    // code modded from https://medium.com/@suragch/making-an-alertdialog-in-android-2045381e2edb
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(title);
    builder.setMessage(message);
    builder.setPositiveButton("OK", null);
    AlertDialog dialog = builder.create();
    dialog.show();
  }
}