package com.qhsd.library.view.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragmentArray;

    public TabViewPagerAdapter(FragmentManager mFragmentManager, Fragment[] fragmentArray) {
        super(mFragmentManager);
        this.fragmentArray = fragmentArray;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentArray[position];
    }

    @Override
    public int getCount() {
        return fragmentArray.length;
    }
}
