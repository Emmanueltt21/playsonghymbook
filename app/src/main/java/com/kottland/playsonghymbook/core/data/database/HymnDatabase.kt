package com.kottland.playsonghymbook.core.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.kottland.playsonghymbook.core.data.database.dao.HymnDao
import com.kottland.playsonghymbook.core.data.database.entity.Hymn

@Database(
    entities = [Hymn::class],
    version = 1,
    exportSchema = false
)
abstract class HymnDatabase : RoomDatabase() {
    
    abstract fun hymnDao(): HymnDao
    
    companion object {
        @Volatile
        private var INSTANCE: HymnDatabase? = null
        
        fun getDatabase(context: Context): HymnDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HymnDatabase::class.java,
                    "hymn_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}