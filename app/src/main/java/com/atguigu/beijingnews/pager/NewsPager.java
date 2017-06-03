package com.atguigu.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.beijingnews.MainActivity;
import com.atguigu.beijingnews.base.BasePager;
import com.atguigu.beijingnews.domain.NewsCenterBean;
import com.atguigu.beijingnews.fragment.LeftMenuFragment;
import com.atguigu.beijingnews.utils.ConstantUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/6/2.
 */

public class NewsPager extends BasePager {


    private List<NewsCenterBean.DataBean> datas;

    public NewsPager(Context context) {
        super(context);

    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("新闻");

        TextView textView = new TextView(context);
        textView.setText("新闻页面");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.BLACK);

        fl_content.addView(textView);

        getDataFromNet();


    }

    public void getDataFromNet() {
        String url = ConstantUtils.NEWSCENTER_PAGER_URL;

        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Log.e("TAG", "请求失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int i) {
                        Log.e("TAG", "请求成功==" + response);
                        processData(response);
                    }

                });

    }

    private void processData(String json) {

        NewsCenterBean newsCenterBean = new Gson().fromJson(json, NewsCenterBean.class);

        Log.e("TAG", "解析成功了哦==" + newsCenterBean.getData().get(0).getChildren().get(0).getTitle());

        datas = newsCenterBean.getData();
        MainActivity mainActivity = (MainActivity) context;
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        leftMenuFragment.setData(datas);


    }
}
