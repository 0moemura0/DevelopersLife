package com.example.developerslife.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val TAG: String = "RetrofitClient"
    private const val url: String = "https://developerslife.ru"
    private var retrofit: Retrofit? = null
    private var client: API? = null
    private val okhttpClient : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()

    fun getClient(): API {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        client = retrofit!!.create(API::class.java)
        return client!!
    }
}