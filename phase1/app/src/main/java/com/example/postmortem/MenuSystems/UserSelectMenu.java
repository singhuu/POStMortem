package com.example.postmortem.MenuSystems;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class UserSelectMenu extends GameMenu {

    UserSelectMenu(String title){
        super(title);
    }

    @Override
    public List<View> buildMenuItems(Context context) {

        return new ArrayList<>();

    }
}
