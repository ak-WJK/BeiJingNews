package com.atguigu.beijingnews.detailpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.adapter.PhotosAdapter;
import com.atguigu.beijingnews.base.MenuDetailBasePager;
import com.atguigu.beijingnews.domain.NewsCenterBean;
import com.atguigu.beijingnews.domain.PhotosBean;
import com.atguigu.beijingnews.utils.ConstantUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/3.
 */

public class PhotosMenuDetailPager extends MenuDetailBasePager {


    private final NewsCenterBean.DataBean dataBean;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private ImageButton switchPager;
    private PhotosAdapter adapter;
    private List<PhotosBean.DataBean.NewsBean> newsBeans;
    private String url;


    public PhotosMenuDetailPager(Context context, NewsCenterBean.DataBean dataBean) {
        super(context);

        this.dataBean = dataBean;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.photos_detail_layout, null);
        ButterKnife.bind(this, view);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestNetwork(url);
            }
        });

        refreshLayout.setDistanceToTriggerSync(200);
        return view;
    }


    @Override
    public void initData() {
        url = ConstantUtils.BASE_URL + dataBean.getUrl();
//        Log.e("TAG", "url==" + url);
        requestNetwork(url);


    }

    private void requestNetwork(String url) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        Log.e("TAG", "请求图组数据成功" + s);

                        anaylsisJson(s);

                    }
                });
    }

    @SuppressLint("WrongConstant")
    private void anaylsisJson(String json) {
        PhotosBean photosBean = new Gson().fromJson(json, PhotosBean.class);
        newsBeans = photosBean.getData().getNews();

        if (newsBeans.size() != 0) {
            progress.setVisibility(View.GONE);

            adapter = new PhotosAdapter(context, newsBeans);

            recyclerview.setAdapter(adapter);
        } else {
            progress.setVisibility(View.VISIBLE);
        }

        //设置布局管理器
        recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));

        //设置刷新隐藏
        refreshLayout.setRefreshing(false);


    }

    private boolean isList = true;

    public void setSwitchPager(ImageButton switchPager) {
        if (isList) {
            recyclerview.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
            switchPager.setBackgroundResource(R.drawable.list_grid_select1);

            isList = false;
        } else {
            recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
            switchPager.setBackgroundResource(R.drawable.list_grid_select);

            isList = true;

            adapter.notifyItemRangeChanged(0, newsBeans.size());
        }


        this.switchPager = switchPager;
    }
}
