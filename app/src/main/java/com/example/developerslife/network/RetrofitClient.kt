package com.example.developerslife.network


import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {
    private const val TAG: String = "RetrofitClient"
    private const val url: String = "https://developerslife.ru"
    private var retrofit: Retrofit? = null
    private var client: API? = null

    fun getClient(): API {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        client = retrofit!!.create(API::class.java)
        return client!!
    }
}