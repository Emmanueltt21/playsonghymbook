package com.kottland.playsonghymbook.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hymns")
data class Hymn(
    @PrimaryKey
    val id: Long,
    val number: Int,
    val titleEn: String,
    val titleFr: String,
    val lyricsEn: String,
    val lyricsFr: String,
    val audioUrlEn: String?,
    val audioUrlFr: String?,
    val lastViewedAt: Long? = null,
    val isFavorite: Boolean = false
)