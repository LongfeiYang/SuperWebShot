package com.ylf.imageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;

import com.ylf.imageloader.request.BitmapRequest;

/**
 * 空加载器
 * Created by 11208_000 on 2018/2/4.
 */

public class NullLoader extends AbstractLoader {
    private static final String TAG = "NullLoader";
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        Log.e(TAG, "onLoad: 无法加载，不支持的图片协议，无响应的加载器");
        return null;
    }
}
