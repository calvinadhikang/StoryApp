package com.example.storyapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.UserPreference
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.response.LoginResult
import com.example.storyapp.ui.home.HomeActivity
import com.example.storyapp.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPreference()

        mainViewModel.error.observe(this){
            val result = it.getContentIfNotHandled()
            if (result == "berhasil"){
                toHomePage()
            }else{
                binding.tvLoginError.text = it.getContentIfNotHandled()
            }
        }

        mainViewModel.user.observe(this) {
            savePreference(it)
        }

        mainViewModel.loading.observe(this){
            binding.loginLoading.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }

        binding.btnToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            if (password.length < 8){
                Toast.makeText(this, "Login minimal 8 karakter", Toast.LENGTH_SHORT).show()
            }else{
                mainViewModel.login(email, password)
            }
        }
    }

    private fun toHomePage(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun getPreference(){
        val pref = UserPreference(this)
        val user = pref.getUser()

        Log.e("pref", user.toString())

        if (user.token != ""){
            MainViewModel.API_KEY = user.token!!
            MainViewModel.LOGIN_ID = user.userId!!
            MainViewModel.LOGIN_NAME = user.name!!
            toHomePage()
        }
    }
    private fun savePreference(user: LoginResult){
        val pref = UserPreference(this)
        pref.setUser(user)
        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
    }
}