package com.atguigu.beijingnews.detailpager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.atguigu.beijingnews.base.MenuDetailBasePager;

/**
 * Created by Administrator on 2017/6/3.
 */

public class VoteMenuDetailPager extends MenuDetailBasePager {

    private TextView textView;

    public VoteMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);

        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("投票");
    }
}
