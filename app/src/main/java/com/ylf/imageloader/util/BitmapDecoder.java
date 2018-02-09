package com.ylf.imageloader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public abstract class BitmapDecoder {
    protected abstract Bitmap decoderBitmapWithOptions(BitmapFactory.Options options);

    public Bitmap decoderBitmap(int realWidth, int realHeight){
        //初始化Options
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只需读取图片信息，无需将整张图片加载到内存
        options.inJustDecodeBounds = true;
        decoderBitmapWithOptions(options);
        //计算图片缩放比例
        calculateSampleSizeWithOptions(options, realWidth, realHeight);

        options.inJustDecodeBounds = false;

        //每个像素占用2个字节
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //当系统内存不足可以回收Bitmap
        options.inPurgeable = true;
        options.inInputShareable = true;

        return decoderBitmapWithOptions(options);
    }

    /**
     * 计算图片的缩放比例
     * @param options
     * @param reqWidth
     * @param reqHeight
     */
    private void calculateSampleSizeWithOptions(BitmapFactory.Options options, int reqWidth, int reqHeight){
        //图片的原始宽高
        int width = options.outWidth;
        int height = options.outHeight;

        int sampleSize = 1;

        if(width > reqWidth || height > reqHeight){
            //计算宽高的缩放比例
            int heightRadio = Math.round((float)height / (float) reqHeight);
            int widthRadio = Math.round((float)width / (float) reqWidth);

            sampleSize = Math.max(heightRadio, widthRadio);
        }
        //图片的宽高变为原来的 1/sampleSize
        options.inSampleSize = sampleSize;
    }

}
