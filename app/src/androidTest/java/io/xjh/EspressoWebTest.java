package io.xjh;

import android.content.Intent;
import android.support.test.espresso.web.webdriver.DriverAtoms;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.xjh.app.WebViewActivity;

import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.clearElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by xujianhua on 8/8/18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoWebTest {

    private static  String INPUT_STR = "xujianhua";

    private ActivityTestRule<WebViewActivity> mRule
            = new ActivityTestRule<WebViewActivity>(WebViewActivity.class){
        @Override
        protected void afterActivityLaunched() {
            super.afterActivityLaunched();
            onWebView().forceJavascriptEnabled();
        }
    };

    @Test
    public void changeText(){
        mRule.launchActivity(withWebFormIntent());
        onWebView().withElement(findElement(Locator.ID,"text_input"))
                .perform(clearElement())
                .perform(DriverAtoms.webKeys(INPUT_STR))
                .withElement(findElement(Locator.ID,"changeTextBtn"))
                .perform(webClick())
                .withElement(findElement(Locator.ID,"message"))
                .check(webMatches(getText(),containsString(INPUT_STR)));
    }


    @Test
    public void submitText(){
        mRule.launchActivity(withWebFormIntent());
        onWebView().withElement(findElement(Locator.ID,"text_input"))
                .perform(clearElement())
                .perform(DriverAtoms.webKeys(INPUT_STR))
                .withElement(findElement(Locator.ID,"submitBtn"))
                .perform(webClick())
                .withElement(findElement(Locator.ID,"response"))
                .check(webMatches(getText(),containsString(INPUT_STR)));
    }
    /**
     * @return start {@link Intent} for the simple web form URL.
     */
    private static Intent withWebFormIntent() {
        Intent basicFormIntent = new Intent();
        basicFormIntent.putExtra(WebViewActivity.KEY_URL_TO_LOAD, WebViewActivity.WEB_FORM_URL);
        return basicFormIntent;
    }

}
