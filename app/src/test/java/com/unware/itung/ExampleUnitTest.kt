package com.unware.itung

import com.unware.itung.utilities.NumberUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun priceFormatForIndonesia20rb() {
        val actual = NumberUtils.getInstance().getFormattedNumber(20000.00)
        val expected = "20.000"
        assertEquals("Price format for 20.000 wrong.", expected, actual)
    }

    @Test
    fun priceFormatForIndonesia_0() {
        val actual = NumberUtils.getInstance().getFormattedNumber(0.0)
        val expected = "0"
        assertEquals("Price format for 0 wrong.", expected, actual)
    }

    @Test
    fun priceFormatForIndonesia_999999() {
        val actual = NumberUtils.getInstance().getFormattedNumber(999999.0)
        val expected = "999.999"
        assertEquals("Price format for 999.999 wrong.", expected, actual)
    }
}
