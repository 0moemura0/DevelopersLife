package com.example.developerslife.network

import com.google.gson.annotations.SerializedName

data class GifResult(@SerializedName("result") val result: List<GIF>)