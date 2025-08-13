package com.kottland.playsonghymbook.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kottland.playsonghymbook.core.audio.AudioPlayer
import com.kottland.playsonghymbook.core.data.database.entity.Hymn
import com.kottland.playsonghymbook.core.data.preferences.PreferencesManager
import com.kottland.playsonghymbook.core.data.repository.HymnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val audioPlayer: AudioPlayer
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
        // TODO: Implement sharing functionality
        // This would typically use Android's share intent
    }
    
    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }
}