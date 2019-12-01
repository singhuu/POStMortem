package com.example.postmortem;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * this class controls all the sound heard in the game
 */
public class SoundManager {

    private Context mContext;
    private MediaPlayer main;
    private MediaPlayer wowEffect;
    private MediaPlayer booEffect;

    /**
     * the constructor creates mediaplayers for each sound and gets the MainMethod context which is
     * required in order to pull the sound files from their folder
     *
     * @param c the context of the file needed to pull the sound files
     */
    public SoundManager(Context c) {
        mContext = c;
        main = MediaPlayer.create(mContext, R.raw.mainmusic);
        wowEffect = MediaPlayer.create(mContext, R.raw.wowshorter);
        booEffect = MediaPlayer.create(mContext, R.raw.buzzerloweraudio);

    }

    /**
     * Starts the Title track
     */
    void startMainMusic() {
        main.start();
        main.setLooping(true);
    }

    public void playWowEffect() {

        wowEffect.start();
    }


    public void playBooEffect() {
        booEffect.start();
    }

}
