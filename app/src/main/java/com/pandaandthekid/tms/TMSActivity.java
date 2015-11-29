package com.pandaandthekid.tms;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pandaandthekid.tms.adapters.NavMenuAdapter;
import com.pandaandthekid.tms.verses.TMSBundle;
import com.pandaandthekid.tms.view.PackCardView;
import com.pandaandthekid.tms.view.PackScrollView;

public class TMSActivity extends AppCompatActivity {

    private LinearLayout packsContainer;
    private PackScrollView packScroll;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView selectText;
    private TextView toolbarTitle;

    private boolean packsInitialized;

    private RecyclerView menuList;

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

        this.drawerLayout = (DrawerLayout) findViewById(R.id.versesMenu);
        this.packScroll = (PackScrollView) findViewById(R.id.packScroll);
        this.packsContainer = (LinearLayout) findViewById(R.id.packLayout);
        this.toolbar = (Toolbar) findViewById(R.id.tms_tool_bar);
        this.selectText = (TextView) findViewById(R.id.select_label);
        this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        this.menuList = (RecyclerView) findViewById(R.id.nav_list);

        this.packsInitialized = false;

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

        packScroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!packsInitialized) {
                    packScroll.setCurrentItemAndCenter(0);
                    packsInitialized = true;
                }
            }
        });

//        Typeface tf = Typeface.createFromAsset(getAssets(),
//                "font/Roboto-Light.ttf");
//
//        selectText.setTypeface(tf);
//        toolbarTitle.setTypeface(tf);

        initializeToolbar();
        initializeMenu();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_contact_us:
                // Send email to me
                final Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

                emailIntent.setType("plain/text");
                emailIntent.setData(Uri.parse(getString(R.string.contact_us_email)));
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.contact_us_email_subject));

                startActivity(Intent.createChooser(emailIntent, getString(R.string.contact_us_email_chooser)));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
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
