package com.ylf.imageloader.policy;

import com.ylf.imageloader.request.BitmapRequest;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public class ReversePolicy implements LoaderPolicy {
    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSerialNo() - request1.getSerialNo();
    }
}
