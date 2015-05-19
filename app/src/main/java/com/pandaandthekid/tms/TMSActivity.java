package com.pandaandthekid.tms;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.pandaandthekid.tms.util.SystemUiHider;
import com.pandaandthekid.tms.verses.bean.TMSBundle;
import com.pandaandthekid.tms.view.PackScrollView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class TMSActivity extends Activity {

    LinearLayout packsContainer;
    PackScrollView packScroll;

    TMSBundle[] visiblePacks = {
            TMSBundle.A_PACK,
            TMSBundle.B_PACK,
            TMSBundle.C_PACK,
            TMSBundle.D_PACK,
            TMSBundle.E_PACK
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tms);

        packScroll = (PackScrollView) findViewById(R.id.packScroll);
        packsContainer = (LinearLayout) findViewById(R.id.packLayout);

        for (int i = 0; i < visiblePacks.length; i++) {
//            PackCardView card = new PackCardView(this, visiblePacks[i]);
//            packsContainer.addView(card);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        packScroll.setCurrentItemAndCenter(0);
    }
}
