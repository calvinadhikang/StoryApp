package com.example.storyapp.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerViewModel.message.observe(this){
            if (it.peekContent() == "200"){
                Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        registerViewModel.loading.observe(this){
            binding.registerLoading.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }

        registerViewModel.error.observe(this){
            binding.tvRegisterError.text = it.getContentIfNotHandled()
        }

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToLogin.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            registerViewModel.register(name, email, password)
        }
    }
}