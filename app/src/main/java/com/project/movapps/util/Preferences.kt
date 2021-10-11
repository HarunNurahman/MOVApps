package com.project.movapps.util

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context: Context) {
    companion object {
        const val USER_PREFERENCES = "USER_PREFERENCES"
    }

    var sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, 0)
    fun setValues(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValues(key: String): String? {
        return sharedPreferences.getString(key, "")
    }
}