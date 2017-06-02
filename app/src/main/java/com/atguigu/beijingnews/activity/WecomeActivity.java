package com.atguigu.beijingnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.atguigu.beijingnews.MainActivity;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.utils.SPUtils;

public class WecomeActivity extends AppCompatActivity {
    private LinearLayout wecome_root_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wecome);

        wecome_root_pager = (LinearLayout) findViewById(R.id.wecome_root_pager);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);


        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(2000);


        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(false);

        animationSet.addAnimation(rotateAnimation);

        animationSet.addAnimation(alphaAnimation);

        animationSet.addAnimation(scaleAnimation);

        wecome_root_pager.startAnimation(animationSet);


        animationSet.setAnimationListener(new MyAnimationListener());


    }

    class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {


            boolean isOneStart = SPUtils.getBoolean(WecomeActivity.this, "boolean", false);
            Log.e("TAG", "isOneStart==" + isOneStart);
            if (isOneStart) {

                Intent intent = new Intent(WecomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {

                Intent intent = new Intent(WecomeActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            }


        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
