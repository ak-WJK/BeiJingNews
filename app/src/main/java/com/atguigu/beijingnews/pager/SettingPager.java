package com.atguigu.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.beijingnews.base.BasePager;

/**
 * Created by Administrator on 2017/6/2.
 */

public class SettingPager extends BasePager {


    public SettingPager(Context context) {
        super(context);

    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("设置");

        TextView textView = new TextView(context);
        textView.setText("设置页面");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.BLACK);

        fl_content.addView(textView);

    }
}
