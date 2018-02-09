package com.ylf.imageloader.cache;

import android.graphics.Bitmap;

import com.ylf.imageloader.request.BitmapRequest;

/**
 * Created by 11208_000 on 2018/2/5.
 */

public class NoCache implements BitmapCache {
    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {

    }

    @Override
    public Bitmap get(BitmapRequest request) {
        return null;
    }

    @Override
    public void remove(BitmapRequest request) {

    }
}
