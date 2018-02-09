package com.ylf.imageloader.request;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 请求队列
 * Created by 11208_000 on 2018/2/4.
 */

public class RequestQueue {
    private static final String TAG = "RequestQueue";
    /**
     * 阻塞式队列
     * 多线程共享
     * 生产效率和消费效率相差太远
     * 使用优先级队列
     * 优先级高的队列先被消费
     * 每一个产品都有序号
     */
    private BlockingQueue<BitmapRequest> requestQueue = new PriorityBlockingQueue<>();

    /**
     * 转发器的数量
     */
    private int threadCount;

    /**
     * 线程安全的i自增
     * i    能1  不能2
     */
    private AtomicInteger i = new AtomicInteger(0);

    /**
     * 转发器组
     */
    private RequestDispatcher[] dispatchers;

    public RequestQueue(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * 添加请求对象
     * @param request
     */
    public void addRequest(BitmapRequest request){
        //请求队列是否包含该请求
        if(!requestQueue.contains(request)){
            request.setSerialNo(i.incrementAndGet());
            requestQueue.add(request);
            Log.d(TAG, "添加请求，编号：" + request.getSerialNo());
        }else{
            Log.d(TAG, "请求已经存在，编号：" + request.getSerialNo());
        }
    }

    /**
     * 开始请求
     */
    public void start(){
        stop();
        startDispatcher();
    }

    private void startDispatcher() {
        dispatchers = new RequestDispatcher[threadCount];
        //初始化所有的转发器
        for (int j = 0; j < threadCount; j++) {
            RequestDispatcher p = new RequestDispatcher(requestQueue);
            dispatchers[j] = p;
            //启动线程
            dispatchers[j].start();
        }
    }

    /**
     * 停止请求
     */
    public void stop(){
        if (dispatchers != null && dispatchers.length > 1){
            for (int j = 0; j < dispatchers.length; j++) {
                //打断
                dispatchers[j].interrupt();
            }
        }
    }
}
