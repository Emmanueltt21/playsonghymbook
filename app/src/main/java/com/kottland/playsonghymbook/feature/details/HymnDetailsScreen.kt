package com.kottland.playsonghymbook.feature.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.kottland.playsonghymbook.core.ui.components.AudioPlayerControls

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HymnDetailsScreen(
    hymnNumber: Int,
    onNavigateBack: () -> Unit,
    onNavigateToHymn: (Int) -> Unit,
    viewModel: HymnDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()
    val playbackState by viewModel.playbackState.collectAsStateWithLifecycle()
    val bottomSheetState = rememberBottomSheetScaffoldState()
    
    LaunchedEffect(hymnNumber) {
        viewModel.loadHymn(hymnNumber)
    }
    
    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
            if (playbackState.isPlaying || playbackState.isPaused) {
                AudioPlayerControls(
                    playbackState = playbackState,
                    onPlayPause = { viewModel.playPause() },
                    onSeek = { position -> viewModel.seekTo(position) },
                    onPlayAudio = {
                        uiState.hymn?.let { hymn ->
                            val audioFileName = if (language == "en") "${hymn.number}en.mp3" else "${hymn.number}fr.mp3"
                            val audioUrl = "file:///android_asset/hymn_songs/$audioFileName"
                            viewModel.playAudio(audioUrl)
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                // Empty content when not playing
                Spacer(modifier = Modifier.height(1.dp))
            }
        },
        sheetPeekHeight = if (playbackState.isPlaying || playbackState.isPaused) 120.dp else 0.dp,
        topBar = {
            TopAppBar(
            title = {
                Text(
                    text = uiState.hymn?.let { "#${it.number}" } ?: "",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },


            actions = {

                // Play Button
                IconButton(
                    onClick = {
                        uiState.hymn?.let { hymn ->
                            val audioFileName = if (language == "en") "${hymn.number}en.mp3" else "${hymn.number}fr.mp3"
                            val audioUrl = "file:///android_asset/hymn_songs/$audioFileName"
                            
                            if (playbackState.isPlaying && playbackState.currentMediaItem == audioUrl) {
                                viewModel.playPause()
                            } else {
                                viewModel.playAudio(audioUrl)
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = if (playbackState.isPlaying) "Pause" else "Play"
                    )
                }

                // Favorite Toggle
                uiState.hymn?.let { hymn ->
                    IconButton(
                        onClick = { viewModel.toggleFavorite() }
                    ) {
                        Icon(
                            imageVector = if (hymn.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (hymn.isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (hymn.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                


                // Share Button
                IconButton(
                    onClick = { viewModel.shareHymn() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share"
                    )
                }
            }
        )
        }
    ) { paddingValues ->
        
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            uiState.hymn?.let { hymn ->
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Hymn Content
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Title
                        Text(
                            text = if (language == "en") hymn.titleEn else hymn.titleFr,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Lyrics
                        Text(
                            text = if (language == "en") hymn.lyricsEn else hymn.lyricsFr,
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5
                        )
                    }
                    
                    // Navigation Controls with Play Button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Previous Hymn
                        OutlinedButton(
                            onClick = {
                                if (hymn.number > 1) {
                                    onNavigateToHymn(hymn.number - 1)
                                }
                            },
                            enabled = hymn.number > 1
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Previous"
                            )
                            Text("Previous")
                        }
                        
                       /* // Play Button
                        FloatingActionButton(
                            onClick = {
                                val audioUrl = if (language == "en") hymn.audioUrlEn else hymn.audioUrlFr
                                audioUrl?.let { 
                                    if (playbackState.isPlaying) {
                                        viewModel.playPause()
                                    } else {
                                        viewModel.playAudio(it)
                                    }
                                }
                            },
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = if (playbackState.isPlaying) "Pause" else "Play",
                                modifier = Modifier.size(32.dp)
                            )
                        }*/
                        
                        // Next Hymn
                        OutlinedButton(
                            onClick = {
                                if (hymn.number < 400) {
                                    onNavigateToHymn(hymn.number + 1)
                                }
                            },
                            enabled = hymn.number < 400
                        ) {
                            Text("Next")
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Next"
                            )
                        }
                    }
                    
                    // Audio player controls are now in the bottom sheet
                }
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.hymn_not_found),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}