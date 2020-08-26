package com.tiendito.swvlmovies.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlickerServicesTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var flickerApis: FlickerApis

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createWebService(){
        mockWebServer = MockWebServer()
        flickerApis = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlickerApis::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSearchPhotos() {
        enqueueResponse("flicker_photos_response.json")
        runBlocking {
            val orders = flickerApis.searchPhotos(
                method = "flickr.photos.search",
                apiKey = "99a09da7219c20cd6e6dacd82ae76d33",
                format = "json",
                nojsoncallback = "1",
                text = "peace",
                page = "1",
                perPage = "10")

            Assert.assertThat(orders.body()?.photos?.photo?.size, `is`(10))
            val order1 = orders.body()?.photos?.photo?.get(0)
            Assert.assertThat(order1?.server, `is`("65535"))

            val order4 = orders.body()?.photos?.photo?.get(4)
            Assert.assertThat(order4?.owner, `is`("188493262@N07"))

        }

    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

}