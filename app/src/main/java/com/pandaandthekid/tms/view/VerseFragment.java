package com.pandaandthekid.tms.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pandaandthekid.tms.R;
import com.pandaandthekid.tms.verses.TMSBundle;
import com.pandaandthekid.tms.verses.TMSVerse;

public class VerseFragment extends Fragment {

    TextView verseSubTopicView;
    TextView verseView;
    TextView verseScriptureView;
    LinearLayout screen;

    TMSVerse currentVerse;
    TMSBundle currentPack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        this.currentPack = (TMSBundle) getActivity().getIntent().getSerializableExtra(TMSBundle.CHOSEN_PACK);
        this.currentVerse = new TMSVerse(getActivity(), this.currentPack, getArguments().getInt(TMSBundle.CHOSEN_VERSE));

        View view = inflater.inflate(R.layout.verse_fragment, container, false);

        this.verseSubTopicView = (TextView) view.findViewById(R.id.verseSubTopic);
        this.verseView = (TextView) view.findViewById(R.id.verse);
        this.verseScriptureView = (TextView) view.findViewById(R.id.verseScripture);

        this.screen = (LinearLayout) this.verseView.findViewById(R.id.memorizeScreen);

        this.verseSubTopicView.setText(this.currentVerse.getTmsIndex() + " " + this.currentVerse.getSubTopic());
        this.verseView.setText(this.currentVerse.getVerse());
        this.verseScriptureView.setText(this.currentVerse.getScripture());

//        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
//                "font/Roboto-Light.ttf");
//
//        this.verseSubTopicView.setTypeface(tf);
//        this.verseView.setTypeface(tf);
//        this.verseScriptureView.setTypeface(tf);

        return view;
    }
}
