package com.example.storyapp.ui.home.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.response.DetailStoryResponse
import com.example.storyapp.response.ListStoryItem
import com.example.storyapp.ui.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private var _story = MutableLiveData<ListStoryItem>()
    var story: LiveData<ListStoryItem> = _story

    fun fetchDetail(id: String){
        val client = ApiConfig.getApiService().fetchDetailStories(id, "Bearer ${MainViewModel.API_KEY}")
        client.enqueue(object: Callback<DetailStoryResponse>{
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                if (response.isSuccessful){
                    _story.value = response.body()!!.story as ListStoryItem
                }
            }

            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {

            }

        })
    }
}