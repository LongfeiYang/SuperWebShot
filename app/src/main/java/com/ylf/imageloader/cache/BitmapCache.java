package com.ylf.imageloader.cache;

import android.graphics.Bitmap;

import com.ylf.imageloader.request.BitmapRequest;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public interface BitmapCache {
    /**
     * 缓存Bitmap
     * @param request
     * @param bitmap
     */
    void put(BitmapRequest request, Bitmap bitmap);

    /**
     * 获取缓存的Bitmap
     * @param request
     * @return
     */
    Bitmap get(BitmapRequest request);

    /**
     * 移除缓存
     * @param request
     */
    void remove(BitmapRequest request);
}
