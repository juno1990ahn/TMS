package com.pandaandthekid.tms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pandaandthekid.tms.verses.TMSVerse;
import com.pandaandthekid.tms.verses.bean.TMSBundle;

public class MemorizeActivity extends Activity {

    final static int START_VERSE = 1;

    TMSBundle currentPack;
    TMSVerse currentVerse;
    LinearLayout verseContainer;
    int verseIndex;

    TextView currentView;
    Button peekButton;
    Button nextButton;

    TextView[] answerViews;
    TextView[] progressedViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        currentPack = (TMSBundle) intent.getSerializableExtra(TMSBundle.CHOSEN_PACK);
        verseIndex = intent.getIntExtra(TMSBundle.CHOSEN_VERSE, START_VERSE);
        currentVerse = new TMSVerse(this, currentPack, verseIndex);
        Log.d(this.getClass().getSimpleName(), currentPack.toString());
        setContentView(R.layout.activity_memorize);

        verseContainer = (LinearLayout) findViewById(R.id.verseContainer);
        peekButton = (Button) findViewById(R.id.peekButton);
        nextButton = (Button) findViewById(R.id.nextButton);

        createAnswer();
        setListeners();
    }

    private void createAnswer() {
        answerViews = new TextView[3];
        answerViews[0] = new TextView(this);
        answerViews[1] = new TextView(this);
        answerViews[2] = new TextView(this);

        answerViews[0].setText(currentVerse.getTmsIndex() + ' ' + currentVerse.getSubTopic());
        answerViews[1].setText(currentVerse.getVerse());
        answerViews[2].setText(currentVerse.getScripture());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_UP) :
                if (!currentVerse.isEmpty()) {
                    String next = currentVerse.getNextWord();
                    if (next.equals(TMSVerse.DIVIDER)) {
                        currentView = new TextView(this);
                        verseContainer.addView(currentView);
                        next = currentVerse.getNextWord();
                    } else if (currentView == null) {
                        currentView = new TextView(this);
                        verseContainer.removeAllViews();
                        verseContainer.addView(currentView);
                    }
                    currentView.append(next);
                }
                break;
        }

        return true;
    }

    private void setListeners() {
        peekButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);

                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        progressedViews = new TextView[verseContainer.getChildCount()];
                        for (int i = 0; i < verseContainer.getChildCount(); i++) {
                            progressedViews[i] = (TextView) verseContainer.getChildAt(i);
                        }
                        verseContainer.removeAllViews();
                        verseContainer.addView(answerViews[0]);
                        verseContainer.addView(answerViews[1]);
                        verseContainer.addView(answerViews[2]);
                        break;
                    case (MotionEvent.ACTION_UP):
                        verseContainer.removeAllViews();
                        for ( TextView progressedView : progressedViews ) {
                            verseContainer.addView(progressedView);
                        }
                        break;
                }
                return true;
            }
        });

        if (currentPack.getIndex() == 4 && verseIndex == 12) {
            nextButton.setText(R.string.to_main_btn);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                nextVerse();
            }
        });
    }

    private void nextVerse() {

        if (verseIndex < 12) {
            Intent nextIntent = new Intent(this, MemorizeActivity.class);
            nextIntent.putExtra(TMSBundle.CHOSEN_PACK, currentPack);
            nextIntent.putExtra(TMSBundle.CHOSEN_VERSE, verseIndex + 1);
            startActivity(nextIntent);
        } else if (currentPack.getIndex() == 4) {
            Intent mainIntent = new Intent(this, TMSActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
        } else {
            Intent nextIntent = new Intent(this, MemorizeActivity.class);
            int index = currentPack.getIndex();
            nextIntent.putExtra(TMSBundle.CHOSEN_PACK, TMSBundle.bundleOrder[index + 1]);
            startActivity(nextIntent);
        }
    }
}

