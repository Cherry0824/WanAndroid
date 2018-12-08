package com.rxjava.net.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rxjava.net.R;
import com.rxjava.net.adapter.FragmentPageAdapter;
import com.rxjava.net.base.BaseActivity;
import com.rxjava.net.base.BasePresenter;
import com.rxjava.net.comm.AppCode;
import com.rxjava.net.comm.event.EventBusCode;
import com.rxjava.net.comm.event.MessageEvent;
import com.rxjava.net.ui.user.UserLoginActivity;
import com.rxjava.net.view.NoSlideViewPager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {


    @BindView(R.id.search_key)
    EditText searchKey;

    @BindView(R.id.search_button)
    TextView searchButton;

    @BindView(R.id.viewpager)
    NoSlideViewPager viewPager;
    private FragmentPageAdapter pageAdapter;


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        searchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    searchButton.setText("搜索");
                } else {
                    searchButton.setText("关闭");
                }
            }
        });
        pageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), new Fragment[]{SearchHotFragment.newInstance(), SearchResultFragment.newInstance()});
        viewPager.setScanScroll(false);
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(0);
    }

    @OnClick(R.id.search_button)
    public void OnClick(View view) {
        if (searchButton.getText().equals("关闭")) {
            finish();
        } else {
            if (viewPager.getCurrentItem() != 1)
                viewPager.setCurrentItem(1);
            EventBus.getDefault().post(new MessageEvent(EventBusCode.EVENT_SEARCH, searchKey.getText().toString().trim()));
        }
    }

}
