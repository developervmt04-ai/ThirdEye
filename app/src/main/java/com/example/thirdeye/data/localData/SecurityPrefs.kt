package com.example.thirdeye.data.localData

import android.content.Context
import com.example.thirdeye.constants.Constants.KEY_IS_FIRST_LAUNCH
import com.example.thirdeye.constants.Constants.KEY_SELECTED_LANGUAGE
import com.example.thirdeye.constants.Constants.PREF_NAME

class SecurityPrefs(context: Context) {


    val sharedPref= context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var isFirstLaunch: Boolean
        get() = sharedPref.getBoolean(KEY_IS_FIRST_LAUNCH,true)
        set(value) = sharedPref.edit().putBoolean(KEY_IS_FIRST_LAUNCH,value).apply()

    var selectedLanguage: String?
        get() = sharedPref.getString(KEY_SELECTED_LANGUAGE,"English")?:"English"
        set(value) = sharedPref.edit().putString(KEY_SELECTED_LANGUAGE,value).apply()








}