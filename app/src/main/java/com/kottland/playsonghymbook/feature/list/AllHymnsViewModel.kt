package com.kottland.playsonghymbook.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kottland.playsonghymbook.core.data.database.entity.Hymn
import com.kottland.playsonghymbook.core.data.preferences.PreferencesManager
import com.kottland.playsonghymbook.core.data.repository.HymnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class AllHymnsUiState(
    val hymns: List<Hymn> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

@OptIn(FlowPreview::class)
@HiltViewModel
class AllHymnsViewModel @Inject constructor(
    private val hymnRepository: HymnRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    
    val language = preferencesManager.language
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "en"
        )
    
    val uiState = combine(
        _searchQuery.debounce(300),
        language
    ) { query, lang ->
        if (query.isBlank()) {
            hymnRepository.getAllHymns()
        } else {
            hymnRepository.searchHymns(query, lang)
        }
    }.flatMapLatest { it }
        .combine(_searchQuery) { hymns, searchQuery ->
            AllHymnsUiState(
                hymns = hymns,
                searchQuery = searchQuery
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AllHymnsUiState()
        )
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}