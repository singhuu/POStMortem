package com.example.postmortem.MenuSystems;

import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

public abstract class MenuActivity extends AppCompatActivity {
    /**
     * Used to disable back button usability in app
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // do something on back.
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
