package com.ylf.imageloader.util;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public class ImageViewHelper {
    //默认的图片宽高
    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 200;

    public static int getImageViewWidth(ImageView imageView){
        if(imageView != null){
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            int width = 0;
            if(params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT){
                width = imageView.getWidth();
            }

            if(width < 0 && params != null){
                width = params.width;
            }

            if (width <= 0){
                width = getImageViewFieldValue(imageView, "mMaxWidth");
            }

            return width;
        }
        return DEFAULT_WIDTH;
    }
    public static int getImageViewHeight(ImageView imageView){
        if(imageView != null){
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            int height = 0;
            if(params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT){
                height = imageView.getHeight();
            }

            if(height <= 0 && params != null){
                height = params.height;
            }

            if (height <= 0){
                height = getImageViewFieldValue(imageView, "mMaxHeight");
            }

            return height;
        }
        return DEFAULT_HEIGHT;
    }

    private static int getImageViewFieldValue(ImageView imageView, String fieldName) {
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (int) field.get(imageView);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE){
                return fieldValue;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
