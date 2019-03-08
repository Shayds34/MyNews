package com.example.theshayds.mynewstest.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.theshayds.mynewstest.R;
import com.example.theshayds.mynewstest.R.id;
import com.example.theshayds.mynewstest.Utils.ViewPagerAdapter;
import com.example.theshayds.mynewstest.Views.ArticleSearchFragment;
import com.example.theshayds.mynewstest.Views.MostPopularFragment;
import com.example.theshayds.mynewstest.Views.TopStoriesFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create new Dialog
        mDialog = new Dialog(this);

        // Create toolbar
        Toolbar mMToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mMToolbar);
        final ActionBar mActionBar = getSupportActionBar();

        // Create NavigationDrawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        // Add menu button to Toolbar
        assert mActionBar != null;
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        // Create and setup viewPager
        mViewPager = findViewById(R.id.viewPager);
        setupViewPager(mViewPager);

        // Create and setup TabLayout with viewPager
        TabLayout mMTabLayout = findViewById(R.id.tabs);
        mMTabLayout.setupWithViewPager(mViewPager);

        // NavigationDrawer multiple events
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // Set item as selected to persist highlight

                        switch (item.getItemId()) {

                            case id.nav_top_stories :{
                                mViewPager.setCurrentItem(0);
                                break;
                                }

                            case id.nav_most_popular :{
                                mViewPager.setCurrentItem(1);
                                break;
                            }

                            case id.nav_arts :{
                                mViewPager.setCurrentItem(2);
                                break;
                            }
                            case id.nav_search :{
                                Intent mIntent = new Intent(MainActivity.this, SearchActivity.class);
                                startActivity(mIntent);
                                break;
                            }

                            case id.nav_notifications :{
                                Intent mIntent = new Intent(MainActivity.this, NotificationsActivity.class);
                                startActivity(mIntent);
                                break;
                            }

                            case id.nav_help :{
                                showHelpPopup();
                                break;
                            }
                            case id.nav_about :{
                                showAboutPopup();
                                break;
                            }
                        }
                        // Close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                // Respond when the drawer's position changes
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                // Respond when the drawer is opened
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                // Respond when the drawer is closed
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // Respond when the drawer motion state changes
            }
        });
    }

    // Show "help" information
    private void showHelpPopup() {
        mDialog.setContentView(R.layout.popup_help);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

    // Show "about" information
    private void showAboutPopup() {
        mDialog.setContentView(R.layout.popup_about);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

    // Close navigationDrawer when back button is pressed
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Create Option Menu in ToolBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    // Create actions when menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Open navigation drawer
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }

        // Switch between items from toolbar menu
        switch (item.getItemId()){
            case R.id.search:{
                // Start "SearchActivity"
                Intent mIntent2 = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(mIntent2);
                break;
            }
            case R.id.notifications:{
                // Start "NotificationActivity"
                Intent mIntent = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(mIntent);
                break;
            }
            case R.id.help: {
                // Open popup to show information "help"
                showHelpPopup();
                break;
            }
            case R.id.about: {
                // Open popup to show information "about"
                showAboutPopup();
                break;
            }
        }
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        // Setup the ViewPager with multiple fragment
        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(new TopStoriesFragment(), "TOP STORIES");
        mPagerAdapter.addFragment(new MostPopularFragment(), "MOST POPULAR");
        mPagerAdapter.addFragment(new ArticleSearchFragment(), "ARTS");
        viewPager.setAdapter(mPagerAdapter);
    }
}
