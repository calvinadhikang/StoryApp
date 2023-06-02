package com.example.storyapp.ui.home.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.response.FetchStoriesResponse
import com.example.storyapp.response.ListStoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel: ViewModel() {

    private val _stories = MutableLiveData<List<ListStoryItem>>()
    val stories: LiveData<List<ListStoryItem>> = _stories

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun fetch(token: String){
        val client = ApiConfig.getApiService().fetchStoriesWithLocation("Bearer $token")
        client.enqueue(object: Callback<FetchStoriesResponse> {
            override fun onResponse(
                call: Call<FetchStoriesResponse>,
                response: Response<FetchStoriesResponse>
            ) {
                if (response.isSuccessful){
                    _stories.value =  (response.body()!!.listStory as List<ListStoryItem>?)!!
                    _text.value = response.body()!!.message.toString()
                }
            }

            override fun onFailure(call: Call<FetchStoriesResponse>, t: Throwable) {
                Log.e("Error Fetch", t.localizedMessage)
            }

        })
    }
}