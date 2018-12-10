package edu.cs.cs184.photoapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.zomato.photofilters.imageprocessors.Filter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FilterSelectorActivity extends AppCompatActivity {

    // TODO: sort filters, maybe display some info, mark the suggested ones

    private static Context mContext;

    private static boolean showAllFilters = false;
    private static boolean featDialogShown = true;

    public static Context getContext(){
        return mContext;
    }


    // Daniel: adapted from my homework 3 implementation

    public static CustomFragmentsPagerAdapter customFragmentsPagerAdapter;
    private static ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ArrayList<FilterFragment> mFilterFragments;

    public static int getDisplayWidth= 1;
    public static int getDisplayHeight =1;
    private static FilterSelectorActivity activity;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

            //savedInstanceState.getBundle("main");
            showAllFilters = savedInstanceState.getBoolean("all");
            featDialogShown = savedInstanceState.getBoolean("featDialog");

            mViewPager.setCurrentItem(savedInstanceState.getInt("current", 1));
            mTabLayout.getTabAt(savedInstanceState.getInt("tab",1)).select();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // done: create a dialogfragment that displays a nicely formatted list of the features

        setContentView(R.layout.activity_filter_selector);
        updateWidth(this);
        mContext = getApplicationContext();
        activity = this;

        Intent intent = getIntent();

        if(savedInstanceState == null){
            Log.d("filterselect", "bundle not null");
            Bundle bundle = intent.getExtras();
            showAllFilters = bundle.getBoolean("all");
            featDialogShown = bundle.getBoolean("featDialog");
        }
        else{
            showAllFilters = savedInstanceState.getBoolean("all");
            featDialogShown = savedInstanceState.getBoolean("featDialog");
        }

        customFragmentsPagerAdapter = new CustomFragmentsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.fragmentsPager);
        mTabLayout = (TabLayout) findViewById(R.id.fragmentTabs);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        
        // done: fix tab selection listener - it currently causes the app to reopen?
        // solution : it was trying to set the icon color and there was no icon, removed line
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mViewPager.setCurrentItem(tab.getPosition());
                tab.select();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab.select();


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                try{mTabLayout.getTabAt(i).select();}catch (Exception e){}
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        setupViewPager(mViewPager);

        showFeatureDialog();


    }


    private void showFeatureDialog() {
        if(!featDialogShown) {
            FragmentManager fm = getSupportFragmentManager();

            FeatureDialog featureList = FeatureDialog.newInstance("Some Title");
            featureList.addData(MainActivity.features, MainActivity.percentCertainties);

            featureList.show(fm, "fragment_edit_name");
            featDialogShown = true;
        }

    }

    private void setupViewPager(ViewPager pager) {
        CustomFragmentsPagerAdapter pagerAdapter = new CustomFragmentsPagerAdapter(getSupportFragmentManager());
        mFilterFragments = new ArrayList<>();
        /*todo: generate the list of filters to apply, then apply them on each instance of a fragment.
        / todo: We could also generate an array of them right after the result is received in main activity.*/

        // Daniel: very roughly adapted from my hw 4 implementation.
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Don't want to send a full res bitmap because of memory and processing cost
        MainActivity.scaleBitmapDown( MainActivity.myPhoto,1000).compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();


        //TODO: conditional statement based on whether it displays applicable filters or all filters (customFilters=applicable, currentArray=all)
        //TODO: Add button on no filter page to show all filters
        if(showAllFilters){
            showAllFilters(pager);

        }
        else{
            showApplicableFilters(pager);
        }







    }





    public void updateWidth(Context context)
    {
        final WindowManager w = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        final Display d = w.getDefaultDisplay();
        final DisplayMetrics m = new DisplayMetrics();
        d.getMetrics(m);
        getDisplayWidth = m.widthPixels;
        getDisplayHeight = m.heightPixels;
    }

    public static void setShowAllFilters(boolean bool){
        showAllFilters = bool;
        activity.setupViewPager(mViewPager);


    }

    public static boolean getShowAll() { return showAllFilters; }

    public static int getWidth()
    {
        return getDisplayWidth;
    }
    public static int getHeight(){return getDisplayHeight;}

    // https://stackoverflow.com/questions/23902892/how-to-programmatically-trigger-the-touch-event-in-android for no button //todo: maybe come up with a less sketchy way to dismiss the dialog from its no button
    // otherwise the back button takes you back to the main activity and doesn't let you do anything
    /*@Override
    public void onBackPressed(){
        new AlertDialog.Builder(FilterSelectorActivity.this)
                .setTitle(R.string.dialog_quit_prompt)
                .setMessage("\n")
                .setPositiveButton("Yes", (dialog, which) -> this.finishAffinity())
                .setNegativeButton("No",(dialog,which)-> MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis()+10,MotionEvent.ACTION_UP,0f,0f,0))
                .setCancelable(true)
                .create().show();

    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
        updateWidth(this);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt("current",mViewPager.getCurrentItem());
        state.putInt("tab",mTabLayout.getSelectedTabPosition());
        state.putBundle("main",state);
        state.putBoolean("all", showAllFilters);
        state.putBoolean("featDialog", featDialogShown);
    }



public void showAllFilters(ViewPager pager){
    CustomFragmentsPagerAdapter pagerAdapter = new CustomFragmentsPagerAdapter(getSupportFragmentManager());
        /*todo: generate the list of filters to apply, then apply them on each instance of a fragment.
        / todo: We could also generate an array of them right after the result is received in main activity.*/

    // Daniel: very roughly adapted from my hw 4 implementation.
    ByteArrayOutputStream stream = new ByteArrayOutputStream();

    // Don't want to send a full res bitmap because of memory and processing cost
    MainActivity.scaleBitmapDown( MainActivity.myPhoto,1000).compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] byteArray = stream.toByteArray();
    mFilterFragments = new ArrayList<>();
    mTabLayout.removeAllTabs();

    //TODO: conditional statement based on whether it displays applicable filters or all filters (customFilters=applicable, currentArray=all)
    //TODO: Add button on no filter page to show all filters
        ArrayList<Pair<Filter,String>> currentArray = CustomFilters.getFiltersInOrder(MainActivity.percentCertainties,MainActivity.features,this.getApplicationContext());

        for(int i=0; i<currentArray.size();i++){

            FilterFragment currentFrag = FilterFragment.newInstance(byteArray,i,currentArray.get(i).second,null, null);
            mFilterFragments.add(currentFrag);
            pagerAdapter.addFragment(currentFrag);


            mTabLayout.addTab(mTabLayout.newTab().setText(currentArray.get(i).second));

            // fixed: set new ontablistener to select the right fragment
            // solution: blank tab in layout was pushing them all forward by 1, removed blank tab

        }
    mTabLayout.getTabAt(0).select();
    pager.setAdapter(pagerAdapter);
}

