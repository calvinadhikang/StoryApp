package com.example.storyapp

import android.content.Context
import com.example.storyapp.response.LoginResult

internal class UserPreference(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: LoginResult) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(USER_ID, value.userId)
        editor.putString(TOKEN, value.token)
        editor.apply()
    }

    fun getUser(): LoginResult {
        val name = preferences.getString(NAME, "")
        val userId = preferences.getString(USER_ID, "")
        val token = preferences.getString(TOKEN, "")

        return LoginResult(name, userId, token)
    }

    fun clearUser(){
        preferences.edit().clear().commit()
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val USER_ID = "userId"
        private const val TOKEN = "token"
    }
}