package com.atguigu.beijingnews;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.atguigu.beijingnews.fragment.ContentFragment;
import com.atguigu.beijingnews.fragment.LeftMenuFragment;
import com.atguigu.beijingnews.utils.DensityUtil;
import com.atguigu.beijingnews.utils.SPUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import static com.atguigu.beijingnews.detailpager.TabDetailPager.JILU_DIANJI;


public class MainActivity extends SlidingFragmentActivity {

    public static final String LEFT_TAG = "left_tag";
    public static final String MAIN_TAG = "main_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSlidingMenu();


        //初始化fragmet
        initFragment();


    }

    private void initSlidingMenu() {
        setContentView(R.layout.activity_main);

        setBehindContentView(R.layout.left_menu);

        SlidingMenu slidingMenu = getSlidingMenu();

        slidingMenu.setMode(SlidingMenu.LEFT);

        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this, 300));
    }

    private void initFragment() {
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();

        fm.replace(R.id.fm_left, new LeftMenuFragment(), LEFT_TAG);
        fm.replace(R.id.fm_main, new ContentFragment(), MAIN_TAG);

        fm.commit();
    }


    //得到左侧菜单
    public LeftMenuFragment getLeftMenuFragment() {

        //根据tag到对象池中找到对应的fragment
        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(LEFT_TAG);

    }

    public ContentFragment getContentFragment() {
        //根据tag到对象池中找到对应的fragment
        return (ContentFragment) getSupportFragmentManager().findFragmentByTag(MAIN_TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        String clickArr = SPUtils.getString(MainActivity.this, JILU_DIANJI, "");
        clickArr = "";
        SPUtils.saveString(MainActivity.this, JILU_DIANJI, clickArr);

    }
}
