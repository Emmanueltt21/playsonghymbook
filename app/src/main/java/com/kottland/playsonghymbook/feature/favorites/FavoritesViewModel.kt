package com.kottland.playsonghymbook.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kottland.playsonghymbook.core.data.database.entity.Hymn
import com.kottland.playsonghymbook.core.data.preferences.PreferencesManager
import com.kottland.playsonghymbook.core.data.repository.HymnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class FavoritesUiState(
    val favoriteHymns: List<Hymn> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val hymnRepository: HymnRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    
    val language = preferencesManager.language
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "en"
        )
    
    val uiState = hymnRepository.getFavoriteHymns()
        .map { favoriteHymns ->
            FavoritesUiState(
                favoriteHymns = favoriteHymns
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState()
        )
}