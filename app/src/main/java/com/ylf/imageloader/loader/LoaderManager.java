package com.ylf.imageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public class LoaderManager {
    private static LoaderManager instance = new LoaderManager();

    //缓存所有支持的Loader类型
    private Map<String, Loader> loaderMap = new HashMap<String, Loader>();

    private NullLoader nullLoader = new NullLoader();

    public LoaderManager() {
        register("http", new UrlLoader());
        register("https", new UrlLoader());
        register("file", new LocalLoader());
    }

    public static LoaderManager getInstance(){
        return instance;
    }

    /**
     * 根据特定的协议，获取加载器
     * @param schema
     * @return
     */
    public Loader getLoader(String schema){
        if(loaderMap.containsKey(schema)){
            return loaderMap.get(schema);
        }
        return nullLoader;
    }

    /**
     * 注册加载器
     * @param schema
     * @param loader
     */
    public final void register(String schema, Loader loader){
        loaderMap.put(schema, loader);
    }

}
