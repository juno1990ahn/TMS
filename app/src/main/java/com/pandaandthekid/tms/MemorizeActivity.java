package com.pandaandthekid.tms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.pandaandthekid.tms.verses.bean.TMSBundle;

/**
 * Created by juneahn on 5/16/15.
 */
public class MemorizeActivity extends Activity {

    TMSBundle currentPack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        currentPack = (TMSBundle) intent.getSerializableExtra(TMSActivity.CHOSEN_PACK);
        Log.d(this.getClass().getSimpleName(), currentPack.toString());
        setContentView(R.layout.activity_memorize);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}

