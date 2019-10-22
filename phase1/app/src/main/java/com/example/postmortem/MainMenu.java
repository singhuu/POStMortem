package com.example.postmortem;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class MainMenu extends GameMenu {

    MainMenu(String title){
        super(title);
    }

    public List<View> buildMenuItems(final Context context){

        ArrayList<View> items = new ArrayList<>();

        //set the properties of the text view
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextSize(32);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setY(64);
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        items.add(textView);

        //set the properties of the button
        Button button = new Button(context);
        button.setText("Return");
        button.setTextSize(18);
        button.setY(256);
        button.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Return(context);
            }
        });

        items.add(button);

        return items;

    }

    private void Return(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}
