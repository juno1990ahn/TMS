package com.pandaandthekid.tms;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
public class TMSActivity extends AppCompatActivity {

    private LinearLayout packsContainer;
    private PackScrollView packScroll;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

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

        drawerLayout = (DrawerLayout) findViewById(R.id.versesMenu);
        packScroll = (PackScrollView) findViewById(R.id.packScroll);
        packsContainer = (LinearLayout) findViewById(R.id.packLayout);
        toolbar = (Toolbar) findViewById(R.id.tms_tool_bar);

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

        initializeToolbar();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        packScroll.setCurrentItemAndCenter(0);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled(true);
        }

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_menu, R.string.close_menu) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                invalidateOptionsMenu();
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    public void openPack(View view) {
        TMSBundle pack = ((PackCardView) view).getPack();
        Log.d("TMS", "open Pack method");
        Intent intent = new Intent(this, MemorizeActivity.class);
        intent.putExtra(TMSBundle.CHOSEN_PACK, pack);

        startActivity(intent);
    }
}
