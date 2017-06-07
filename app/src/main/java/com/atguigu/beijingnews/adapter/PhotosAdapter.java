package com.atguigu.beijingnews.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.activity.PicassoSampleActivity;
import com.atguigu.beijingnews.domain.PhotosBean;
import com.atguigu.beijingnews.utils.BitmapCacheUtils;
import com.atguigu.beijingnews.utils.ConstantUtils;
import com.atguigu.beijingnews.utils.NetCacheUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/6.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHodler> {


    private final Context context;

    private BitmapCacheUtils bitmapCacheUtils;

    private List<PhotosBean.DataBean.NewsBean> newsBeans;
    private RecyclerView recyclerview;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NetCacheUtils.SUSSCE:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    int position = msg.arg1;

                    ImageView imageView = (ImageView) recyclerview.findViewWithTag(position);
                    if (imageView != null && bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }

                    break;
                case NetCacheUtils.FAIL:
                    position = msg.arg1;
                    Log.e("TAG", "请求图片失败==" + position);
                    break;
            }


        }
    };


    public PhotosAdapter(Context context, List<PhotosBean.DataBean.NewsBean> newsBeans, RecyclerView recyclerview) {

        this.context = context;
        this.newsBeans = newsBeans;
        this.recyclerview = recyclerview;

        bitmapCacheUtils = new BitmapCacheUtils(handler);
    }

    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.photos_item, null);
        return new MyViewHodler(view);
    }

    @Override
    public void onBindViewHolder(MyViewHodler myViewHodler, final int position) {
        final PhotosBean.DataBean.NewsBean newsBean = newsBeans.get(position);
        myViewHodler.tvPhotoContent.setText(newsBean.getTitle());

        //3.设置点击事件
        String imageUrl = ConstantUtils.BASE_URL + newsBean.getListimage();
//        Glide.with(context)
//                .load(imageUrl)
//                .placeholder(R.drawable.pic_item_list_default)
//                .error(R.drawable.pic_item_list_default)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(myViewHodler.ivPhoto);


        //自定义加载图片
        Bitmap bitmap = bitmapCacheUtils.getBitmap(imageUrl, position);

        myViewHodler.ivPhoto.setTag(position);

        if (bitmap != null) {//来自内存和本地，不包括网络
            myViewHodler.ivPhoto.setImageBitmap(bitmap);
        }


        myViewHodler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = ConstantUtils.BASE_URL + newsBean.getListimage();
                Intent intent = new Intent(context, PicassoSampleActivity.class);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);

            }
        });


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
