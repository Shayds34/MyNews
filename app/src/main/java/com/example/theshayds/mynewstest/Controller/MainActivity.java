package com.example.theshayds.mynewstest.Controller;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.theshayds.mynewstest.R;
import com.example.theshayds.mynewstest.Views.ArticleAdapter;
import com.example.theshayds.mynewstest.Views.ArticleSearchFragment;
import com.example.theshayds.mynewstest.Views.MostPopularFragment;
import com.example.theshayds.mynewstest.Views.TopStoriesFragment;
import com.example.theshayds.mynewstest.Views.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    ArticleAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        final ActionBar mActionBar = getSupportActionBar();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        assert mActionBar != null;
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mViewPager = findViewById(R.id.viewPager);
        setupViewPager(mViewPager);

        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {

                            case R.id.nav_top_stories :{
                                mViewPager.setCurrentItem(0);
                                Toast toast = Toast.makeText(getApplicationContext(), "Top Stories", Toast.LENGTH_LONG);
                                toast.show();
                                break;
                                }

                            case R.id.nav_most_popular :{
                                mViewPager.setCurrentItem(1);
                                Toast toast = Toast.makeText(getApplicationContext(), "Most Popular", Toast.LENGTH_LONG);
                                toast.show();
                                break;
                            }

                            case R.id.nav_business :{
                                mViewPager.setCurrentItem(2);
                                Toast toast = Toast.makeText(getApplicationContext(), "Business", Toast.LENGTH_LONG);
                                toast.show();
                                break;
                            }
                            case R.id.nav_search :{
                                Intent mIntent = new Intent(MainActivity.this, SearchActivity.class);
                                startActivity(mIntent);
                                break;
                            }

                            case R.id.nav_notifications :{
                                Intent mIntent = new Intent(MainActivity.this, NotificationsActivity.class);
                                startActivity(mIntent);
                                break;
                            }

                            case R.id.nav_help :{
                                Toast toast = Toast.makeText(getApplicationContext(), "Business", Toast.LENGTH_LONG);
                                toast.show();
                                break;
                            }
                            case R.id.nav_about :{
                                Toast toast = Toast.makeText(getApplicationContext(), "Business", Toast.LENGTH_LONG);
                                toast.show();
                                break;
                            }
                        }

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onClearItems(){
        mAdapter.clearNews();
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
                Intent mIntent2 = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(mIntent2);
                break;
            }
            case R.id.notifications:{
                Intent mIntent = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(mIntent);
                break;
            }
            case R.id.help: {
                Toast toast = Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_LONG);
                toast.show();
                break;
            }
            case R.id.about: {
                Toast toast = Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_LONG);
                toast.show();
                break;
            }
        }
        return true;
    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(new TopStoriesFragment(), "TOP STORIES");
        mPagerAdapter.addFragment(new MostPopularFragment(), "MOST POPULAR");
        mPagerAdapter.addFragment(new ArticleSearchFragment(), "BUSINESS");
        viewPager.setAdapter(mPagerAdapter);
    }
}
