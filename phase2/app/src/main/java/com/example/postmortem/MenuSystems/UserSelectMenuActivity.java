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

      // check login credentials
    Optional<User> user = getUserLoggedIn();
    if (user.isPresent()) {
      acceptLogin(user.get());
    } else {
      constructErrorDialog(target, "Username or password incorrect.");
    }
  }

  private Optional<User> getUserLoggedIn(){
    EditText usernameField = findViewById(R.id.usernameField);
    EditText passwordField =  findViewById(R.id.passwordField);

    String username = usernameField.getText().toString();
    String password = passwordField.getText().toString();

    return userManager.attemptLogin(username, password);
  }

  public void acceptLogin(User user) {
    // login accepted, advance to main menu screen
    gameManager.setActiveUser(user);
    Intent intent = new Intent(this, MainMenuActivity.class);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);
    startActivity(intent);
    finish();
  }

  public void createUser(View target) {

    EditText usernameField = findViewById(R.id.usernameField);
    EditText passwordField =  findViewById(R.id.passwordField);

    boolean created = tryCreateAccount();
    if (created) {
      usernameField.setHint("Account created");
      usernameField.setText("");
      passwordField.setText("");
      userManager.saveState();
    } else {
      constructErrorDialog(target, "User already exists.");
    }
  }

  private boolean tryCreateAccount(){

    EditText usernameField = findViewById(R.id.usernameField);
    EditText passwordField =  findViewById(R.id.passwordField);

    String username = usernameField.getText().toString();
    String password = passwordField.getText().toString();

    return userManager.createUser(username, password);

  }

  public void constructErrorDialog(View target, String message) {
    // code modded from https://medium.com/@suragch/making-an-alertdialog-in-android-2045381e2edb
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Error");
    builder.setMessage(message);
    builder.setPositiveButton("OK", null);
    AlertDialog dialog = builder.create();
    dialog.show();
  }
}