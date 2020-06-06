package com.vantrai.challengew6.activity

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", "7519cb3f829ecd53bd9b7007076dbe23").build()
        val finalRequest = request.newBuilder().url(url).build()
        return chain.proceed(finalRequest)
    }
}