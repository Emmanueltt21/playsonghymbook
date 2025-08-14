package com.kottland.playsonghymbook.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kottland.playsonghymbook.R
import com.kottland.playsonghymbook.core.ui.components.HymnCard
import com.kottland.playsonghymbook.core.ui.components.NumberGridButton
import com.kottland.playsonghymbook.core.ui.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetails: (Int) -> Unit,
    onNavigateToAllHymns: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // App Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            // Language Toggle
            FilterChip(
                onClick = { viewModel.toggleLanguage() },
                label = {
                    Text(
                        text = if (language == "en") "EN" else "FR",
                        fontWeight = FontWeight.Bold
                    )
                },
                selected = true
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Search Bar
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = viewModel::updateSearchQuery,
            onSearch = { onNavigateToAllHymns() },
            placeholder = stringResource(R.string.search_hymns)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Recent Hymns Section
        if (uiState.recentHymns.isNotEmpty()) {
           /* Text(
                text = stringResource(R.string.recent_hymns),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )*/
            
            // Spacer(modifier = Modifier.height(8.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.recentHymns) { hymn ->
                    HymnCard(
                        hymn = hymn,
                        language = language,
                        onClick = { onNavigateToDetails(hymn.number) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
        }
        
       /* // Number Grid Section
        Text(
            text = stringResource(R.string.hymn_numbers),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        */
        Spacer(modifier = Modifier.height(8.dp))
        
        // Number Grid (1-400)
        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items((1..400).toList()) { number ->
                NumberGridButton(
                    number = number,
                    onClick = { onNavigateToDetails(number) }
                )
            }
        }
    }
}