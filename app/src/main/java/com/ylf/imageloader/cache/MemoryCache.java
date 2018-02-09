package com.ylf.imageloader.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.ylf.imageloader.request.BitmapRequest;

/**
 * 内存缓存
 * Created by 11208_000 on 2018/2/4.
 */

public class MemoryCache implements BitmapCache {
    private static final String TAG = "MemoryCache";

    private LruCache<String, Bitmap> lruCache ;

    public MemoryCache(){
        //缓存的最大值，可用内存的八分之一
        int masSize = (int) (Runtime.getRuntime().freeMemory() / 1024/8);//多少M
        lruCache = new LruCache<String, Bitmap>(masSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //一个Bitmap的大小
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }
    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        lruCache.put(request.getImageUriMD5(), bitmap);
        Log.i(TAG, "put in MemoryCache: " + request.getImageUri());
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        Log.i(TAG, "get from MemoryCache: " + request.getImageUri());
        return lruCache.get(request.getImageUriMD5());
    }

    @Override
    public void remove(BitmapRequest request) {
        lruCache.remove(request.getImageUriMD5());
        Log.i(TAG, "remove in MemoryCache: " + request.getImageUri());
    }
}
