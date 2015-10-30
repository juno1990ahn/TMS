package com.pandaandthekid.tms.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pandaandthekid.tms.R;
import com.pandaandthekid.tms.verses.TMSVerse;
import com.pandaandthekid.tms.verses.bean.TMSBundle;

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

        currentPack = (TMSBundle) getActivity().getIntent().getSerializableExtra(TMSBundle.CHOSEN_PACK);
        currentVerse = new TMSVerse(getActivity(), currentPack, getArguments().getInt(TMSBundle.CHOSEN_VERSE));

        View view = inflater.inflate(R.layout.verse_fragment, container, false);

        verseSubTopicView = (TextView) view.findViewById(R.id.verseSubTopic);
        verseView = (TextView) view.findViewById(R.id.verse);
        verseScriptureView = (TextView) view.findViewById(R.id.verseScripture);

        screen = (LinearLayout) verseView.findViewById(R.id.memorizeScreen);

        verseSubTopicView.setText(currentVerse.getSubTopic());
        verseView.setText(currentVerse.getVerse());
        verseScriptureView.setText(currentVerse.getScripture());

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto-Light.ttf");

        verseSubTopicView.setTypeface(tf);
        verseView.setTypeface(tf);
        verseScriptureView.setTypeface(tf);

        return view;
    }
}
