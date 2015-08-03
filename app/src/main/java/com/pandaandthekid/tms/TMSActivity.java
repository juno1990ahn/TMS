package com.pandaandthekid.tms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.pandaandthekid.tms.util.SystemUiHider;
import com.pandaandthekid.tms.verses.bean.TMSBundle;
import com.pandaandthekid.tms.view.PackCardView;
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

        for (TMSBundle pack : visiblePacks) {
            int topicId = getResources().getIdentifier(String.format("topic_%s", pack.toString().toLowerCase()), "string", getPackageName());
            String topic = getString(topicId);
            Log.d("TMS", "Create pack " + pack.toString());
            PackCardView card = new PackCardView(this, pack, topic);
            card.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log.d("pack", "click pack");
                    openPack(view);
                }
            });
            packsContainer.addView(card);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        packScroll.setCurrentItemAndCenter(0);
    }

    public void openPack(View view) {
        TMSBundle pack = ((PackCardView) view).getPack();
        Log.d("TMS", "open Pack method");
        Intent intent = new Intent(this, MemorizeActivity.class);
        intent.putExtra(TMSBundle.CHOSEN_PACK, pack);

        startActivity(intent);
    }
}
