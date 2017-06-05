package com.atguigu.beijingnews.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.beijingnews.MainActivity;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.base.BaseFragment;
import com.atguigu.beijingnews.domain.NewsCenterBean;
import com.atguigu.beijingnews.pager.NewsPager;

import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class LeftMenuFragment extends BaseFragment {


    private List<NewsCenterBean.DataBean> data;
    private ListView listView;
    private int prePosition;
    private LeftMenuAdapter adapter;

    @Override
    public View initView() {

        listView = new ListView(context);
        listView.setPadding(0, 40, 0, 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                prePosition = position;
                adapter.notifyDataSetChanged();//导致 getCount 和 getView重新执行

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();

                switchPager(prePosition);




            }
        });

        return listView;

    }


    @Override
    public void initData() {


    }

    public void setData(List<NewsCenterBean.DataBean> data) {
        this.data = data;


//            NewsCenterBean.DataBean dataBean = data.get(i);
//            String title = dataBean.getTitle();
//
//            Log.e("TAG", "title" + title);

        adapter = new LeftMenuAdapter();

        listView.setAdapter(adapter);

//        switchPager(prePosition);

    }

    private static void switchPager(int prePosition) {
        //得到MainActivi
        MainActivity mainActivity = (MainActivity) context;
        //得到ContentFragment
        ContentFragment contentFragment = mainActivity.getContentFragment();

        //得到NewsPager
        NewsPager newsPager = contentFragment.getNewsPager();

        //切换
        newsPager.swichPager(prePosition);
    }

    class LeftMenuAdapter extends BaseAdapter {

//
//        private List<NewsCenterBean.DataBean> data;
//
//        public LeftMenuAdapter(List<NewsCenterBean.DataBean> data) {
//
//            this.data = data;
//        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(context, R.layout.item_leftmenu, null);

            if (prePosition == position) {
                //高亮
                textView.setEnabled(true);
            } else {
                //默认
                textView.setEnabled(false);
            }

            NewsCenterBean.DataBean dataBean = data.get(position);
            String title = dataBean.getTitle();
            textView.setText(title);

            return textView;
        }
    }
}
