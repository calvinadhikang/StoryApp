package com.example.storyapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.Event
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.request.LoginRequest
import com.example.storyapp.response.LoginResponse
import com.example.storyapp.response.LoginResult
import com.example.storyapp.response.RegisterResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _error = MutableLiveData<Event<String>>()
    var error: LiveData<Event<String>> = _error

    private val _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    private val _user = MutableLiveData<LoginResult>()
    var user: LiveData<LoginResult> = _user

    fun login(email: String, password: String){
        _error.value = Event("")
        _loading.value = true

        val request = LoginRequest(password, email)
        val client = ApiConfig.getApiService().login(request)
        client.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful){
                    _error.value = Event("berhasil")
                    _user.value = response.body()!!.loginResult as LoginResult

                    API_KEY = response.body()!!.loginResult!!.token!!
                    LOGIN_NAME = response.body()!!.loginResult!!.name!!
                    LOGIN_ID = response.body()!!.loginResult!!.userId!!
                } else {
                    val gson = GsonBuilder().create()
                    var pojo = RegisterResponse()
                    try {
                        pojo = gson.fromJson(response.errorBody()!!.string(), RegisterResponse::class.java)
                        _error.value = Event(response.message())
                    } catch (ex: Exception) {
                        Log.e("error try", ex.message.toString())
                    }
                }
                _loading.value = false
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }
        })
    }

    init {
        _loading.value = false
    }

    companion object {
        var API_KEY = ""
        var LOGIN_NAME = ""
        var LOGIN_ID = ""
    }
}