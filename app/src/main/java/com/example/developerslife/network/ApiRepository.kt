package com.example.developerslife.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Response

class ApiRepository(private val apiService: API) {

    private fun <T> Call<T>.enqueue(callback: Callback<T>.() -> Unit) {
        Callback<T>().let {
            callback(it)
            enqueue(it)
        }
    }

    private class Callback<T> : retrofit2.Callback<T> {

        private var onResponse: ((Response<T>) -> Unit)? = null
        private var onError: ((Response<T>?, t: Throwable?) -> Unit)? = null

        fun success(onResponse: (Response<T>) -> Unit) {
            this.onResponse = onResponse
        }

        fun error(onError: (Response<T>?, t: Throwable?) -> Unit) {
            this.onError = onError
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onError?.invoke(null, t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                onResponse?.invoke(response)
            } else {
                onError?.invoke(response, null)
            }
        }
    }

    fun getGif(request: GetGifRequest): LiveData<Resource<GIF>> {
        val resource = MutableLiveData<Resource<GIF>>(Resource.Loading())
        apiService.getGIF(request.type, request.page).enqueue {
            success {
                resource.value = it.body()?.result?.get(0)?.let { it1 -> Resource.Success(it1) }
                    ?: Resource.Error(
                        it.code(),
                        it.message()
                    )
            }
            error { response, _ ->
                resource.value =
                    Resource.Error(response?.code(), response?.message().toString())
            }
        }
        return resource

    }
}