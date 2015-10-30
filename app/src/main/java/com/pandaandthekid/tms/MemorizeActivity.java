package com.pandaandthekid.tms;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.pandaandthekid.tms.verses.TMSVerse;
import com.pandaandthekid.tms.verses.bean.TMSBundle;
import com.pandaandthekid.tms.view.VerseFragment;
import com.pandaandthekid.tms.view.VersePagerAdapter;

import java.util.List;
import java.util.Vector;

public class MemorizeActivity extends AppCompatActivity {

    final static int START_VERSE = 1;

    TMSBundle currentPack;
    TMSVerse currentVerse;
    int verseIndex;

    ViewPager versePager;
    VersePagerAdapter pagerAdapter;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        currentPack = (TMSBundle) intent.getSerializableExtra(TMSBundle.CHOSEN_PACK);
        verseIndex = intent.getIntExtra(TMSBundle.CHOSEN_VERSE, START_VERSE);
        currentVerse = new TMSVerse(this, currentPack, verseIndex);

        Log.d(this.getClass().getSimpleName(), currentPack.toString());

        setContentView(R.layout.activity_memorize);

        drawerLayout = (DrawerLayout) findViewById(R.id.versesMenu);
        toolbar = (Toolbar) findViewById(R.id.tms_tool_bar);

        initializeToolbar();
        initializeFragments();
    }

    private void initializeFragments() {
        List<Fragment> fragments = new Vector<>();

        for (int index = 1; index <= 12; index++) {
            Bundle args = new Bundle();
            args.putInt(TMSBundle.CHOSEN_VERSE, index);

            fragments.add(Fragment.instantiate(this, VerseFragment.class.getName(), args));
        }

        this.pagerAdapter = new VersePagerAdapter(super.getSupportFragmentManager(), fragments);
        versePager = (ViewPager) findViewById(R.id.versePager);
        versePager.setAdapter(this.pagerAdapter);
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

        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}

