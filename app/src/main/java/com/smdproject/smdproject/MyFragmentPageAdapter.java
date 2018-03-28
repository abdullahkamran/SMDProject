package com.smdproject.smdproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Abdullah on 3/28/2018.
 */

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENT_COUNT = 4;

    public MyFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                //return new SongFragment();
            case 1:
                //return new PlaylistFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Squad Feed";
            case 1:
                return "Squad Events";
            case 2:
                return "Squad Map";
            case 3:
                return "Squad Chat";
        }
        return null;
    }

}
