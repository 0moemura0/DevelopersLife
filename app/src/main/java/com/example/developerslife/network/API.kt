package com.example.developerslife.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    @GET("/{type}/{page}?json=true")
    fun getGIF(@Path("type" )type: String, @Path("page" ) page:Int): Call<List<GIF>>
}