package com.ylf.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ylf.imageloader.cache.BitmapCache;
import com.ylf.imageloader.config.DisplayConfig;
import com.ylf.imageloader.request.BitmapRequest;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public abstract class AbstractLoader implements Loader {
    private BitmapCache bitmapCache = SimpleImageLoader.getInstance().getConfig().getBitmapCache();
    private DisplayConfig displayConfig = SimpleImageLoader.getInstance().getConfig().getDisplayConfig();



    @Override
    public void loadImage(BitmapRequest request) {
        //从缓存获取Bitmap
        Bitmap bitmap = bitmapCache.get(request);
        if(bitmap == null){
            //显示默认加载图片
            showLoadingImage(request);
            //开始真正加载图片
            bitmap = onLoad(request);
            //缓存图片
            cacheBitmap(request, bitmap);
        }
        deliveryToUIThread(request, bitmap);
    }

    /**
     * 加载前显示的图片
     * @param request
     */
    private void showLoadingImage(BitmapRequest request) {
        if(hasLoadingPlaceHolder()){
            final ImageView imageView = request.getImageView();
            if(imageView != null){
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(displayConfig.loadingImage);
                    }
                });
            }
        }
    }

    private boolean hasLoadingPlaceHolder() {
        return displayConfig != null && displayConfig.loadingImage > 0;
    }

    //抽象加载策略，因为加载网络图片和本地图片有差异
    protected abstract Bitmap onLoad(BitmapRequest request);

    /**
     * 缓存图片
     * @param request
     * @param bitmap
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap){
        if(request != null && bitmap != null){
            synchronized (AbstractLoader.class){
                bitmapCache.put(request, bitmap);
            }
        }
    }

    protected void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap){
        ImageView imageView = request.getImageView();
        if(imageView != null){
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request, bitmap);
                }
            });
        }
    }

    private void updateImageView(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        //加载正常，防止图片错位
        if(bitmap != null && imageView.getTag().equals(request.getImageUri())){
            imageView.setImageBitmap(bitmap);
        }
        //有可能加载失败
        if (bitmap == null && request.getDisplayConfig() != null && request.getDisplayConfig().faildImage > 0){
            imageView.setImageResource(request.getDisplayConfig().faildImage);
        }
        //监听
        //回调，给圆角图片或者特殊图片进行扩展
        if (request.getListener() != null){
            request.getListener().onComplete(imageView, bitmap, request.getImageUri());
        }
    }



}
