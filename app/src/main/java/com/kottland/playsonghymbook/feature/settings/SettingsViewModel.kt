package com.kottland.playsonghymbook.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kottland.playsonghymbook.core.data.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val language: String = "en",
    val theme: String = "system",
    val fontScale: String = "medium",
    val autoplay: Boolean = false,
    val rememberPosition: Boolean = true
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    
    val uiState = combine(
        preferencesManager.language,
        preferencesManager.theme,
        preferencesManager.fontScale,
        preferencesManager.autoplay,
        preferencesManager.rememberPosition
    ) { language, theme, fontScale, autoplay, rememberPosition ->
        SettingsUiState(
            language = language,
            theme = theme,
            fontScale = fontScale,
            autoplay = autoplay,
            rememberPosition = rememberPosition
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )
    
    fun setLanguage(language: String) {
        viewModelScope.launch {
            preferencesManager.setLanguage(language)
        }
    }
    
    fun setTheme(theme: String) {
        viewModelScope.launch {
            preferencesManager.setTheme(theme)
        }
    }
    
    fun setFontScale(fontScale: String) {
        viewModelScope.launch {
            preferencesManager.setFontScale(fontScale)
        }
    }
    
    fun setAutoplay(autoplay: Boolean) {
        viewModelScope.launch {
            preferencesManager.setAutoplay(autoplay)
        }
    }
    
    fun setRememberPosition(rememberPosition: Boolean) {
        viewModelScope.launch {
            preferencesManager.setRememberPosition(rememberPosition)
        }
    }
}