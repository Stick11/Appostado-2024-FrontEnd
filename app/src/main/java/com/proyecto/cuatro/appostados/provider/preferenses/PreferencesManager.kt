package com.proyecto.cuatro.appostados.provider.preferenses

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREF_NAME = "MyAppPreferences"

    fun writeString(context: Context, key: String, value: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun readString(context: Context, key: String, defaultValue: String): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(key, defaultValue)
    }


     /*
     Guardar un valor en SharedPreferences
     PreferencesManager.writeString(context, "user_name", "John Doe")

     Recuperar un valor de SharedPreferences
     val userName = PreferencesManager.readString(context, "user_name", "No Name")
*/
}