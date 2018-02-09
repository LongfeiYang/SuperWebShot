package com.ylf.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ylf.imageloader.config.DisplayConfig;
import com.ylf.imageloader.config.ImageLoaderConfig;
import com.ylf.imageloader.request.BitmapRequest;
import com.ylf.imageloader.request.RequestQueue;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public class SimpleImageLoader {
    //单例对象
    private static volatile SimpleImageLoader instance;
    //配置
    private ImageLoaderConfig config;
    //请求队列
    private RequestQueue requestQueue;

    private SimpleImageLoader(){

    }

    private SimpleImageLoader(ImageLoaderConfig config){
        this.config = config;
        //初始化请求队列
        requestQueue = new RequestQueue(config.getThreadCount());
        //开始，请求转发线程开始不断从队列中获取请求，进行转发处理
        requestQueue.start();
    }

    /**
     * 第一次初始化
     * @param config
     * @return
     */
    public static SimpleImageLoader getInstance(ImageLoaderConfig config){
        if(instance == null){
            synchronized (SimpleImageLoader.class){
                if(instance == null){
                    instance = new SimpleImageLoader(config);
                }
            }
        }
        return instance;
    }
    public static SimpleImageLoader getInstance(){
        if(instance == null){
            throw new UnsupportedOperationException("getInstance(ImageLoaderConfig config)没有执行过！");
        }
        return instance;
    }

    public void displayImage(ImageView imageView, String uri){
        displayImage(imageView, uri, null);
    }
    public void displayImage(ImageView imageView, String uri, DisplayConfig config){
        displayImage(imageView, uri, config, null);
    }
    public void displayImage(ImageView imageView, String uri, DisplayConfig config, ImageListener listener){
        //生成一个请求，添加到请求队列中
        BitmapRequest bitmapRequest = new BitmapRequest(imageView, uri, config, listener);
        requestQueue.addRequest(bitmapRequest);
    }
    public static interface ImageListener{
        /**
         * 加载完成
         * @param imageView
         * @param bitmap
         * @param uri
         */
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }
    public ImageLoaderConfig getConfig(){
        return config;
    }

}
