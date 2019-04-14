package com.example.yuramnadzij.photochemistry;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements CameraFragment.OnFragmentInteractionListener,
        CalcFragment.OnFragmentInteractionListener, ResultFragment.OnFragmentInteractionListener{

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private String result = "";
    private boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Calculator"));
        tabLayout.addTab(tabLayout.newTab().setText("Camera"));
        tabLayout.addTab(tabLayout.newTab().setText("Result"));
        //tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setScrollPosition(1,0f,true);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(tabSelectedListener);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).setIcon(R.drawable.camera);
        tabLayout.getTabAt(0).setIcon(R.drawable.calc);
        tabLayout.getTabAt(2).setIcon(R.drawable.folder);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.layoutColor));
    }

    public void setResult(String result){
        this.result = result;
    }

    public void setIsVisible(boolean isVisible){
        this.isVisible = isVisible;
    }

    public boolean getIsVisible(){
        return isVisible;
    }

    public String getResult(){
        return result;
    }

    public void setFragmentToShow(int i){
        viewPager.setCurrentItem(i);
    }

    public TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener(){
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
