package com.ylf.imageloader.loader;

import com.ylf.imageloader.request.BitmapRequest;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public interface Loader {
    /**
     * 加载图片
     * @param request
     */
    void loadImage(BitmapRequest request);
}
