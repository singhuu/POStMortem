package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.DataTypes.Hiscore;
import com.example.postmortem.DataTypes.HiscoreManager;
import com.example.postmortem.DataTypes.User;
import com.example.postmortem.GameManager;
import com.example.postmortem.R;
import com.example.postmortem.UserLoader;

import java.util.List;

public class GameOverMenuActivity extends AppCompatActivity {

  GameManager gameManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_over_menu);
    Bundle extras = getIntent().getExtras();
    gameManager = (GameManager) extras.get(GameManager.INTENT_NAME);
    populateCurrentScores();
    populateHighScores();
  }

  private void populateCurrentScores() {
    // tap score : a
    // pickup score: b
    // swipe score: c
    // type score: d
    // total score: a + b + c + d
    User user = gameManager.getActiveUser();
    StringBuilder scores = new StringBuilder();
    scores.append("Tap Level Score: " + user.getTapScore() + "\n");
    scores.append("Type Level Score: " + user.getTypeScore() + "\n");
    scores.append("Pickup Level Score: " + user.getPickupScore() + "\n");
    scores.append("Swipe Level Score: " + user.getTapScore() + "\n"); //TODO ADD SWIPE LEVEL GET
    scores.append("Total Score: " + user.getScore());

    TextView currentScores = findViewById(R.id.currentScores);
    currentScores.setText(scores.toString());
  }

  private void populateHighScores() {
    StringBuilder scores = new StringBuilder();

    List<Hiscore> hiscores = HiscoreManager.getManager().getHiscores();
    int i = 0;
    for(Hiscore hiscore: hiscores){
      scores.append("#");
      scores.append(i+1);
      scores.append(": ");
      scores.append(hiscore);
      scores.append("\n");
      i++;
    }

    TextView hiScores = findViewById(R.id.highScores);
    hiScores.setText(scores.toString());
  }

  public void restart(View target) {
    gameManager.start(this);
  }

  /**
   * Return to main menu.
   */
  public void quit(View target) {
    Intent intent = new Intent(this, MainMenuActivity.class);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);
    startActivity(intent);
  }
}
