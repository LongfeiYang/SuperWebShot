package com.ylf.imageloader.config;

import com.ylf.imageloader.cache.BitmapCache;
import com.ylf.imageloader.cache.DoubleCache;
import com.ylf.imageloader.cache.MemoryCache;
import com.ylf.imageloader.cache.NoCache;
import com.ylf.imageloader.policy.LoaderPolicy;
import com.ylf.imageloader.policy.ReversePolicy;
import com.ylf.imageloader.policy.SerialPolicy;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public class ImageLoaderConfig {
    //缓存策略
    private BitmapCache bitmapCache = new MemoryCache();
    //加载策略
    private LoaderPolicy loaderPolicy = new ReversePolicy();
    //线程个数
    private int threadCount = Runtime.getRuntime().availableProcessors();
    //图片加载的显示配置
    private DisplayConfig displayConfig = new DisplayConfig();

    public ImageLoaderConfig(){

    }

    /**
     * 建造者模式
     */
    public static class Builder{
        private ImageLoaderConfig config;

        public Builder(){
            config = new ImageLoaderConfig();
        }

        /**
         * 设置缓存策略
         * @param bitmapCache
         * @return
         */
        public Builder setCachePolicy(BitmapCache bitmapCache){
            config.bitmapCache = bitmapCache;
            return this;
        }

        /**
         * 设置加载策略
         * @param loaderPolicy
         * @return
         */
        public Builder setLoaderPolicy(LoaderPolicy loaderPolicy){
            config.loaderPolicy = loaderPolicy;
            return this;
        }
        /**
         * 设置线程数
         * @param count
         * @return
         */
        public Builder setThreadCount(int count){
            config.threadCount = count;
            return this;
        }

        /**
         * 图片加载过程中显示的图片
         * @param resId
         * @return
         */
        public Builder setLoadingImage(int resId){
            config.displayConfig.loadingImage = resId;
            return this;
        }
        public Builder setFaildImage(int resId){
            config.displayConfig.faildImage = resId;
            return this;
        }
        public ImageLoaderConfig build(){
            return config;
        }
    }

    public LoaderPolicy getLoaderPolicy() {
        return loaderPolicy;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

}
