package com.example.postmortem;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {

    private Context mContext;
    private MediaPlayer main;
    private MediaPlayer wowEffect;
    private MediaPlayer booEffect;
    public SoundManager(Context c) {
        mContext = c;
        main = MediaPlayer.create(mContext, R.raw.mainmusic);
        wowEffect = MediaPlayer.create(mContext, R.raw.wow);
        booEffect = MediaPlayer.create(mContext, R.raw.buzzerreal);

    }

    public void playMainMusic() {
        main.start();
        main.setLooping(true);
    }


    public void pauseMainMusic() {
        main.pause();
    }

    public void stopMainMusic() {
        main.stop();
        main.release();
    }

    public void playWowEffect() {

        wowEffect.start();
    }

    public boolean isWowPlaying() {
        return wowEffect.isPlaying(); 
    }

    public void stopWowEffect() {
        wowEffect.stop();
        wowEffect.release();
    }


    public void playBooEffect() {
        booEffect.start();
    }

    public void stopBooEffect() {

        booEffect.stop();
        booEffect.release();
    }








}
