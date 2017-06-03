package com.atguigu.beijingnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.atguigu.beijingnews.MainActivity;
import com.atguigu.beijingnews.R;

/**
 * Created by Administrator on 2017/6/2.
 */

public class BasePager {

    public TextView tv_title;
    public ImageButton ib_menu;
    public FrameLayout fl_content;

    public Context context;
    public View rootView;

    public BasePager(final Context context) {
        this.context = context;
        rootView = View.inflate(context, R.layout.base_pager_layout, null);
        tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        ib_menu = (ImageButton) rootView.findViewById(R.id.ib_menu);

        fl_content = (FrameLayout) rootView.findViewById(R.id.fl_content);

        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).getSlidingMenu().toggle();
            }
        });

    }


    //子类绑定数据的时候重写该方法
    public void initData() {


    }


}
