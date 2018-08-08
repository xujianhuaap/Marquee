package io.xjh;

import android.content.Intent;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import io.xjh.app.ListActivity;
import io.xjh.app.R;
import io.xjh.app.adapter.CityAdapter;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.xjh.app.adapter.CityAdapter.KEY_CITY_NUM;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.*;

/**
 * Created by xujianhua on 8/8/18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ListActivityTest {
    ActivityTestRule<ListActivity> mRule = new ActivityTestRule(ListActivity.class);

    @Test
    public void clickItem(){
        Intent intent = new Intent();
        intent.putExtra(ListActivity.EXTRA_CITY,"zhengzhou");
        mRule.launchActivity(intent);

        onData(allOf(
                is(instanceOf(Map.class)),
                hasEntry(equalTo(KEY_CITY_NUM), is("1"))
        )).perform(click());
    }

    @Test
    public void clickItemWithContentItem(){
        Intent intent = new Intent();
        intent.putExtra(ListActivity.EXTRA_CITY,"zhengzhou");
        mRule.launchActivity(intent);
        onData(withContentItem("2"))
                .onChildView(withId(R.id.tv_city))
                .check(matches(withText("2")));
    }

    private Matcher withContentItem(final String expectText){
        final Matcher<String> itemTextMatcher = is(expectText);
        return new BoundedMatcher<Object, Map>(Map.class) {
            @Override
            public boolean matchesSafely(Map map) {
                return hasEntry(equalTo(KEY_CITY_NUM), itemTextMatcher).matches(map);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("city");
                itemTextMatcher.describeTo(description);
            }
        };
    }
}