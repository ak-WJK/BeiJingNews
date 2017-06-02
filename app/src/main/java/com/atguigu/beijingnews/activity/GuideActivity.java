package com.atguigu.beijingnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.atguigu.beijingnews.MainActivity;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.utils.DensityUtil;
import com.atguigu.beijingnews.utils.SPUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {


    int[] icon = {
            R.drawable.guide_1,
            R.drawable.guide_2,
            R.drawable.guide_3
    };

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.btn_guide_start)
    Button btnGuideStart;
    @BindView(R.id.ll_point_group)
    LinearLayout llPointGroup;
    @BindView(R.id.iv_red_point)
    ImageView ivRedPoint;
    private ArrayList<ImageView> imageViews;
    private int leftMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);


        initData();
//计算两个点之间的距离
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {


            @Override
            public void onGlobalLayout() {


                //取消监听
                ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // 间距 = 第1个点距离左边的距离 - 第0个点距离左边的距离
                leftMargin = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();

            }
        });

    }

    private void initData() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < icon.length; i++) {
            ImageView imageView = new ImageView(this);

            imageView.setBackgroundResource(icon[i]);

            imageViews.add(imageView);


            //添加三个灰色的点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.guide_point_normal);

            LinearLayout.LayoutParams perams = new LinearLayout.LayoutParams(DensityUtil.dip2px(GuideActivity.this, 10), DensityUtil.dip2px(GuideActivity.this, 10));
            //设置点的距离
            point.setLayoutParams(perams);
            if (i != 0) {
                perams.leftMargin = DensityUtil.dip2px(GuideActivity.this, 10);
            }


            //填加到线性布局
            llPointGroup.addView(point);


        }

        MyPagerAdapter pagerAdapter = new MyPagerAdapter();

        vp.setAdapter(pagerAdapter);

        vp.addOnPageChangeListener(new MyOnPageChangeListener());

    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            //红点移动的距离： 间距 = ViewPager滑动的百分比
            //红点移动的距离 = 间距*ViewPager滑动的百分比
            float left = leftMargin * (positionOffset + position);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
            params.leftMargin = (int) left;
            ivRedPoint.setLayoutParams(params);


        }

        @Override
        public void onPageSelected(int position) {

            if (position == imageViews.size() - 1) {
                btnGuideStart.setVisibility(View.VISIBLE);
            } else {
                btnGuideStart.setVisibility(View.GONE);
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return icon.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);

            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    @OnClick({R.id.btn_guide_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_guide_start:

                SPUtils.saveBoolean(this, "boolean", true);

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
