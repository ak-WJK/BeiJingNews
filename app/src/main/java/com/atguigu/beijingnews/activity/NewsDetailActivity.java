package com.atguigu.beijingnews.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atguigu.beijingnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsDetailActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_title_back)
    ImageButton ibTitleBack;
    @BindView(R.id.ib_title_textsize)
    ImageButton ibTitleTextsize;
    @BindView(R.id.title_shared)
    ImageButton titleShared;
    @BindView(R.id.progress)
    ProgressBar progress;
    private Uri url;
    private WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        setView();

        url = getIntent().getData();

        settings = webview.getSettings();
        //设置相关配置
        //设置支持javaScript
        settings.setJavaScriptEnabled(true);
        //设置双击页面变大变小
        settings.setUseWideViewPort(true);

        //添加变大变小按钮
        settings.setBuiltInZoomControls(true);

        //设置加载网页完成的监听
        webview.setWebViewClient(new WebViewClient() {
            @SuppressLint("WrongConstant")
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.setVisibility(View.GONE);
            }
        });

        //加载网页地址
        webview.loadUrl(url.toString());


    }

    @SuppressLint("WrongConstant")
    private void setView() {
        tvTitle.setVisibility(View.GONE);
        ibTitleBack.setVisibility(View.VISIBLE);
        ibTitleTextsize.setVisibility(View.VISIBLE);
        titleShared.setVisibility(View.VISIBLE);

    }


    @OnClick({R.id.ib_title_back, R.id.ib_title_textsize, R.id.title_shared})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_title_back:
                finish();
                break;
            case R.id.ib_title_textsize:

                showChangeTextSizeDialog();

                break;
            case R.id.title_shared:
                break;
        }
    }


    private int selectItem = 2;
    private int tempSelect;

    private void showChangeTextSizeDialog() {

        String[] items = {"超大字体", "大字体", "标准字体", "小字体", "超小字体"};
        new AlertDialog.Builder(this)
                .setTitle("设置字体大小")
                .setSingleChoiceItems(items, selectItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tempSelect = which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        selectItem = tempSelect;
                        selectTextSize(selectItem);
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }

    private void selectTextSize(int selectItem) {
        switch (selectItem) {
            case 0:
                settings.setTextZoom(200);
                break;
            case 1:
                settings.setTextZoom(150);
                break;
            case 2:
                settings.setTextZoom(100);
                break;
            case 3:
                settings.setTextZoom(75);
                break;
            case 4:
                settings.setTextZoom(50);
                break;
        }


    }
}
