package com.weatherly.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.test.core.app.ApplicationProvider
import com.weatherly.domain.model.WeatherInfo
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class WeatherLocalDataSourceTest {

    private val testDispatcher = StandardTestDispatcher()
    private val appContext = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var weatherLocalDataSource: WeatherLocalDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dataStore = PreferenceDataStoreFactory.create(
            scope = TestScope(testDispatcher),
            produceFile = { File.createTempFile("test_prefs", ".preferences_pb") }
        )
        weatherLocalDataSource = WeatherLocalDataSource(appContext)
    }

    @Test
    fun `Given cached weather info When getWeatherData is called Then it returns the cached data`() =
        runTest(testDispatcher) {
            // Given
            weatherLocalDataSource.saveWeatherData(weatherInfo)

            // When
            val result = weatherLocalDataSource.getWeatherData()

            // Then
            assertEquals(weatherInfo, result)
        }

    @Test
    fun `Given no data in DataStore When getWeatherData is called Then it returns null`() =
        runTest(testDispatcher) {
            // When
            val result = weatherLocalDataSource.getWeatherData()

            // Then
            assertNull(result)
        }

    companion object {
        private val weatherInfo = WeatherInfo(
            cityName = "Tokyo",
            temperature = 22.0,
            conditionText = "Clear Sky",
            conditionIconUrl = "icon_url",
            humidity = 50,
            uvIndex = 4.0,
            feelsLike = 23.0
        )
    }
}
