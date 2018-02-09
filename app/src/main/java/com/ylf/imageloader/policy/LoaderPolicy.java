package com.ylf.imageloader.policy;

import com.ylf.imageloader.request.BitmapRequest;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public interface LoaderPolicy {
    /**
     * 两个BitmapRequest进行优先级排序
     * @param request1
     * @param request2
     * @return
     */
    int compare(BitmapRequest request1, BitmapRequest request2);
}
