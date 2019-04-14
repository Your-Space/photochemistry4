package com.example.yuramnadzij.photochemistry;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                final CalcFragment calcFragment = new CalcFragment();
                return calcFragment;
            case 1:
                CameraFragment cameraFragment = new CameraFragment();
                return cameraFragment;
            case 2:
                ResultFragment resultFragment = new ResultFragment();
                return resultFragment;
            /*case 3:
                HistoryFragment historyFragment = new HistoryFragment();
                return historyFragment;*/
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
