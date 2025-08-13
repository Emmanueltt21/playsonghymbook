package com.kottland.playsonghymbook.core.di

import android.content.Context
import androidx.room.Room
import com.kottland.playsonghymbook.core.data.database.HymnDatabase
import com.kottland.playsonghymbook.core.data.database.dao.HymnDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideHymnDatabase(
        @ApplicationContext context: Context
    ): HymnDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            HymnDatabase::class.java,
            "hymn_database"
        ).build()
    }
    
    @Provides
    fun provideHymnDao(database: HymnDatabase): HymnDao {
        return database.hymnDao()
    }
}