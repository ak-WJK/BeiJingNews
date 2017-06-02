package com.atguigu.beijingnews.fragment;

import android.view.View;
import android.widget.TextView;

import com.atguigu.beijingnews.base.BaseFragment;

/**
 * Created by Administrator on 2017/6/2.
 */

public class LeftMenuFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(context);

        return textView;

    }

    @Override
    public void initData() {
        textView.setText("左侧");


    }
}
