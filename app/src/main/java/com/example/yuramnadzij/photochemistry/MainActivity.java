package com.example.yuramnadzij.photochemistry;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements CameraFragment.OnFragmentInteractionListener,
        CalcFragment.OnFragmentInteractionListener, ResultFragment.OnFragmentInteractionListener{

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setScrollPosition(1,0f,true);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(tabSelectedListener);

    }

    public void setResult(String result){
        /*String solution = "Solution steps";
        //((ResultFragment)pagerAdapter.getItem(2)).setResult(solution , result);
        ResultFragment resultFragment = new ResultFragment();
        resultFragment.setResult(solution, result);*/

        viewPager.setCurrentItem(2);
    }

    public String getResult(){
        return result;
    }

    public ViewPager getViewPager(){
        return viewPager;
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

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DEL:
            {
                Toast.makeText(this, "Cursor position 1 / ", Toast.LENGTH_LONG).show();
                ((CalcFragment)pagerAdapter.getItem(0)).backspace();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
*/
}
