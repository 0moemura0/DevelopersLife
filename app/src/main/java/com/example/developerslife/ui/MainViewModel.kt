package com.example.developerslife.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.developerslife.GifTypes
import com.example.developerslife.network.ApiRepository
import com.example.developerslife.network.GetGifRequest


class MainViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    private val gifLiveData = MutableLiveData<GetGifRequest>()
    val gif = Transformations.switchMap(gifLiveData) {
        apiRepository.getGif(it)
    }
    fun setData(type: GifTypes, page: Int){
        gifLiveData.value = GetGifRequest(type.urlName, page)
    }
}