public void showApplicableFilters(ViewPager pager){
    CustomFragmentsPagerAdapter pagerAdapter = new CustomFragmentsPagerAdapter(getSupportFragmentManager());
        /*todo: generate the list of filters to apply, then apply them on each instance of a fragment.
        / todo: We could also generate an array of them right after the result is received in main activity.*/

    // Daniel: very roughly adapted from my hw 4 implementation.
    ByteArrayOutputStream stream = new ByteArrayOutputStream();

    // Don't want to send a full res bitmap because of memory and processing cost
    MainActivity.scaleBitmapDown( MainActivity.myPhoto,1000).compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] byteArray = stream.toByteArray();
    mFilterFragments = new ArrayList<>();
    mTabLayout.removeAllTabs();

    //TODO: conditional statement based on whether it displays applicable filters or all filters (customFilters=applicable, currentArray=all)
    //TODO: Add button on no filter page to show all filters
    ArrayList<CustomFilter> customFilters = CustomFilters.getFilters(MainActivity.features, MainActivity.percentCertainties, this.getApplicationContext());

    for(int i=0; i<customFilters.size();i++){

        FilterFragment currentFrag = FilterFragment.newInstance(byteArray,i,customFilters.get(i).getFilterName(), customFilters.get(i).getFeatures(), customFilters.get(i).getCerts());
        mFilterFragments.add(currentFrag);
        pagerAdapter.addFragment(currentFrag);
        Log.d("filtersort", customFilters.size()+"");


        mTabLayout.addTab(mTabLayout.newTab().setText(customFilters.get(i).getFilterName()));

        // fixed: set new ontablistener to select the right fragment
        // solution: blank tab in layout was pushing them all forward by 1, removed blank tab

    }
    mTabLayout.getTabAt(0).select();

    pager.setAdapter(pagerAdapter);
}




}

