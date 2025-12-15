package com.example.thirdeye

import android.content.Context
import androidx.room.Room
import com.example.thirdeye.data.localData.LockImagePrefs
import com.example.thirdeye.data.localData.db.IntruderDB
import com.example.thirdeye.data.localData.db.IntruderDao
import com.example.thirdeye.data.models.IntrudersImages
import com.example.thirdeye.ui.intruders.IntruderRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)


object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext app: Context
    ): IntruderDB =
        Room.databaseBuilder(
            app,
            IntruderDB::class.java,
            "intruder_db"
        ).build()
    @Provides

    fun provideIntruderDao(db: IntruderDB): IntruderDao=db.intruderDao()
    @Provides
    @Singleton

    fun provideIntruderRepo(dao: IntruderDao): IntruderRepo= IntruderRepo(dao)

    @Provides
    @Singleton
    fun providesLockImagePrefs(
        @ApplicationContext app: Context
    ): LockImagePrefs= LockImagePrefs(app)



}