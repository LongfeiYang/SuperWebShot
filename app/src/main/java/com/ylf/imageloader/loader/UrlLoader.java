package com.ylf.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ylf.imageloader.request.BitmapRequest;
import com.ylf.imageloader.util.BitmapDecoder;
import com.ylf.imageloader.util.ImageViewHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public class UrlLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) new URL(request.getImageUri()).openConnection();
            //mark与reset支持重复使用流，但是InputStream不支持
            inputStream = new BufferedInputStream(connection.getInputStream());

            //标记
            inputStream.mark(inputStream.available());
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            final InputStream finalInputStream = inputStream;
            //匿名内部类
            BitmapDecoder bitmapDecoder = new BitmapDecoder(){
                @Override
                protected Bitmap decoderBitmapWithOptions(BitmapFactory.Options options) {
                    Bitmap bitmap = BitmapFactory.decodeStream(finalInputStream, null, options);
                    if(options.inJustDecodeBounds){//对流执行第一次读操作
                        try {
                            //第一次读图片宽高信息，读完之后必须为第二次读整个图片进行准备，将流重置
                            finalInputStream.reset();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try{
                            finalInputStream.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    return bitmap;
                }
            };
            return bitmapDecoder.decoderBitmap(ImageViewHelper.getImageViewWidth(request.getImageView()),
                    ImageViewHelper.getImageViewHeight(request.getImageView()));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }
            try {
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
