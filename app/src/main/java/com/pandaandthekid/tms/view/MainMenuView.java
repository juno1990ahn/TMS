package com.pandaandthekid.tms.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.pandaandthekid.tms.R;

public class MainMenuView extends RelativeLayout {
    public MainMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.main_menu, this);
    }
}
