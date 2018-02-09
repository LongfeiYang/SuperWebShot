package com.ylf.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.ylf.imageloader.request.BitmapRequest;

/**
 * 双重缓存
 * Created by 11208_000 on 2018/2/5.
 */

public class DoubleCache implements BitmapCache{
    //内存缓存
    private MemoryCache memoryCache = new MemoryCache();
    //硬盘缓存
    private DiskCache diskCache;

    public DoubleCache(Context context){
        diskCache = DiskCache.getInstance(context);
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        memoryCache.put(request, bitmap);
        diskCache.put(request, bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        Bitmap bitmap = memoryCache.get(request);
        if (bitmap == null){
            bitmap = diskCache.get(request);
            if(bitmap != null){
                memoryCache.put(request, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void remove(BitmapRequest request) {
        memoryCache.remove(request);
        diskCache.remove(request);
    }

}
