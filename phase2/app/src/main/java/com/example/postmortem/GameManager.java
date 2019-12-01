package com.example.postmortem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.DataTypes.Hiscore;
import com.example.postmortem.DataTypes.HiscoreManager;
import com.example.postmortem.DataTypes.User;
import com.example.postmortem.DataTypes.UserManager;
import com.example.postmortem.LevelSystems.LevelType;
import com.example.postmortem.LevelSystems.PickUpLevelActivity;
import com.example.postmortem.LevelSystems.SwipeLevelActivity;
import com.example.postmortem.LevelSystems.TapLevelActivity;
import com.example.postmortem.LevelSystems.TypeLevelActivity;
import com.example.postmortem.MenuSystems.GameOverMenuActivity;

public class GameManager implements Parcelable {

    public static final String INTENT_NAME = "manager";
    public static final Creator<GameManager> CREATOR = new Creator<GameManager>() {
        @Override
        public GameManager createFromParcel(Parcel in) {
            return new GameManager(in);
        }

        @Override
        public GameManager[] newArray(int size) {
            return new GameManager[size];
        }
    };
    /**
     * Total Score of the game
     */
    public int totalScore;
    /**
     * The manager for the games users
     */
    private UserManager userManager;
    /**
     * The Current Level Type
     */
    private LevelType currLevelType;
    /**
     * The Active state of the User
     */
    private User activeUser;
    /**
     * Stores what level the user is in
     */
    private int levels;
    /**
     * Checks if the Ads are running or not
     */
    private boolean runningAds;
    /**
     * The difficulty of a certain level
     */
    private int difficulty;

    /**
     * Constructor of the GameManager class that is public
     * <p>
     * Initializes variables levels, difficulty & runningAds
     * </p>
     */
    GameManager() {
        this.userManager = UserManager.getManager();
        this.levels = 4;
        this.difficulty = 1;
        this.runningAds = true;
    }

    /**
     * Constructor of the GameManager class that is protected
     * <p>
     * Initializes currLevelType, activeUser, levels, difficulty & runningAds.
     * Not visible to outside methods; other than the ones that GameManager allows
     * </p>
     *
     * @param in Input of Parcel that initializes the variables
     */
    private GameManager(Parcel in) {
        this.userManager = UserManager.getManager();

        this.currLevelType = (LevelType) in.readSerializable();
        this.activeUser = userManager.getUser(in.readString());
        this.levels = in.readInt();
        this.difficulty = in.readInt();
        this.runningAds = in.readInt() == 1;
    }

    /**
     * Function that writes to Parcel Object
     *
     * @param dest  Object to which the variable is being moved to
     * @param flags Temporary variable to keep track of output
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(currLevelType);
        //dest.writeInt(currLevelType);
        if (activeUser == null) {
            dest.writeString("null");
        } else {
            dest.writeString(activeUser.getUsername());
        }
        dest.writeInt(levels);
        dest.writeInt(difficulty);
        if (runningAds) {
            dest.writeInt(1);
        } else {
            dest.writeInt(0);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Returns activeUser
     *
     * @return activeUser object
     */
    public User getActiveUser() {
        return activeUser;
    }

    /**
     * Setter function that sets activeUser
     *
     * @param activeUser the activeUser object that is set
     */
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * Setter function that enables/disables if ads are running
     *
     * @param runningAds stores boolean values of ads if enabled
     */
    public void setRunningAds(boolean runningAds) {
        this.runningAds = runningAds;
    }

    /**
     * Setter function that stores the level
     *
     * @param levels integer value of level
     */
    public void setLevels(int levels) {
        this.levels = levels;
    }

    /**
     * Getter function that returns the difficulty
     *
     * @return integer value of difficulty (1,2 or 3)
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Setter function that stores the difficulty
     *
     * @param difficulty integer value of difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Starts a new game activity based on the settings chosen by the user
     *
     * @param context the current context of the app
     */
    public void start(AppCompatActivity context) {
        levels--;
        Intent intent = createRandomLevel(context);
        updateActiveUser();
        transitionLevel(context, intent);
    }

    /**
     * Continues the game from previous state
     *
     * @param context the current context of the app
     */
    public void continueFromSave(AppCompatActivity context) {
        loadSettingsFromUser();
        LevelType currentLevelType = activeUser.getCurrentRunLevelType();
        Intent intent = createGivenLevel(context, currentLevelType);
        transitionLevel(context, intent);
    }

    /**
     * Loads the previously selected settings of the User
     */
    private void loadSettingsFromUser() {
        levels = activeUser.getCurrentRunLevels();
        difficulty = activeUser.getCurrentRunDifficulty();
        runningAds = activeUser.isRunningAds();
    }

    /**
     * Starts the new game and go to the next activity
     *
     * @param context the current context of the app
     */
    public void play(AppCompatActivity context) {
        if (levels > 0) {
            levels--;
            continuePlaying(context);
        } else {
            gameOver(context);
        }
    }

    /**
     * States that the game is over and goes to the Game Over Menu
     *
     * @param context the current state of the app
     */
    private void gameOver(AppCompatActivity context) {
        updateHiscores();
        endGame(context);
    }

