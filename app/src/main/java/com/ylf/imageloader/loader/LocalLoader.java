package com.ylf.imageloader.loader;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.ylf.imageloader.request.BitmapRequest;
import com.ylf.imageloader.util.BitmapDecoder;
import com.ylf.imageloader.util.ImageViewHelper;

import java.io.File;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public class LocalLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        //得到图片的本地路径
        final String path = Uri.parse(request.getImageUri()).getPath();
        File file = new File(path);
        if(!file.exists()){
            return null;
        }
        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            protected Bitmap decoderBitmapWithOptions(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path, options);
            }
        };

        return decoder.decoderBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()),
                ImageViewHelper.getImageViewHeight(request.getImageView()));
    }
}
