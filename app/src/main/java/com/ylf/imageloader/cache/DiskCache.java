package com.ylf.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.ylf.imageloader.cache.disk.DiskLruCache;
import com.ylf.imageloader.cache.disk.Util;
import com.ylf.imageloader.request.BitmapRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 硬盘缓存
 * Created by 11208_000 on 2018/2/4.
 */

public class DiskCache implements BitmapCache {
    private static final String TAG = "DiskCache";

    private static DiskCache diskCache;

    private String cacheDir = "Image";

    private static final int MB = 1024 * 1024;

    //jackwharton的作品
    private static DiskLruCache diskLruCache;

    public static DiskCache getInstance(Context context){
        if(diskCache == null){
            synchronized (DiskCache.class){
                if (diskCache == null){
                    diskCache = new DiskCache(context);
                }
            }
        }
        return diskCache;
    }

    public DiskCache(Context context) {
        initDiskCache(context);
    }

    private void initDiskCache(Context context){
        //得到缓存的目录，android/data/data/包名/Image
        File directory = getDiskCacheDir(cacheDir, context);
        if(!directory.exists()){
            directory.mkdirs();
        }
        try {
            diskLruCache = DiskLruCache.open(directory, 1, 1, 50*MB, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存路径
     * @param cacheDir
     * @param context
     * @return
     */
    private File getDiskCacheDir(String cacheDir, Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            cachePath = context.getExternalCacheDir().getPath();//外部存储
        }else{
            cachePath = context.getCacheDir().getPath();//内部存储
        }
        return new File(cachePath, cacheDir);
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        OutputStream os = null;
        try {
            editor = diskLruCache.edit(request.getImageUriMD5());
            os = editor.newOutputStream(0);
            if(persistBitmap2Disk(bitmap, os)){
                editor.commit();
            }else {
                editor.abort();
            }
            diskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Util.closeQuietly(os);
        }
    }

    /**
     * 持久化Bitmap对象到Disk
     * @param bitmap
     * @param os
     * @return
     */
    private boolean persistBitmap2Disk(Bitmap bitmap, OutputStream os) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(os);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            Util.closeQuietly(bos);
        }
        return true;
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        InputStream is = null;
        try {//快照
            DiskLruCache.Snapshot snapshot = diskLruCache.get(request.getImageUriMD5());
            if(snapshot != null){
                is = snapshot.getInputStream(0);
                Log.d(TAG, "get from DiskCache: " + request.getImageUri());
                return BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Util.closeQuietly(is);
        }
        return null;
    }

    @Override
    public void remove(BitmapRequest request) {
        try {
            diskLruCache.remove(request.getImageUriMD5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
