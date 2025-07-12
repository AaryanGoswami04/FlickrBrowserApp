package com.example.flickrbrowser

import android.util.Log
import org.junit.Test
import org.robolectric.RobolectricTestRunner
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class ExampleUnitTest {
    private val mockListener = mock(GetRawData.OnDownloadComplete::class.java)

    @Test
    fun testDoInBackground_validUrl_returnsData() {

        val getRawData = GetRawData(mockListener)
        val url = "https://jsonplaceholder.typicode.com/todos/1"
        val result = getRawData.doInBackground(url)

        assertTrue("Expected non-empty data", result.isNotEmpty())
    }
}