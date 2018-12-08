package com.rxjava.net.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.rxjava.net.R;
import com.rxjava.net.base.BaseActivity;
import com.rxjava.net.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.web_layout)
    FrameLayout view;
    private AgentWeb agentWeb;
    @BindView(R.id.title)
    TextView titleName;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(view, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(ContextCompat.getColor(this, R.color.main_style_blue), 3)
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                .createAgentWeb()
                .ready()
                .go(getIntent().getStringExtra("url"));
    }

    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (titleName != null && !TextUtils.isEmpty(title)) {
                titleName.setText(title);
            }
        }
    };

    @OnClick({R.id.title_back, R.id.title_close})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                if (!agentWeb.back()) {
                    finish();
                }
                break;
            case R.id.title_close:
                finish();
                break;
        }
    }

    @Override
    public void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    public void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
}
