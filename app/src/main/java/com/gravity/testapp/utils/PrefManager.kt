package com.gravity.testapp.utils

import android.content.Context
import android.content.SharedPreferences

const val IS_FIRST_RUN = "IS_FIRST_RUN"

class PrefManager(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    fun saveBoolean(key: String, state: Boolean) {
        val prefEditor = preferences.edit()
        prefEditor.putBoolean(key, state)

        prefEditor.apply()
    }

    fun loadBoolean(key: String): Boolean {
        return preferences.getBoolean(key, true)
    }
}