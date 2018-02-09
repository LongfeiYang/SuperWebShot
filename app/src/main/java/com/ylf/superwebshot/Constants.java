package com.ylf.superwebshot;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by 11208_000 on 2018/2/4.
 */

public class Constants {
    public static class Directories{
        public static final String SDCARD = Environment.getExternalStorageDirectory().toString();

        public static final String ROOT = SDCARD + File.separator + "SuperShot";

        public static final String SHOT_WEB_LONG = ROOT + File.separator + "web_long" + File.separator;
        public static boolean makeDirectory(String directory){
            if(TextUtils.isEmpty(directory)){
                return false;
            }
            File webLongFile = new File(directory);
            if(!webLongFile.exists()){
                try{
                    webLongFile.mkdirs();
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        public static String getWebLongPath() throws IOException {
            if(makeDirectory(SHOT_WEB_LONG)){
                File file = new File(SHOT_WEB_LONG, new Date().getTime() + ".png");
                file.createNewFile();
                return file.getAbsolutePath();
            }else{
                return "";
            }
        }

    }
}
