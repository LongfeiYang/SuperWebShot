package com.ylf.imageloader.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.ylf.imageloader.config.DisplayConfig;
import com.ylf.imageloader.loader.SimpleImageLoader;
import com.ylf.imageloader.policy.LoaderPolicy;
import com.ylf.imageloader.util.MD5Util;

import java.lang.ref.SoftReference;

/**
 * 图片请求
 * Created by 11208_000 on 2018/2/4.
 */

public class BitmapRequest implements Comparable<BitmapRequest>{
    //加载策略
    private LoaderPolicy loaderPolicy = SimpleImageLoader.getInstance().getConfig().getLoaderPolicy();
    //编号
    private int serialNo;
    //内存不足，回收引用的对象
    private SoftReference<ImageView> imageViewSoftRef;
    //图片路径
    private String imageUri;
    //MD5的图片路径
    private String imageUriMD5;
    //下载完成监听
    private SimpleImageLoader.ImageListener listener;

    private DisplayConfig displayConfig = SimpleImageLoader.getInstance().getConfig().getDisplayConfig();

    public BitmapRequest(ImageView imageView, String imageUri, DisplayConfig displayConfig,
                         SimpleImageLoader.ImageListener listener) {
        this.imageViewSoftRef = new SoftReference<>(imageView);
        //设置可见的Image的Tag，要下载的图片路径
        imageView.setTag(imageUri);
        this.imageUri = imageUri;
        this.imageUriMD5 = MD5Util.toMD5(imageUri);
        this.listener = listener;
        if(displayConfig != null){
            this.displayConfig = displayConfig;
        }
    }

    /**
     * 优先级的确定
     * @param another
     * @return
     */
    @Override
    public int compareTo(@NonNull BitmapRequest another) {
        return loaderPolicy.compare(this, another);
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public ImageView getImageView(){
        return imageViewSoftRef.get();
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUriMD5() {
        return imageUriMD5;
    }

    public void setImageUriMD5(String imageUriMD5) {
        this.imageUriMD5 = imageUriMD5;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

    public SimpleImageLoader.ImageListener getListener() {
        return listener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitmapRequest that = (BitmapRequest) o;

        if (serialNo != that.serialNo) return false;
        return loaderPolicy != null ? loaderPolicy.equals(that.loaderPolicy) : that.loaderPolicy == null;

    }

    @Override
    public int hashCode() {
        int result = loaderPolicy != null ? loaderPolicy.hashCode() : 0;
        result = 31 * result + serialNo;
        return result;
    }


}
