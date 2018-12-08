package com.rxjava.net.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class FragmentPageAdapter extends FragmentStatePagerAdapter {


    private Fragment[] mList;
    public FragmentPageAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.mList=fragments;
    }
    @Override
    public Fragment getItem(int position) {
       return mList[position];
    }

    @Override
    public int getCount() {
         return mList.length;
    }


}
