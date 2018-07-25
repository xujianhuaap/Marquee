package io.xjh.app.utils;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;

import io.xjh.app.MainActivity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by xujianhua on 7/20/18.
 */
public class FileUtilTest {
    @Mock
    Context mMockContext;

    @Before
    public void initMock(){
        mMockContext = mock(MainActivity.class);
    }
    @Test
    public void getExternalStorage() throws Exception {
    }

    @Test
    public void getAppExternalStorage() throws Exception {
        File file = new File("/storage/emulated/0");
        when(mMockContext.getExternalFilesDir(null)).thenReturn(file);
//        Log.d("-------------",FileUtil.getAppExternalStorage(mMockContext).getAbsolutePath());
        assertNotNull(FileUtil.getAppExternalStorage(mMockContext));
        assertTrue(FileUtil.getAppExternalStorage(mMockContext).getAbsolutePath().contains("/storage/emulated/0"));
    }

    @Test
    public void getFilesDir() throws Exception {
    }

}