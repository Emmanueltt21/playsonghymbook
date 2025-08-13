package com.kottland.playsonghymbook.core.data.database.dao

import androidx.room.*
import com.kottland.playsonghymbook.core.data.database.entity.Hymn
import kotlinx.coroutines.flow.Flow

@Dao
interface HymnDao {
    
    @Query("SELECT * FROM hymns WHERE number = :number")
    suspend fun getByNumber(number: Int): Hymn?
    
    @Query("SELECT * FROM hymns WHERE number = :number")
    fun getByNumberFlow(number: Int): Flow<Hymn?>
    
    @Query("""
        SELECT * FROM hymns 
        WHERE (:locale = 'en' AND (titleEn LIKE '%' || :query || '%' OR lyricsEn LIKE '%' || :query || '%'))
        OR (:locale = 'fr' AND (titleFr LIKE '%' || :query || '%' OR lyricsFr LIKE '%' || :query || '%'))
        OR (CAST(number AS TEXT) LIKE '%' || :query || '%')
        ORDER BY number
    """)
    fun search(query: String, locale: String): Flow<List<Hymn>>
    
    @Query("SELECT * FROM hymns WHERE lastViewedAt IS NOT NULL ORDER BY lastViewedAt DESC LIMIT :limit")
    fun getRecent(limit: Int): Flow<List<Hymn>>
    
    @Query("SELECT * FROM hymns WHERE isFavorite = 1 ORDER BY number")
    fun getFavorites(): Flow<List<Hymn>>
    
    @Query("SELECT * FROM hymns ORDER BY number")
    fun getAllHymns(): Flow<List<Hymn>>
    
    @Query("UPDATE hymns SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)
    
    @Query("UPDATE hymns SET lastViewedAt = :timestamp WHERE id = :id")
    suspend fun updateLastViewed(id: Long, timestamp: Long)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hymns: List<Hymn>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hymn: Hymn)
    
    @Query("DELETE FROM hymns")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM hymns")
    suspend fun getCount(): Int
}