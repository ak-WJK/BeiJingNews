package com.atguigu.beijingnews.fragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.atguigu.beijingnews.MainActivity;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.base.BaseFragment;
import com.atguigu.beijingnews.base.BasePager;
import com.atguigu.beijingnews.pager.HomePager;
import com.atguigu.beijingnews.pager.NewsPager;
import com.atguigu.beijingnews.pager.SettingPager;
import com.atguigu.beijingnews.view.NoViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/2.
 */

public class ContentFragment extends BaseFragment {
    @BindView(R.id.vp)
    NoViewPager vp;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_news)
    RadioButton rbNews;
    @BindView(R.id.rb_setting)
    RadioButton rbSetting;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    Unbinder unbinder;
    Unbinder unbinder1;
    private MyAdapter adapter;
    private ArrayList<BasePager> pagers;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_content_layout, null);
        ButterKnife.bind(this, view);


        pagers = new ArrayList<>();
        pagers.add(new HomePager(context));
        pagers.add(new NewsPager(context));
        pagers.add(new SettingPager(context));


        return view;
    }

    @Override
    public void initData() {

        adapter = new MyAdapter();
        vp.setAdapter(adapter);

        vp.addOnPageChangeListener(new MyOnPageChangeListener());


        //进入默认选中页面
        pagers.get(0).initData();
        //屏蔽第一次进入时主页面可侧滑出左侧菜单
        isEnableSliding(context, SlidingMenu.TOUCHMODE_NONE);
        rgMain.check(R.id.rb_home);

    }

    public NewsPager getNewsPager() {
        return (NewsPager) pagers.get(1);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //屏蔽viewPager预加载
            pagers.get(position).initData();

            //屏蔽每个页面都可以拖动处左侧菜单
            //得到MainActivity
            if (position == 1) {
                isEnableSliding(context, SlidingMenu.TOUCHMODE_FULLSCREEN);
            } else {
                isEnableSliding((MainActivity) context, SlidingMenu.TOUCHMODE_NONE);
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //是否可滑动处左侧菜单
    private static void isEnableSliding(Context context, int touchmodeFullscreen) {
        MainActivity mainActivity = (MainActivity) context;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        slidingMenu.setTouchModeAbove(touchmodeFullscreen);
    }


    @OnClick({R.id.rb_home, R.id.rb_news, R.id.rb_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                vp.setCurrentItem(0);
                break;
            case R.id.rb_news:
                vp.setCurrentItem(1);
                break;
            case R.id.rb_setting:
                vp.setCurrentItem(2);
                break;
        }
    }

    class MyAdapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = pagers.get(position);
            View rootView = pager.rootView;
//            pager.initData();
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
}
