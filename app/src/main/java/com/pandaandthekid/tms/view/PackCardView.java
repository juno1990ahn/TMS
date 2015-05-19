package com.pandaandthekid.tms.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.pandaandthekid.tms.R;
import com.pandaandthekid.tms.verses.bean.TMSBundle;

/**
 * Created by juneahn on 5/16/15.
 */
public class PackCardView extends LinearLayout {

    TMSBundle pack;

    public PackCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.pack_card, this);
    }

    public PackCardView(Context context, TMSBundle pack) {
        super(context);

        this.pack = pack;
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.pack_card, this);
        Log.d(this.getClass().getSimpleName(), pack + " created");
    }

}
