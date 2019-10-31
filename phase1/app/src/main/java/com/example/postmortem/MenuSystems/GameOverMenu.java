package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.GameManager;
import com.example.postmortem.LevelSystems.LevelType;
import com.example.postmortem.User;
import com.example.postmortem.UserLoader;

import java.util.List;

public class GameOverMenu extends GameMenu {

    private GameManager manager;
    private User user;

    GameOverMenu(String title){
        super(title);
    }

    @Override
    public List<View> buildMenuItems(final AppCompatActivity context){

        getGameManager(context);
        updateUserHiscore();

        createTextViews(context);
        createButtons(context);

        return items;

    }

    private void getGameManager(AppCompatActivity context){
        Intent intent = context.getIntent();
        manager = intent.getParcelableExtra(GameManager.INTENT_NAME);
        user = manager.getActiveUser();
    }

    private void createTextViews(AppCompatActivity context) {
        //create and set the properties of the title text view
        TextView titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextSize(32);
        titleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        titleView.setY(64);
        titleView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setColours(titleView);
        items.add(titleView);


        //create and set the properties of the user score title
        TextView userTitle = new TextView(context);
        userTitle.setText("Your Score");
        userTitle.setTextSize(24);
        userTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        userTitle.setY(480);
        userTitle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        items.add(userTitle);

        //create and set the properties of the user score view
        TextView userScore = new TextView(context);
        String userScoreText = getUserScoreText(context);
        userScore.setText(userScoreText);
        userScore.setTextSize(18);
        userScore.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        userScore.setY(608);
        userScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        items.add(userScore);

        //Create and set the properties of the hiscores title
        TextView hiscoresTitle = new TextView(context);
        hiscoresTitle.setText("Hiscores");
        hiscoresTitle.setTextSize(24);
        hiscoresTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        hiscoresTitle.setY(224);
        hiscoresTitle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        items.add(hiscoresTitle);

        //create and set the properties of the hiscores view
        TextView hiscoresView = new TextView(context);
        String hiscoresText = getHiscoresText();
        hiscoresView.setText(hiscoresText);
        hiscoresView.setTextSize(18);
        hiscoresView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        hiscoresView.setY(352);
        hiscoresView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        items.add(hiscoresView);
    }

    private String getHiscoresText(){

        List<String[]> hiscores = UserLoader.getHiscores();
        StringBuilder hiscoresText = new StringBuilder();

        int size = hiscores.size();

        for(int i = 0; i < size; i++){

            StringBuilder formattedHiscore = getFormattedHiscore(hiscores.get(i), i);
            hiscoresText.append(formattedHiscore);

        }

        for(int i = size; i < 5; i++){

            hiscoresText.append(i+1);
            hiscoresText.append(":  ");

        }

        return hiscoresText.toString();

    }

    private StringBuilder getFormattedHiscore(String[] hiscore, int index) {
        StringBuilder formattedHiscore = new StringBuilder();
        formattedHiscore.append(index+1);
        formattedHiscore.append(": ");
        formattedHiscore.append(hiscore[0]);
        formattedHiscore.append(' ');
        formattedHiscore.append(hiscore[1]);
        formattedHiscore.append("\t");
        return formattedHiscore;
    }

    private String getUserScoreText(AppCompatActivity context){

        int tapScore = user.getTapScore();
        int typeScore = user.getTypeScore();
        int pickupScore = user.getPickupScore();

        int score = user.getScore();
        int hiscore = user.getHiscore();

        StringBuilder userScoreText = new StringBuilder();
        userScoreText.append("Tap Score: ");
        userScoreText.append(tapScore);
        userScoreText.append("  Type Score: ");
        userScoreText.append(typeScore);
        userScoreText.append("  Pickup Score: ");
        userScoreText.append(pickupScore);
        userScoreText.append("  Total Score: ");
        userScoreText.append(score);
        userScoreText.append("  Hiscore: ");
        userScoreText.append(hiscore);

        return userScoreText.toString();
    }

    private void updateUserHiscore(){
        int score = user.getScore();
        if(user.getHiscore() < score){
            user.setHiscore(score);
            UserLoader.updateHiscores(user.getUsername(), score);
            UserLoader.updateFiles();
        }
    }

    private void createButtons(final AppCompatActivity context) {
        //create and set the properties of the restart button
        Button restart = new Button(context);
        restart.setText("Restart");
        restart.setTextSize(24);
        restart.setY(736);
        restart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setColours(restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart(context);
            }
        });
        items.add(restart);

        //create and set the properties of the quit button
        Button quit = new Button(context);
        quit.setText("Quit");
        quit.setTextSize(24);
        quit.setY(896);
        quit.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setColours(quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quit(context);
            }
        });
        items.add(quit);
    }

    private void restart(AppCompatActivity context){

        Intent oldIntent = context.getIntent();
        User user = (User) oldIntent.getSerializableExtra("user");

        Intent newIntent = GameMenu.openMenu(context, GameMenu.MAIN_MENU);
        newIntent.putExtra("user", user);

        context.startActivity(newIntent);
        context.finish();

    }

    private void quit(AppCompatActivity context){

        context.finish();
        System.exit(0);

    }

}
