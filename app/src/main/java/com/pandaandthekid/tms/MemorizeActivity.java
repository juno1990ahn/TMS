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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.pandaandthekid.tms.adapters.NavMenuAdapter;
import com.pandaandthekid.tms.adapters.VersePagerAdapter;
import com.pandaandthekid.tms.verses.TMSBundle;
import com.pandaandthekid.tms.verses.TMSVerse;
import com.pandaandthekid.tms.view.VerseFragment;

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
    private TextView toolbarTitle;

    private RecyclerView menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        currentPack = (TMSBundle) intent.getSerializableExtra(TMSBundle.CHOSEN_PACK);
        verseIndex = intent.getIntExtra(TMSBundle.CHOSEN_VERSE, START_VERSE);
        currentVerse = new TMSVerse(this, currentPack, verseIndex);

        Log.d(this.getClass().getSimpleName(), currentPack.toString());

        setContentView(R.layout.activity_memorize);

        this.drawerLayout = (DrawerLayout) findViewById(R.id.versesMenu);
        this.toolbar = (Toolbar) findViewById(R.id.tms_tool_bar);
        this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        this.menuList = (RecyclerView) findViewById(R.id.nav_list);

//        Typeface tf = Typeface.createFromAsset(getAssets(),
//                "font/Roboto-Light.ttf");
//
//        toolbarTitle.setTypeface(tf);

        initializeToolbar();
        initializeFragments();
        initializeMenu();
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

    private void initializeMenu() {
        menuList.setHasFixedSize(true);
        menuList.setLayoutManager(new LinearLayoutManager(this));

        NavMenuAdapter adapter = new NavMenuAdapter(this);
        menuList.setAdapter(adapter);
    }

    private void initializeToolbar() {
        toolbar.setTitle("");
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

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0); // this disables the animation
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public TMSBundle getCurrentPack() {
        return this.currentPack;
    }
}

