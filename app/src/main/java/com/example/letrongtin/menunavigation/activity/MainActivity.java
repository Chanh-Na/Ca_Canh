package com.example.letrongtin.menunavigation.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.letrongtin.menunavigation.R;
import com.example.letrongtin.menunavigation.fragment.AquariumsFragment;
import com.example.letrongtin.menunavigation.fragment.DecoratesFragment;
import com.example.letrongtin.menunavigation.fragment.FishesFragment;
import com.example.letrongtin.menunavigation.fragment.FoodsFragment;
import com.example.letrongtin.menunavigation.fragment.HomeFragment;
import com.example.letrongtin.menunavigation.fragment.InfoFragment;
import com.example.letrongtin.menunavigation.fragment.IrrigationTreesFragment;
import com.example.letrongtin.menunavigation.fragment.MachinesFragment;
import com.example.letrongtin.menunavigation.fragment.MedicinesFragment;
import com.example.letrongtin.menunavigation.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SendData {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg;
    private Toolbar toolbar;

    CollapsingToolbarLayout collapsingToolbar;
    AppBarLayout appBarLayout;

    //boolean isShow = false;
    //int scrollRange = -1;


    public static int navItemIndex = 0;

    private static final String TAG_HOME = "home";
    private static final String TAG_FISHES = "fishes";
    private static final String TAG_AQUARIUMS = "aquariums";
    private static final String TAG_DECORATES = "decorates";
    private static final String TAG_IRRIGATION_TREES = "irrigation trees";
    private static final String TAG_MACHINE = "machines";
    private static final String TAG_MEDICINES = "medicines";
    private static final String TAG_FOODS = "foods";
    private static final String TAG_INFO = "info";
    public static String CURRENT_TAG = TAG_HOME;

    private String[] fragmentTitles;
    private String[] fragmentImage;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    public static ArrayList<Cart> carts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navHeader = navigationView.getHeaderView(0);

        imgNavHeaderBg = navHeader.findViewById(R.id.img_header_bg);

        mHandler = new Handler();

        if (carts == null){
            carts = new ArrayList<>();
        }

        fragmentTitles = getResources().getStringArray(R.array.nav_item_fragment_titles);
        fragmentImage = new String[]{"https://firebasestorage.googleapis.com/v0/b/menunavigation-cc571.appspot.com/o/Backdrop%2Fhome.jpg?alt=media&token=c01669e3-27d4-408c-adfa-315d009e56d9",
        "https://firebasestorage.googleapis.com/v0/b/menunavigation-cc571.appspot.com/o/Backdrop%2Ffish.jpg?alt=media&token=05f26503-6baa-47cb-9acd-7c3290b1423e",
        "https://firebasestorage.googleapis.com/v0/b/menunavigation-cc571.appspot.com/o/Backdrop%2Faquariums.jpg?alt=media&token=3efe962b-6342-4195-b045-3a86ed29a18b",
        "https://firebasestorage.googleapis.com/v0/b/menunavigation-cc571.appspot.com/o/Backdrop%2Fdecorate.png?alt=media&token=ed074488-7dc8-4abd-86e2-0134435eef0a",
        "https://firebasestorage.googleapis.com/v0/b/menunavigation-cc571.appspot.com/o/Backdrop%2Firrigationtree.jpg?alt=media&token=9887b5be-8681-47da-9ce1-adba405d663c",
        "https://firebasestorage.googleapis.com/v0/b/menunavigation-cc571.appspot.com/o/Backdrop%2Fmachine.png?alt=media&token=82fc25da-1022-473b-8436-11871bf7c96e",
        "https://firebasestorage.googleapis.com/v0/b/menunavigation-cc571.appspot.com/o/Backdrop%2Fmedecine.jpg?alt=media&token=0f0dff11-a92b-4011-8c53-ddab26d29918",
        "https://firebasestorage.googleapis.com/v0/b/menunavigation-cc571.appspot.com/o/Backdrop%2Ffood.jpg?alt=media&token=30840d6c-2cda-4993-b5e0-ac090af7a6cf",
               "https://firebasestorage.googleapis.com/v0/b/menunavigation-cc571.appspot.com/o/Backdrop%2Fuit.jpg?alt=media&token=8babcc47-cae0-481f-b41c-dba851cc7b0a"};

        loadNavHeader();

        setUpNavigationView();

        if (savedInstanceState == null){
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadFragment();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadNavHeader() {

    }

    private void loadFragment() {

        navigationView.getMenu().getItem(navItemIndex).setChecked(true);

        getSupportActionBar().setTitle("");

        appBarLayout.setExpanded(true);

        loadImageAndTitle(fragmentImage[navItemIndex]);


        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null){
            drawer.closeDrawers();
            return;
        }

        Runnable mPendingRunable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();

            }
        };

        mHandler.post(mPendingRunable);

        drawer.closeDrawers();

        invalidateOptionsMenu();
    }

    private Fragment getFragment(){
        switch (navItemIndex){
            case 0:
                return new HomeFragment();
            case 1:
                return new FishesFragment();
            case 2:
                return new AquariumsFragment();
            case 3:
                return new DecoratesFragment();
            case 4:
                return new IrrigationTreesFragment();
            case 5:
                return new MachinesFragment();
            case 6:
                return new MedicinesFragment();
            case 7:
                return new FoodsFragment();
            case 8:
                return new InfoFragment();
            default:
                return new HomeFragment();

        }
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_fishes:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_FISHES;
                        break;
                    case R.id.nav_aquariums:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_AQUARIUMS;
                        break;
                    case R.id.nav_decorates:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_DECORATES;
                        break;
                    case R.id.nav_irrigation_trees:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_IRRIGATION_TREES;
                        break;
                    case R.id.nav_machines:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_MACHINE;
                        break;
                    case R.id.nav_medicines:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_MEDICINES;
                        break;
                    case R.id.nav_foods:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_FOODS;
                        break;
                    case R.id.nav_about_us:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_INFO;
                        break;
                    default:
                        navItemIndex = 0;
                }

                if (item.isChecked()){
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                item.setChecked(true);

                loadFragment();

                return true;
            }

        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawers();
            return;
        }

        if (shouldLoadHomeFragOnBackPress){
            if (navItemIndex != 0){
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    private void initCollapsingToolbar() {
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(fragmentTitles[navItemIndex]);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }

            }
        });
    }

    private void loadImageAndTitle(String image){

        ImageView backdrop = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this)
                .load(image)
                .placeholder(R.drawable.bg_fuzzy)
                .fit()
                .error(R.drawable.bg_fuzzy)
                .into(backdrop);

    }

    @Override
    public void ChangFragment(int index, String tag) {
        navItemIndex = index;
        CURRENT_TAG = tag;
        loadFragment();
    }




}
