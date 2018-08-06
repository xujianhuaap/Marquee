package io.xjh;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.xjh.app.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class MarqueeUiTest {

    private static final String BASIC_SAMPLE_PACKAGE
            = "io.xjh.margeeview";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;

    @Before
    @Test
    public void startAppFromHome(){
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();
        final String launchPackage = mDevice.getLauncherPackageName();
        assertThat(launchPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launchPackage).depth(0)),LAUNCH_TIMEOUT);

        Context context = InstrumentationRegistry.getContext();
        final Intent intent =
                context.getPackageManager().getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),LAUNCH_TIMEOUT);
    }


    @Test
    public void changeMainViewContent() throws UiObjectNotFoundException {

        UiObject2 textView = mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE,"edit_text"));
        assertThat(textView,notNullValue());
        textView.setText("skull");//只适用于 EditText

        //在 开发者选项 中，将USB模拟点击 允许
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),LAUNCH_TIMEOUT);
        UiObject2 button = mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE,"button"));
        assertThat(button,notNullValue());
        button.click();
//        Log.d("-----",button.getText()+button.getClassName());

    }

}
