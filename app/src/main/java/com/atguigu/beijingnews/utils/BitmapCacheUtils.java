package com.atguigu.beijingnews.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

/**
 * Created by Administrator on 2017/6/7.
 */

public class BitmapCacheUtils {
    private NetCacheUtils netCacheUtils;
    private Handler handler;

    private LocalCacheUtils localCacheUtils;


    public BitmapCacheUtils(Handler handler) {
        this.handler = handler;
        localCacheUtils = new LocalCacheUtils();
        this.netCacheUtils = new NetCacheUtils(handler, localCacheUtils);
    }

    public Bitmap getBitmap(String imageUrl, int position) {

        //从内存中获取


        //从本地获取
        if (localCacheUtils != null) {
            Bitmap bitmap = localCacheUtils.getBitmap(imageUrl);
            if (bitmap != null) {
                Log.e("TAG", "图片是从本地获取的哦==" + position);
                return bitmap;
            }
        }


        //从内网络获取

        netCacheUtils.getBitmapFromNet(imageUrl, position);

        return null;
    }
}
