package com.example.thirdeye.data.localData.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.thirdeye.data.models.IntruderImageMetaData
import com.example.thirdeye.data.models.IntrudersImages

@Database([IntruderImageMetaData::class],
    version = 1,
    exportSchema = false)
abstract class IntruderDB : RoomDatabase(){
    abstract fun intruderDao(): IntruderDao
}