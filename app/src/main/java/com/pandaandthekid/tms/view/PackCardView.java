package com.pandaandthekid.tms.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pandaandthekid.tms.R;
import com.pandaandthekid.tms.verses.TMSBundle;

public class PackCardView extends LinearLayout {

    TMSBundle pack;

    TextView letterView;
    TextView topicView;

    public PackCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.pack_card, this);
    }

    public PackCardView(Context context, TMSBundle pack, String topic) {
        super(context);

        this.pack = pack;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.pack_card, this);
        Log.d(this.getClass().getSimpleName(), pack.toString() + " created");

        this.letterView = (TextView) findViewById(R.id.packLetter);
        this.topicView = (TextView) findViewById(R.id.packTopic);

        this.letterView.setText(pack.toString());
        this.topicView.setText(topic);

//        Typeface tf = Typeface.createFromAsset(context.getAssets(),
//                "font/Roboto-Light.ttf");
//
//        this.letterView.setTypeface(tf);
//        this.topicView.setTypeface(tf);
    }

    public TMSBundle getPack() {
        return pack;
    }
}
