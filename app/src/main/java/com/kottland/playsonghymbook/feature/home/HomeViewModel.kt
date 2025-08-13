package com.kottland.playsonghymbook.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kottland.playsonghymbook.core.data.database.entity.Hymn
import com.kottland.playsonghymbook.core.data.preferences.PreferencesManager
import com.kottland.playsonghymbook.core.data.repository.HymnRepository
import com.kottland.playsonghymbook.core.data.seeder.HymnSeeder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val recentHymns: List<Hymn> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val hymnRepository: HymnRepository,
    private val preferencesManager: PreferencesManager,
    private val hymnSeeder: HymnSeeder
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    
    val language = preferencesManager.language
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "en"
        )
    
    val uiState = combine(
        hymnRepository.getRecentHymns(3),
        _searchQuery
    ) { recentHymns, searchQuery ->
        HomeUiState(
            recentHymns = recentHymns,
            searchQuery = searchQuery
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )
    
    init {
        // Seed hymns on first launch
        viewModelScope.launch {
            hymnSeeder.seedHymnsIfNeeded()
        }
    }
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun toggleLanguage() {
        viewModelScope.launch {
            val currentLanguage = language.value
            val newLanguage = if (currentLanguage == "en") "fr" else "en"
            preferencesManager.setLanguage(newLanguage)
        }
    }
}