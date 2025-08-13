package com.kottland.playsonghymbook.core.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class PreferencesManager @Inject constructor(
    private val context: Context
) {
    private val dataStore = context.dataStore
    
    companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language")
        val THEME_KEY = stringPreferencesKey("theme")
        val FONT_SCALE_KEY = stringPreferencesKey("font_scale")
        val AUTOPLAY_KEY = booleanPreferencesKey("autoplay")
        val REMEMBER_POSITION_KEY = booleanPreferencesKey("remember_position")
    }
    
    val language: Flow<String> = dataStore.data.map { preferences ->
        preferences[LANGUAGE_KEY] ?: "en"
    }
    
    val theme: Flow<String> = dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: "system"
    }
    
    val fontScale: Flow<String> = dataStore.data.map { preferences ->
        preferences[FONT_SCALE_KEY] ?: "medium"
    }
    
    val autoplay: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[AUTOPLAY_KEY] ?: false
    }
    
    val rememberPosition: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[REMEMBER_POSITION_KEY] ?: true
    }
    
    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }
    
    suspend fun setTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }
    
    suspend fun setFontScale(fontScale: String) {
        dataStore.edit { preferences ->
            preferences[FONT_SCALE_KEY] = fontScale
        }
    }
    
    suspend fun setAutoplay(autoplay: Boolean) {
        dataStore.edit { preferences ->
            preferences[AUTOPLAY_KEY] = autoplay
        }
    }
    
    suspend fun setRememberPosition(rememberPosition: Boolean) {
        dataStore.edit { preferences ->
            preferences[REMEMBER_POSITION_KEY] = rememberPosition
        }
    }
}