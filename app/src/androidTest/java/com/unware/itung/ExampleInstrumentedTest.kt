package com.unware.itung

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun tapButton1() {
        onView(ViewMatchers.withId(R.id.btn_fragment_home_btn1)).perform(ViewActions.click())
    }

    @Test
    fun tapButton2() {
        onView(ViewMatchers.withId(R.id.btn_fragment_home_btn2)).perform(ViewActions.click())
    }

    @Test
    fun tapAllButton() {
        val button3To9Ids = listOf(
            R.id.btn_fragment_home_btn1,
            R.id.btn_fragment_home_btn2,
            R.id.btn_fragment_home_btn3,
            R.id.btn_fragment_home_btn4,
            R.id.btn_fragment_home_btn5,
            R.id.btn_fragment_home_btn6,
            R.id.btn_fragment_home_btn7,
            R.id.btn_fragment_home_btn8,
            R.id.btn_fragment_home_btn9,
            R.id.btn_fragment_home_btn0,
            R.id.btn_fragment_home_btn000,
            R.id.btn_fragment_home_btn_del)
        for (btnId in button3To9Ids) {
            onView(ViewMatchers.withId(btnId)).perform(ViewActions.click())
            sleep(5_00)
        }
    }

}
