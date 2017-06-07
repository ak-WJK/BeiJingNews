package com.atguigu.beijingnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/6/7.
 */

public class LocalCacheUtils {
    public void putBitmap2Local(String imageUrl, Bitmap bitmap) {

        try {

            String directory = Environment.getExternalStorageDirectory() + "/beijingnews/";


            String fileName = MD5Encoder.encode(imageUrl);

            File file = new File(fileName, directory);

            File parentFile = file.getParentFile();

            if (!parentFile.exists()) {
                parentFile.mkdirs();

            }

            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Bitmap getBitmap(String imageUrl) {
        try {

            String dir = Environment.getExternalStorageDirectory() + "/beijingnews/";
            //文件名称
            String fileName = MD5Encoder.encode(imageUrl);

            File file = new File(dir, fileName);

            if (file.exists()) {

                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));

                return bitmap;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

