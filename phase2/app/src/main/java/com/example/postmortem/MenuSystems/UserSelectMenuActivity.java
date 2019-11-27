package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.DataTypes.UserManager;
import com.example.postmortem.GameManager;
import com.example.postmortem.R;

public class UserSelectMenuActivity extends AppCompatActivity {

  GameManager gameManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_select_menu);
    Bundle extras = getIntent().getExtras();
    gameManager = (GameManager) extras.get(GameManager.INTENT_NAME);
  }

  public void login(View target) {
    EditText uname = (EditText) findViewById(R.id.usernameField);
    EditText pword = (EditText) findViewById(R.id.passwordField);

    if (uname.getText().toString().isEmpty() | pword.getText().toString().isEmpty()) {
      constructErrorDialog(target, "Username or password blank.");
    } else {
      // check login credentials
      UserManager userManager = UserManager.getManager();
      if (true) {
        // TODO MAKE PROPER LOGIN CHECK ONCE USER MANAGER GETS FIGURED OUT
        acceptLogin(target);
      } else {
        constructErrorDialog(target, "Username or password incorrect.");
      }
    }
  }

  public void acceptLogin(View target) {
    // login accepted, advance to main menu screen
    Intent intent = new Intent(this, MainMenuActivity.class);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);
    startActivity(intent);
  }

  public void createUser(View target) {
    EditText uname = (EditText) findViewById(R.id.usernameField);
    EditText pword = (EditText) findViewById(R.id.passwordField);

    if (uname.getText().toString().isEmpty() | pword.getText().toString().isEmpty()) {
      constructErrorDialog(target, "Username or password blank.");
    } else {
      // TODO MAKE PROPER USER LOADER CHECK
    }
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