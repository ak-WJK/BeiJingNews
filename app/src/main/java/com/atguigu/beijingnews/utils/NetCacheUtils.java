package com.atguigu.beijingnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/6/7.
 */

public class NetCacheUtils {


    public static final int SUSSCE = 1;
    public static final int FAIL = 2;
    private String imageUrl;
    private Handler handler;
    private LocalCacheUtils localCacheUtils;
    private final ExecutorService executorService;

    public NetCacheUtils(Handler handler, LocalCacheUtils localCacheUtils) {
        this.handler = handler;
        this.localCacheUtils = localCacheUtils;
        executorService = Executors.newFixedThreadPool(10);
    }

    public void getBitmapFromNet(String imageUrl, int position) {


//        new Thread(new MyRunnable(imageUrl)).start();
        //线程池
        executorService.execute(new MyRunnable(imageUrl, position));

    }

    class MyRunnable implements Runnable {


        private String imageUrl;
        private int position;

        public MyRunnable(String imageUrl, int position) {

            this.imageUrl = imageUrl;
            this.position = position;
        }

        @Override
        public void run() {
            try {

                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.connect();

                if (connection.getResponseCode() == 200) {

                    InputStream is = connection.getInputStream();

                    Bitmap bitmap = BitmapFactory.decodeStream(is);


                    Message msg = Message.obtain();
                    msg.obj = bitmap;
                    msg.arg1 = position;
                    msg.what = SUSSCE;
                    handler.sendMessage(msg);

                    //在内存中保存一份


                    //在本地存储保存一份
                    localCacheUtils.putBitmap2Local(imageUrl, bitmap);


                }


            } catch (Exception e) {
                e.printStackTrace();

                Message msg = Message.obtain();
                msg.what = FAIL;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }


        }
    }
}
