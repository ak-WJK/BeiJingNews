package com.atguigu.beijingnews.base;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/6/3.
 */

public abstract class MenuDetailBasePager {


    public Context context;

    public View rootView;

    public MenuDetailBasePager(Context context) {
        this.context = context;

        rootView = initView();
    }

    public abstract View initView();


    public void initData() {

    }

}
