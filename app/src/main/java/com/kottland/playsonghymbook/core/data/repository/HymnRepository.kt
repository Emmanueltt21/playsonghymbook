package com.kottland.playsonghymbook.core.data.repository

import com.kottland.playsonghymbook.core.data.database.dao.HymnDao
import com.kottland.playsonghymbook.core.data.database.entity.Hymn
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HymnRepository @Inject constructor(
    private val hymnDao: HymnDao
) {
    
    suspend fun getHymnByNumber(number: Int): Hymn? {
        return hymnDao.getByNumber(number)
    }
    
    fun getHymnByNumberFlow(number: Int): Flow<Hymn?> {
        return hymnDao.getByNumberFlow(number)
    }
    
    fun searchHymns(query: String, locale: String): Flow<List<Hymn>> {
        return hymnDao.search(query, locale)
    }
    
    fun getRecentHymns(limit: Int = 3): Flow<List<Hymn>> {
        return hymnDao.getRecent(limit)
    }
    
    fun getFavoriteHymns(): Flow<List<Hymn>> {
        return hymnDao.getFavorites()
    }
    
    fun getAllHymns(): Flow<List<Hymn>> {
        return hymnDao.getAllHymns()
    }
    
    suspend fun toggleFavorite(hymnId: Long, isFavorite: Boolean) {
        hymnDao.updateFavorite(hymnId, isFavorite)
    }
    
    suspend fun updateLastViewed(hymnId: Long) {
        hymnDao.updateLastViewed(hymnId, System.currentTimeMillis())
    }
    
    suspend fun insertHymns(hymns: List<Hymn>) {
        hymnDao.insertAll(hymns)
    }
    
    suspend fun getHymnCount(): Int {
        return hymnDao.getCount()
    }
    
    suspend fun clearAllHymns() {
        hymnDao.deleteAll()
    }
}