package com.example.postmortem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.DataTypes.HiscoreManager;
import com.example.postmortem.DataTypes.UserManager;
import com.example.postmortem.MenuSystems.GameMenu;


public class MainActivity extends AppCompatActivity {

    /**
     * Main Context variable that modifies the current context if necessary
     */
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        initializeGame();
        startGame();

    }

    /**
     * Creates and initializes the intents to start the game
     */
    private void startGame() {
        GameManager gameManager = new GameManager();

        Intent intent = GameMenu.openMenu(this, GameMenu.LOGIN_MENU);
        intent.putExtra(GameManager.INTENT_NAME, gameManager);

        startActivity(intent);
    }

    /**
     * Finds the File Path and Loads the game
     */
    private void initializeGame() {
        String path = getFilesDir().getPath();
        UserManager.initialize(path);
        HiscoreManager.initialize(path);
    }

    /**
     * Getter function that returns the context
     *
     * @return mContext, the context in the main activity
     */
    public static Context get_m_Context() {
        return mContext;
    }


}
