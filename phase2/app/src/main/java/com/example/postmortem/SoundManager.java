package com.example.postmortem;

import android.content.Context;
import android.media.MediaPlayer;

/** this class controls all the sound heard in the game */
public class SoundManager {

  private Context mContext;
  private MediaPlayer main;
  private MediaPlayer wowEffect;
  private MediaPlayer booEffect;

  /** the constructor creates mediaplayers for each sound and gets the MainMethod context which is
   * required in order to pull the sound files from their folder
   * @param c the context of the file needed to pull the sound files
   */
  public SoundManager(Context c) {
    mContext = c;
    main = MediaPlayer.create(mContext, R.raw.mainmusic);
    wowEffect = MediaPlayer.create(mContext, R.raw.wowshorter);
    booEffect = MediaPlayer.create(mContext, R.raw.buzzerloweraudio);

  }

  public void startMainMusic() {
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
