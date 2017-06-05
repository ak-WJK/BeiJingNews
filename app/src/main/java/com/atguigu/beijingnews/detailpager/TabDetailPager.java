package com.atguigu.beijingnews.detailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.base.MenuDetailBasePager;
import com.atguigu.beijingnews.domain.NewsCenterBean;
import com.atguigu.beijingnews.domain.TabDetailPagerBean;
import com.atguigu.beijingnews.utils.ConstantUtils;
import com.atguigu.beijingnews.utils.DensityUtil;
import com.atguigu.beijingnews.view.HorizontalScrollViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

;

/**
 * Created by Administrator on 2017/6/5.
 */

public class TabDetailPager extends MenuDetailBasePager {

    private final NewsCenterBean.DataBean.ChildrenBean childrenBean;

    HorizontalScrollViewPager viewpager;

    TextView tvTitle;

    LinearLayout llPointGroup;


    @BindView(R.id.lv)
    ListView lv;
    private TextView textView;

    private String url;

    private List<TabDetailPagerBean.DataBean.TopnewsBean> topnewsBeans;
    private int prePosition = 0;
    private List<TabDetailPagerBean.DataBean.NewsBean> newsBeans;
    private NewsAdapter newsAdapter;

    public TabDetailPager(Context context, NewsCenterBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        View view = View.inflate(context, R.layout.pager_tab_detail, null);
        ButterKnife.bind(this, view);


        View listTopView = View.inflate(context, R.layout.item_listtop_pager, null);

        viewpager = (HorizontalScrollViewPager) listTopView.findViewById(R.id.viewpager);
        tvTitle = (TextView) listTopView.findViewById(R.id.tv_title);
        llPointGroup = (LinearLayout) listTopView.findViewById(R.id.ll_point_group);


        //设置监听ViewPager页面的变化
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //把之前的设置默认
                llPointGroup.getChildAt(prePosition).setEnabled(false);

                //当前的设置true
                llPointGroup.getChildAt(position).setEnabled(true);

                //记录当前值
                prePosition = position;

            }

            @Override
            public void onPageSelected(int position) {

                String title = topnewsBeans.get(position).getTitle();
                tvTitle.setText(title);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //把viewpager添加到listView的头
        lv.addHeaderView(listTopView);

        return view;
    }


    @Override
    public void initData() {
        super.initData();
        url = ConstantUtils.BASE_URL + childrenBean.getUrl();
        Log.e("TAG", "url==" + url);
        //设置数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(url)
//                .addParams("username", "hyman")
//                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "请求失败==" + e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("TAG", "请求成功==" + response);
                        //缓存数据
                        processData(response);
                    }


                });
    }

    private void processData(String response) {
        TabDetailPagerBean bean = new Gson().fromJson(response, TabDetailPagerBean.class);

        topnewsBeans = bean.getData().getTopnews();

        viewpager.setAdapter(new MyAdapter());

        tvTitle.setText(topnewsBeans.get(prePosition).getTitle());

        //把之前的移除
        llPointGroup.removeAllViews();
        //添加指示点
        for (int i = 0; i < topnewsBeans.size(); i++) {

            ImageView point = new ImageView(context);
            point.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 8), DensityUtil.dip2px(context, 8));
            point.setLayoutParams(params);

            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
                params.leftMargin = DensityUtil.dip2px(context, 8);
            }

            //添加到线性布局
            llPointGroup.addView(point);

        }


        //设置数据到listview
        newsBeans = bean.getData().getNews();

        newsAdapter = new NewsAdapter();

        lv.setAdapter(newsAdapter);


    }

    class NewsAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return newsBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_tab_detail, null);

                viewHolder = new ViewHolder(convertView);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            TabDetailPagerBean.DataBean.NewsBean bean = newsBeans.get(position);

            viewHolder.tvTime.setText(bean.getPubdate());
            viewHolder.tvListContent.setText(bean.getTitle());
            String url = ConstantUtils.BASE_URL + bean.getListimage();

            Glide.with(context)
                    .load(url)
                    .error(R.drawable.news_pic_default)
                    .placeholder(R.drawable.news_pic_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.ivIcon);


            return convertView;
        }


    }

    static class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_list_content)
        TextView tvListContent;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    class MyAdapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.pic_item_list_default);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            String imageUrl = ConstantUtils.BASE_URL + topnewsBeans.get(position).getTopimage();

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.pic_item_list_default)
                    .error(R.drawable.pic_item_list_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return topnewsBeans.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }

}
