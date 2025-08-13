package com.kottland.playsonghymbook.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Title
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Language Setting
        SettingsSection(
            title = stringResource(R.string.language)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    onClick = { viewModel.setLanguage("en") },
                    label = { Text("English") },
                    selected = uiState.language == "en",
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    onClick = { viewModel.setLanguage("fr") },
                    label = { Text("Français") },
                    selected = uiState.language == "fr",
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        // Theme Setting
        SettingsSection(
            title = stringResource(R.string.theme)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.theme == "system",
                        onClick = { viewModel.setTheme("system") }
                    )
                    Text(
                        text = stringResource(R.string.theme_system),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.theme == "light",
                        onClick = { viewModel.setTheme("light") }
                    )
                    Text(
                        text = stringResource(R.string.theme_light),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.theme == "dark",
                        onClick = { viewModel.setTheme("dark") }
                    )
                    Text(
                        text = stringResource(R.string.theme_dark),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
        
        // Font Scale Setting
        SettingsSection(
            title = stringResource(R.string.font_scale)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("small", "medium", "large", "xlarge").forEach { scale ->
                    FilterChip(
                        onClick = { viewModel.setFontScale(scale) },
                        label = {
                            Text(
                                when (scale) {
                                    "small" -> "S"
                                    "medium" -> "M"
                                    "large" -> "L"
                                    "xlarge" -> "XL"
                                    else -> scale
                                }
                            )
                        },
                        selected = uiState.fontScale == scale,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        
        // Player Options
        SettingsSection(
            title = stringResource(R.string.player_options)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.autoplay_on_open))
                    Switch(
                        checked = uiState.autoplay,
                        onCheckedChange = viewModel::setAutoplay
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.remember_playback_position))
                    Switch(
                        checked = uiState.rememberPosition,
                        onCheckedChange = viewModel::setRememberPosition
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        content()
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}