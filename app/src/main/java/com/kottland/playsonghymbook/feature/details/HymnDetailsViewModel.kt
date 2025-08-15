package com.kottland.playsonghymbook.feature.details

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kottland.playsonghymbook.core.audio.AudioPlayer
import com.kottland.playsonghymbook.core.data.database.entity.Hymn
import com.kottland.playsonghymbook.core.data.preferences.PreferencesManager
import com.kottland.playsonghymbook.core.data.repository.HymnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HymnDetailsUiState(
    val hymn: Hymn? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HymnDetailsViewModel @Inject constructor(
    private val hymnRepository: HymnRepository,
    private val preferencesManager: PreferencesManager,
    private val audioPlayer: AudioPlayer,
    @ApplicationContext private val context: Context
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HymnDetailsUiState())
    val uiState: StateFlow<HymnDetailsUiState> = _uiState.asStateFlow()
    
    val language = preferencesManager.language
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "en"
        )
    
    val playbackState = audioPlayer.playbackState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = audioPlayer.playbackState.value
        )
    
    fun loadHymn(number: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val hymn = hymnRepository.getHymnByNumber(number)
                if (hymn != null) {
                    _uiState.value = _uiState.value.copy(
                        hymn = hymn,
                        isLoading = false
                    )
                    
                    // Update last viewed
                    hymnRepository.updateLastViewed(hymn.id)
                } else {
                    _uiState.value = _uiState.value.copy(
                        hymn = null,
                        isLoading = false,
                        error = "Hymn not found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun toggleFavorite() {
        viewModelScope.launch {
            _uiState.value.hymn?.let { hymn ->
                val newFavoriteStatus = !hymn.isFavorite
                hymnRepository.toggleFavorite(hymn.id, newFavoriteStatus)
                
                _uiState.value = _uiState.value.copy(
                    hymn = hymn.copy(isFavorite = newFavoriteStatus)
                )
            }
        }
    }
    
    fun playAudio(audioUrl: String) {
        audioPlayer.playAudio(audioUrl)
    }
    
    fun playPause() {
        audioPlayer.playPause()
    }
    
    fun seekTo(position: Long) {
        audioPlayer.seekTo(position)
    }
    
    fun shareHymn() {
        val hymn = _uiState.value.hymn ?: return
        val language = language.value
        
        val title = if (language == "en") hymn.titleEn else hymn.titleFr
        val shareText = "Check out this hymn: #${hymn.number} - $title"
        
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "Hymn #${hymn.number}")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        
        val chooserIntent = Intent.createChooser(shareIntent, "Share Hymn")
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
    }
    
    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }
}