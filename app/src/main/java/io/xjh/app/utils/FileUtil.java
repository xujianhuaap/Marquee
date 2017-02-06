package io.xjh.app.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by xujianhua on 2017/1/19.
 */

public class FileUtil {
        public static File getExternalStorage(){
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                return Environment.getExternalStorageDirectory();
            }
            return null;
        }

        public static File getInternalStorage(Context context){
            context.getExternalFilesDir(null);
            return null;
        }

        public static File getCacheFile(){
            return getCacheFile();
        }
}
