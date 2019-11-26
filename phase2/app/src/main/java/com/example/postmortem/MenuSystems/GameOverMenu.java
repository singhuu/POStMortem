package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.DataTypes.Hiscore;
import com.example.postmortem.DataTypes.HiscoreManager;
import com.example.postmortem.GameManager;
import com.example.postmortem.DataTypes.User;

import java.util.List;

public class GameOverMenu extends GameMenu {

    /**
     * Game Manager Object that creates an instance of it
     */
    private GameManager manager;
    /**
     * User Object that creates an instance of it
     */
    private User user;

    GameOverMenu(String title) {
        super(title);
    }

    @Override
    public List<View> buildMenuItems(final AppCompatActivity context) {

        getGameManager(context);
        updateUserHiscore();

        createTextViews(context);
        createButtons(context);

        return items;

    }

    /**
     * Getter function of GameManager
     *
     * @param context the current state of the program
     */
    private void getGameManager(AppCompatActivity context) {
        Intent intent = context.getIntent();
        manager = intent.getParcelableExtra(GameManager.INTENT_NAME);
        user = manager.getActiveUser();
    }

    /**
     * Creates a text view
     *
     * @param context the current state of the program
     */
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

    /**
     * Getter function that gets high score texts and appends it
     *
     * @return high score in String form
     */
    private String getHiscoresText() {

        List<Hiscore> hiscores = HiscoreManager.getManager().getHiscores();
        StringBuilder hiscoresText = new StringBuilder();

        int i = 0;
        for(Hiscore hiscore: hiscores){
            hiscoresText.append(formatHiscore(i, hiscore));
            i++;

        }

        return hiscoresText.toString();

    }

    private StringBuilder formatHiscore(int i, Hiscore hiscore) {
        StringBuilder hiscoresText = new StringBuilder();
        hiscoresText.append(i+1);
        hiscoresText.append(" ");
        hiscoresText.append(hiscore);
        hiscoresText.append("\t");
        return hiscoresText;
    }

    /**
     * Formats the high score
     *
     * @param hiscore String Builder that stores high score
     * @param index   stores the index or position
     * @return a String that contains high score
     */
    /*
    private StringBuilder getFormattedHiscore(String[] hiscore, int index) {
        StringBuilder formattedHiscore = new StringBuilder();
        formattedHiscore.append(index + 1);
        formattedHiscore.append(": ");
        formattedHiscore.append(hiscore[0]);
        formattedHiscore.append(' ');
        formattedHiscore.append(hiscore[1]);
        formattedHiscore.append("\t");
        return formattedHiscore;
    }

     */

    /**
     * Gets user score in text form
     *
     * @param context the current state of the program
     * @return the user score in string form
     */
    private String getUserScoreText(AppCompatActivity context) {

        int tapScore = user.getTapScore();
        int typeScore = user.getTypeScore();
        int pickupScore = user.getPickupScore();

        int score = tapScore + typeScore + pickupScore; //TODO not using User.getScore()
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

    /**
     * Updates user high score
     */
    private void updateUserHiscore() {
        int score = user.getScore();
        if (user.getHiscore() < score) {

            user.setHiscore(score);
            Hiscore hiscore = new Hiscore(user.getUsername(), score);

            HiscoreManager hiscoreManager = HiscoreManager.getManager();
            hiscoreManager.addHiscore(hiscore);
            hiscoreManager.saveState();

            //UserLoader.updateHighScores(user.getUsername(), score);
            //UserLoader.updateFiles();
        }
    }

    /**
     * Creates clickable buttons for restart and quit
     *
     * @param context the current state of the program
     */
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

    /**
     * Restart the game
     *
     * @param context the current state of the program
     */
    private void restart(AppCompatActivity context) {
        Intent intent = GameMenu.openMenu(context, GameMenu.MAIN_MENU);
        intent.putExtra(GameManager.INTENT_NAME, manager);

        context.startActivity(intent);
        context.finish();

    }

    /**
     * Quit the game
     *
     * @param context the current state of the program
     */
    private void quit(AppCompatActivity context) {
        MainMenu.openMenu(context, MainMenu.MAIN_MENU);
    }

}
