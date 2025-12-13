package com.example.thirdeye.data.localData

import android.content.Context
import androidx.core.content.edit
import com.example.thirdeye.constants.Constants

class LockImagePrefs(context: Context) {

    private val pref=context.getSharedPreferences(Constants.UNLOCK_IMAGE_PREF, Context.MODE_PRIVATE)

    fun addLocked(id:String){
        val set= pref.getStringSet(Constants.UNLOCKED_SET,mutableSetOf())!!.toMutableSet()
        set.add(id)
        pref.edit {
            putStringSet(Constants.UNLOCKED_SET,set).apply()
        }

    }
    fun isLocked(id:String): Boolean{
        val set=pref.getStringSet(Constants.UNLOCKED_SET,emptySet())
        return set!!.contains(id)
    }
    fun removeLocked(id: String){
        val set= pref.getStringSet(Constants.UNLOCKED_SET,emptySet())!!.toMutableSet()
        set.remove(id)
        pref.edit { putStringSet(Constants.UNLOCKED_SET,set).apply() }


    }



}