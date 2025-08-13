package com.kottland.playsonghymbook.core.di

import com.kottland.playsonghymbook.core.data.database.dao.HymnDao
import com.kottland.playsonghymbook.core.data.repository.HymnRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideHymnRepository(
        hymnDao: HymnDao
    ): HymnRepository {
        return HymnRepository(hymnDao)
    }
}