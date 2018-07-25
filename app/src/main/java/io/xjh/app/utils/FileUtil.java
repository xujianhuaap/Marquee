package io.xjh.app.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by xujianhua on 2017/1/19.
 */

public class FileUtil {
        public static File getExternalStorage(){
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                Log.d(FileUtil.class.getName(),
                        "Environment.getExternalStorageDirectory()\t"
                                +Environment.getExternalStorageDirectory().getAbsolutePath());
                return Environment.getExternalStorageDirectory();
            }
            return null;
        }

        public static File getAppExternalStorage(Context context){
            Log.d(FileUtil.class.getName(),
                    "context.getExternalFilesDir\t"
                            +context.getExternalFilesDir(null).getAbsolutePath());
            context.getExternalFilesDir(null);
            return null;
        }

        //
        public static File getFilesDir(Context context){
            Log.d(FileUtil.class.getName(),
                    "context.getFilesDir\t"
                            +context.getFilesDir().getAbsolutePath());
            return context.getFilesDir();
        }
}
