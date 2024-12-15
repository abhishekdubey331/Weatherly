package com.weatherly.core.network

import com.weatherly.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val newUrl = originalUrl.newBuilder().addQueryParameter("key", BuildConfig.API_KEY).build()
        val newRequest = originalRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}
