package com.example.storyapp.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.Event
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.request.RegisterRequest
import com.example.storyapp.response.RegisterResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    fun register(name:String, email:String, password:String){
        _error.value = Event("")
        _loading.value = true

        val request = RegisterRequest(password, name, email)
        val client = ApiConfig.getApiService().register(request)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful){
                    _message.value = Event("200")
                }else{
                    val gson = GsonBuilder().create()
                    var pojo = RegisterResponse()
                    try {
                        pojo = gson.fromJson(response.errorBody()!!.string(), RegisterResponse::class.java)
                        _error.value = Event(pojo.message.toString())
                    } catch (ex: Exception){
                        Log.e("error", ex.localizedMessage.toString())
                    }
                }
                _loading.value = false
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

            }

        })
    }
    init {
        _loading.value = false
    }
}