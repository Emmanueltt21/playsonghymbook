package com.kottland.playsonghymbook.core.data.seeder

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kottland.playsonghymbook.core.data.database.entity.Hymn
import com.kottland.playsonghymbook.core.data.repository.HymnRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

data class HymnRaw(
    val number: Int,
    val titleEn: String,
    val titleFr: String,
    val lyricsEn: String,
    val lyricsFr: String,
    val audioEn: String?,
    val audioFr: String?
)

@Singleton
class HymnSeeder @Inject constructor(
    private val context: Context,
    private val hymnRepository: HymnRepository
) {
    
    suspend fun seedHymnsIfNeeded() {
        withContext(Dispatchers.IO) {
            val count = hymnRepository.getHymnCount()
            if (count == 0) {
                seedHymns()
            }
        }
    }
    
    private suspend fun seedHymns() {
        try {
            val jsonString = context.assets.open("hymns_raw.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val listType = object : TypeToken<List<HymnRaw>>() {}.type
            val hymnRawList: List<HymnRaw> = gson.fromJson(jsonString, listType)
            
            val hymns = hymnRawList.map { raw ->
                Hymn(
                    id = raw.number.toLong(),
                    number = raw.number,
                    titleEn = raw.titleEn,
                    titleFr = raw.titleFr,
                    lyricsEn = raw.lyricsEn,
                    lyricsFr = raw.lyricsFr,
                    audioUrlEn = raw.audioEn?.let { "android.resource://${context.packageName}/raw/${it.substringBefore(".")}" },
                    audioUrlFr = raw.audioFr?.let { "android.resource://${context.packageName}/raw/${it.substringBefore(".")}" }
                )
            }
            
            hymnRepository.insertHymns(hymns)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}