    /**
     * Ends the game
     *
     * @param context the current state of the app
     */
    private void endGame(AppCompatActivity context) {
        Intent intent = new Intent(context, GameOverMenuActivity.class);
        intent.putExtra(INTENT_NAME, this);

        context.startActivity(intent);
        context.finish();
    }

    /**
     * Updates high score of the user
     */
    private void updateHiscores() {
        if (activeUser.getScore() > activeUser.getHiscore()) {
            activeUser.setHiscore(activeUser.getScore());
            Hiscore hiscore = new Hiscore(activeUser.getUsername(), activeUser.getScore());
            HiscoreManager hiscoreManager = HiscoreManager.getManager();
            hiscoreManager.addHiscore(hiscore);
            hiscoreManager.saveState();
            userManager.saveState();
        }
    }

    /**
     * Continue playing the game from the previously left state
     *
     * @param context the current state of the app
     */
    private void continuePlaying(AppCompatActivity context) {
        Intent intent = createRandomLevel(context);
        updateActiveUser();
        tryRunAds(context, intent);
    }

    /**
     * Creates a level and adds it to level list,
     *
     * @param context the current state of the app
     * @return an Intent object for the new LevelType created
     */
    private Intent createRandomLevel(AppCompatActivity context) {

        LevelType newLevelType = decideNewLevelType();

        if (currLevelType != newLevelType) {
            return createGivenLevel(context, newLevelType);
        } else {
            return createRandomLevel(context);
        }
    }

    /**
     * Decides what the next Level is going to be
     *
     * @return the new level type
     */
    private LevelType decideNewLevelType() {
        return LevelType.randomLevelType();
    }

    /**
     * Creates and returns an intent to the specified level
     *
     * @param context   the context of the program
     * @param levelType the type of level to create
     */
    private Intent createGivenLevel(AppCompatActivity context, LevelType levelType) {

        currLevelType = levelType;
        Intent newLevelIntent = createLevelIntent(context);
        addExtras(newLevelIntent);

        return newLevelIntent;

    }

    /**
     * Creates and returns an intent of a level
     *
     * @param context the context of the program
     * @return the level that has been created
     */
    private Intent createLevelIntent(Context context) {
        Intent intent = null;

        switch (currLevelType) {
            case PICKUP:
                intent = new Intent(context, PickUpLevelActivity.class);
                break;

            case TAP:
                intent = new Intent(context, TapLevelActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;

            case TYPE:
                intent = new Intent(context, TypeLevelActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;

            case SWIPE:
                intent = new Intent(context, SwipeLevelActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;

            default:
                System.out.println("Unknown level type");
        }

        intent.putExtra("CURR_USERNAME", activeUser.getUsername());
        return intent;
    }

    /**
     * Attaching the key value pairs using putExtra
     *
     * @param intent the operation to be performed
     */
    private void addExtras(Intent intent) {
        intent.putExtra("DIFFICULTY", difficulty);
        intent.putExtra("GAME_MANAGER", this);
    }

    /**
     * Updates the active State of the User
     */
    private void updateActiveUser() {
        activeUser.setCurrentRunLevels(levels);
        activeUser.setRunningAds(runningAds);
        activeUser.setCurrentRunLevelType(currLevelType);
        activeUser.setCurrentRunDifficulty(difficulty);

        userManager.saveState();
    }

    /**
     * Method that decides if Ads are to be shown or not
     *
     * @param context the current state of the program
     * @param intent  the operation to be performed
     */
    private void tryRunAds(AppCompatActivity context, Intent intent) {

        if (runningAds) {
            showAd(context, intent);
        } else {
            AdNoShow(context, intent);
        }

    }

    /**
     * Displays the Ad
     *
     * @param context the current state of the program
     * @param intent  the operation to be performed
     */
    private void showAd(final AppCompatActivity context, final Intent intent) {

        AlertDialog.Builder builder = createEndPopup(context, intent);
        builder.setMessage("Level Complete \nScore: " + totalScore + "\nPlease Support the devs");
        builder.setPositiveButton("I support local devs", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                donate(context);
                //transitionLevel(context, intent);
            }
        });

        builder.setNegativeButton("I think local devs should starve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                transitionLevel(context, intent);
            }
        });

        builder.setCancelable(false); //Removes ability to click off dialog to close

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * Does not display the ad and shows the EndPopup instead
     *
     * @param context the current state of the program
     * @param intent  the operation of be performed
     */
    private void AdNoShow(AppCompatActivity context, Intent intent) {

        AlertDialog.Builder builder = createEndPopup(context, intent);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * Donate Button that takes to the donate link
     *
     * @param context the current state of the program
     */
    private void donate(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.paypal.com/ca/home"));
        context.startActivity(intent);
    }

    /**
     * Creates the Alert Dialog
     *
     * @param context the current state of the program
     * @param intent  the operation of be performed
     * @return returns the command to build the dialog box
     */
    private AlertDialog.Builder createEndPopup(final AppCompatActivity context, final Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Level Complete \nScore: " + totalScore);
        builder.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                transitionLevel(context, intent);
            }
        });

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                transitionLevel(context, intent);
            }
        });

        return builder;
    }

    /**
     * The state between to levels; allows the dialog box to pop up
     *
     * @param context the current state of the program
     * @param intent  the operation of be performed
     */
    private void transitionLevel(AppCompatActivity context, Intent intent) {
        context.startActivity(intent);
        context.finish();
    }

}
