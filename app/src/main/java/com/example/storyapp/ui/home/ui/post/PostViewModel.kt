package com.example.storyapp.ui.home.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.response.RegisterResponse
import com.example.storyapp.ui.main.MainViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Post Your Photo To Stories"
    }
    val text: LiveData<String> = _text

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success


    fun post(file: MultipartBody.Part, description: RequestBody){
        _loading.value = true

        val client = ApiConfig.getApiService().postStories(file, description, "Bearer ${MainViewModel.API_KEY}")
        client.enqueue(object: Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful){
                    _success.value = true
                }
                _loading.value = false
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

            }

        })
    }

    init {
        _loading.value = false
        _success.value = false
    }
}