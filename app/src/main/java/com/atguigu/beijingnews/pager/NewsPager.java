package com.atguigu.beijingnews.pager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.beijingnews.MainActivity;
import com.atguigu.beijingnews.base.BasePager;
import com.atguigu.beijingnews.base.MenuDetailBasePager;
import com.atguigu.beijingnews.detailpager.InteractMenuDetailPager;
import com.atguigu.beijingnews.detailpager.NewsMenuDetailPager;
import com.atguigu.beijingnews.detailpager.PhotosMenuDetailPager;
import com.atguigu.beijingnews.detailpager.TopicMenuDetailPager;
import com.atguigu.beijingnews.detailpager.VoteMenuDetailPager;
import com.atguigu.beijingnews.domain.NewsCenterBean;
import com.atguigu.beijingnews.fragment.LeftMenuFragment;
import com.atguigu.beijingnews.utils.ConstantUtils;
import com.atguigu.beijingnews.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/6/2.
 */

public class NewsPager extends BasePager {


    private List<NewsCenterBean.DataBean> datas;

    private ArrayList<MenuDetailBasePager> basePagers;

    public NewsPager(Context context) {
        super(context);

    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        super.initData();

        ib_menu.setVisibility(View.VISIBLE);

        tv_title.setText("新闻");

        TextView textView = new TextView(context);
        textView.setText("新闻页面");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.BLACK);

        fl_content.addView(textView);
        //获取数据
        String saveJson = SPUtils.getString(context, "saveJson", "");
        //解析数据
        if (!TextUtils.isEmpty(saveJson)) {
            paresJson(saveJson);
        }


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
                        //缓存数据
                        SPUtils.saveString(context, "saveJson", response);
                        processData(response);
                    }

                });

    }

    private void processData(String json) {

//        NewsCenterBean newsCenterBean = new Gson().fromJson(json, NewsCenterBean.class);


        NewsCenterBean newsCenterBean = paresJson(json);

//        Log.e("TAG", "解析成功了哦==" + newsCenterBean.getData().get(0).getChildren().get(0).getTitle());

        datas = newsCenterBean.getData();
        MainActivity mainActivity = (MainActivity) context;
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        leftMenuFragment.setData(datas);

        //实例化详情页面
        basePagers = new ArrayList<>();
        basePagers.add(new NewsMenuDetailPager(context, datas.get(0).getChildren()));
        basePagers.add(new TopicMenuDetailPager(context , datas.get(0).getChildren()));
        basePagers.add(new PhotosMenuDetailPager(context, datas.get(2)));
        basePagers.add(new InteractMenuDetailPager(context));
        basePagers.add(new VoteMenuDetailPager(context));

        swichPager(0);

    }


    @SuppressLint("WrongConstant")
    public void swichPager(final int position) {

        //设置标题
        tv_title.setText(datas.get(position).getTitle());

        final MenuDetailBasePager pager = basePagers.get(position);//得到每一个页面
        View rootView = pager.rootView;

        fl_content.removeAllViews();

        fl_content.addView(rootView);

        pager.initData();


        //显示图片页面的按钮
        if (position == 2) {
            ib_list_andgrid.setVisibility(View.VISIBLE);

            ib_list_andgrid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotosMenuDetailPager photosPager = (PhotosMenuDetailPager) basePagers.get(2);
                    photosPager.setSwitchPager(ib_list_andgrid);
                }
            });

        } else {
            ib_list_andgrid.setVisibility(View.GONE);
        }


    }

    private NewsCenterBean paresJson(String json) {

        NewsCenterBean newsCenterBean = new NewsCenterBean();

        try {

            JSONObject jsonObject = new JSONObject(json);

            datas = new ArrayList<>();
            newsCenterBean.setData(datas);

            int retcode = jsonObject.optInt("retcode");

            newsCenterBean.setRetcode(retcode);

            JSONArray data = jsonObject.optJSONArray("data");


            for (int i = 0; i < data.length(); i++) {

                NewsCenterBean.DataBean dataBean = new NewsCenterBean.DataBean();


                JSONObject jsonObject1 = data.optJSONObject(i);

                int id = jsonObject1.optInt("id");
                dataBean.setId(id);

                String title = jsonObject1.optString("title");
                dataBean.setTitle(title);

                String url = jsonObject1.optString("url");
                dataBean.setUrl(url);

                int type = jsonObject1.optInt("type");
                dataBean.setType(type);


                JSONArray children = jsonObject1.optJSONArray("children");

                if (children != null) {

                    List<NewsCenterBean.DataBean.ChildrenBean> childrenBeans = new ArrayList<>();

                    dataBean.setChildren(childrenBeans);

                    for (int i1 = 0; i1 < children.length(); i1++) {

                        JSONObject jsonObject2 = children.optJSONObject(i1);

                        NewsCenterBean.DataBean.ChildrenBean childrenBean = new NewsCenterBean.DataBean.ChildrenBean();


                        childrenBean.setId(jsonObject2.optInt("id"));
                        childrenBean.setTitle(jsonObject2.optString("title"));
                        childrenBean.setType(jsonObject2.optInt("type"));
                        childrenBean.setUrl(jsonObject2.optString("url"));

                        childrenBeans.add(childrenBean);
                    }
                }

                datas.add(dataBean);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return newsCenterBean;
    }


}
