package com.rxjava.net.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rxjava.net.base.BaseTabEntity;
import com.rxjava.net.ui.home.ArticleFragment;
import com.rxjava.net.ui.home.ProjectFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2018/12/3.
 */

public class HomeFragmentPageAdapter extends FragmentStatePagerAdapter {

    private List<BaseTabEntity> data = new ArrayList<>();

    public HomeFragmentPageAdapter(FragmentManager fm, List<BaseTabEntity> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return ArticleFragment.newInstance();
        else
            return ProjectFragment.newInstance();

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).getTitle();
    }
}
