package com.rxjava.net.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.rxjava.net.R;
import com.rxjava.net.adapter.FragmentPageAdapter;
import com.rxjava.net.base.BaseActivity;
import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.ui.home.HomeFragment;
import com.rxjava.net.ui.knowledge.KnowledgeFragment;
import com.rxjava.net.ui.navigation.NavigationFragment;
import com.rxjava.net.ui.project.ProjectFragment;
import com.rxjava.net.view.NoSlideViewPager;

import java.util.Date;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity {

    @BindView(R.id.menu_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.viewpager)
    NoSlideViewPager viewPager;


    private FragmentPageAdapter fragmentPageAdapter;
    private Fragment[] fragments;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        fragments = new Fragment[]{HomeFragment.newInstance(), ProjectFragment.newInstance(), KnowledgeFragment.newInstance(), NavigationFragment.newInstance()};
        fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setScanScroll(false);
        viewPager.setAdapter(fragmentPageAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(fragments.length - 1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mRadioGroup.check(R.id.radio_1);
                        break;
                    case 1:
                        mRadioGroup.check(R.id.radio_2);
                        break;
                    case 2:
                        mRadioGroup.check(R.id.radio_3);
                        break;
                    case 3:
                        mRadioGroup.check(R.id.radio_4);
                        //StatusBarUtil.setLightMode(MainActivity.this);//黑
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchTab(checkedId);
            }
        });

    }

    protected void switchTab(int id) {
        switch (id) {
            case R.id.radio_1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.radio_2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.radio_3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.radio_4:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    long lastPressTime = 0;

    @Override
    public void onBackPressed() {
        if (new Date().getTime() - lastPressTime < 1000) {
            finish();//结束程序
            System.exit(0);
        } else {
            lastPressTime = new Date().getTime();//重置lastPressTime
            Toasty.info(this, "再按一次退出", Toast.LENGTH_SHORT, true).show();
        }
    }
}
