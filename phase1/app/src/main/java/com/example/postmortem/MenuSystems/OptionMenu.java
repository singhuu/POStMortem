package com.example.postmortem.MenuSystems;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

class OptionMenu extends GameMenu {

    OptionMenu(String title){
        super(title);
    }

    @Override
    public List<View> buildMenuItems(final AppCompatActivity context){

        //create and set the properties of the title
        TextView titleText = new TextView(context);
        titleText.setText(title);
        titleText.setTextSize(32);
        titleText.setY(64);
        titleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        titleText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setColours(titleText);
        items.add(titleText);

        //create and set the properties of the cancel button
        Button exitButton = new Button(context);
        exitButton.setText("Cancel");
        exitButton.setTextSize(18);
        exitButton.setY(360);
        exitButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel(context);
            }
        });
        setColours(exitButton);
        items.add(exitButton);

        return items;
    }

    private void cancel(AppCompatActivity context){
        context.finish();
    }

}
