package com.example.thirdeye.helper

import android.content.Context
import androidx.core.content.edit
import com.example.thirdeye.constants.Constants.UNLOCKED_SET
import com.example.thirdeye.constants.Constants.UNLOCK_IMAGE_PREF
import kotlin.collections.mutableSetOf

class LockImagePrefs(context: Context) {

    private val pref=context.getSharedPreferences(UNLOCK_IMAGE_PREF, Context.MODE_PRIVATE)

    fun addLocked(id:String){
        val set= pref.getStringSet(UNLOCKED_SET,mutableSetOf())!!.toMutableSet()
        set.add(id)
        pref.edit {
            putStringSet(UNLOCKED_SET,set).apply()
        }

    }
    fun isLocked(id:String): Boolean{
        val set=pref.getStringSet(UNLOCKED_SET,emptySet())
        return set!!.contains(id)
    }
    fun removeLocked(id: String){
        val set= pref.getStringSet(UNLOCKED_SET,emptySet())!!.toMutableSet()
        set.remove(id)
        pref.edit { putStringSet(UNLOCKED_SET,set).apply() }


    }



}