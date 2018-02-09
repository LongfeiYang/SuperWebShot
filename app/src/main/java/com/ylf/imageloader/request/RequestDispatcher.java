package com.ylf.imageloader.request;

import android.util.Log;

import com.ylf.imageloader.loader.Loader;
import com.ylf.imageloader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * 请求转发器
 * 请求转发线程，不断从请求队列获取请求
 *
 * Created by 11208_000 on 2018/2/4.
 */

public class RequestDispatcher extends Thread{
    private static final String TAG = "RequestDispatcher";
    private BlockingQueue<BitmapRequest> requestQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        //非阻塞状态，获取请求处理
        while(!isInterrupted()){
            //从队列中获取优先级最高的请求进行处理
            try{
                BitmapRequest request = requestQueue.take();
                Log.d(TAG, "run: 处理请求" + request.getSerialNo());
                /**
                 * 解析图片地址，获取对象的加载器
                 */
                String schema = parseSchema(request.getImageUri());

                Loader loader = LoaderManager.getInstance().getLoader(schema);
                loader.loadImage(request);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析图片地址，获取schema
     * @param imageUrl
     * @return
     */
    private String parseSchema(String imageUrl) {
        if(imageUrl.contains("://")){
            return imageUrl.split("://")[0];
        }else {
            Log.i(TAG, "不支持的图片地址协议类型！");
        }
        return null;
    }

}
