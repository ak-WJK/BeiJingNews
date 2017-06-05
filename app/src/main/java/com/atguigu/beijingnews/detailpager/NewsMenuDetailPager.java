package com.atguigu.beijingnews.detailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.atguigu.beijingnews.MainActivity;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.base.MenuDetailBasePager;
import com.atguigu.beijingnews.domain.NewsCenterBean;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/3.
 */

public class NewsMenuDetailPager extends MenuDetailBasePager {


    private final List<NewsCenterBean.DataBean.ChildrenBean> children;
    @BindView(R.id.indicator)
    TabPageIndicator indicator;
    @BindView(R.id.ib_next)
    ImageButton ibNext;


    //装配页面的集合

    private ArrayList<TabDetailPager> tabDetailPagers;


    @BindView(R.id.viewpager)
    ViewPager viewpager;

    public NewsMenuDetailPager(Context context, List<NewsCenterBean.DataBean.ChildrenBean> children) {
        super(context);
        this.children = children;

//        Log.e("TAG", "childern======" + children.size());
    }

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.pager_news_menu_layout, null);

        ButterKnife.bind(this, view);
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
            }
        });




        return view;
    }

    @Override
    public void initData() {
        super.initData();

        tabDetailPagers = new ArrayList<>();

        for (int i = 0; i < children.size(); i++) {


            tabDetailPagers.add(new TabDetailPager(context, children.get(i)));

        }


        viewpager.setAdapter(new MyPagerAdapter());

        //设置关联
        indicator.setViewPager(viewpager);

        //监听页面的改变
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    //SlidingMenu可以滑动
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                } else {
                    //不可以滑动
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class MyPagerAdapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            //得到每个页面
            TabDetailPager detailPager = tabDetailPagers.get(position);
            //得到每个页面所实现自己的子页面
            View rootView = detailPager.rootView;
            //得到每个子页面所装配的数据
            detailPager.initData();
            //添加到容器中
            container.addView(rootView);

            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        //得到页面的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }


}
