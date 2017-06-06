package com.atguigu.beijingnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.domain.PhotosBean;
import com.atguigu.beijingnews.utils.ConstantUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/6.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHodler> {


    private final Context context;

    private List<PhotosBean.DataBean.NewsBean> newsBeans;


    public PhotosAdapter(Context context, List<PhotosBean.DataBean.NewsBean> newsBeans) {

        this.context = context;
        this.newsBeans = newsBeans;
    }

    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.photos_item, null);
        return new MyViewHodler(view);
    }

    @Override
    public void onBindViewHolder(MyViewHodler myViewHodler, int position) {
        PhotosBean.DataBean.NewsBean newsBean = newsBeans.get(position);
        myViewHodler.tvPhotoContent.setText(newsBean.getTitle());

        //3.设置点击事件
        String imageUrl = ConstantUtils.BASE_URL + newsBean.getListimage();
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.pic_item_list_default)
                .error(R.drawable.pic_item_list_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myViewHodler.ivPhoto);

    }

    @Override
    public int getItemCount() {
        return newsBeans.size();
    }

    class MyViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_photo_content)
        TextView tvPhotoContent;

        public MyViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
