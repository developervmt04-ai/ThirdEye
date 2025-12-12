package com.example.thirdeye.helper

import android.content.Context
import com.example.thirdeye.constants.Constants.LOCK_KEY

class BiometricPrefs(context: Context) {
    private val pref= context.getSharedPreferences(LOCK_KEY, Context.MODE_PRIVATE)

    fun isBiometricEnabled(): Boolean{
        return pref.getBoolean(LOCK_KEY,false)
    }
    fun setBiometricKeyEnabled(enabled: Boolean) {

        pref.edit().putBoolean(LOCK_KEY,enabled)
    }



}