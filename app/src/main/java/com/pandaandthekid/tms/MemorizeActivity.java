package com.pandaandthekid.tms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;

import com.pandaandthekid.tms.verses.TMSVerse;
import com.pandaandthekid.tms.verses.bean.TMSBundle;

public class MemorizeActivity extends Activity {

    public final static int START_VERSE = 1;
    TMSBundle currentPack;
    TMSVerse currentVerse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        currentPack = (TMSBundle) intent.getSerializableExtra(TMSActivity.CHOSEN_PACK);
        currentVerse = new TMSVerse(this, currentPack, START_VERSE);
        Log.d(this.getClass().getSimpleName(), currentPack.toString());
        setContentView(R.layout.activity_memorize);
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

                break;
        }

        return true;
    }
}

