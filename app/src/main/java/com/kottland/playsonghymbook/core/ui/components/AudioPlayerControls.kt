package com.kottland.playsonghymbook.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kottland.playsonghymbook.core.audio.PlaybackState
import kotlin.math.roundToInt

@Composable
fun AudioPlayerControls(
    playbackState: PlaybackState,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit,
    onPlayAudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Progress Bar
            if (playbackState.duration > 0) {
                val progress = if (playbackState.duration > 0) {
                    playbackState.currentPosition.toFloat() / playbackState.duration.toFloat()
                } else 0f
                
                Column {
                    Slider(
                        value = progress,
                        onValueChange = { newProgress ->
                            val newPosition = (newProgress * playbackState.duration).toLong()
                            onSeek(newPosition)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = formatTime(playbackState.currentPosition),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = formatTime(playbackState.duration),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Control Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rewind 10 seconds
                if (playbackState.duration > 0) {
                    IconButton(
                        onClick = {
                            val newPosition = (playbackState.currentPosition - 10000).coerceAtLeast(0)
                            onSeek(newPosition)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Rewind 10 seconds"
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Play/Pause Button
                FilledIconButton(
                    onClick = {
                        if (playbackState.duration > 0) {
                            onPlayPause()
                        } else {
                            onPlayAudio()
                        }
                    },
                    modifier = Modifier.size(56.dp)
                ) {
                    when {
                        playbackState.isLoading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        playbackState.isPlaying -> {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Pause",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        else -> {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Forward 10 seconds
                if (playbackState.duration > 0) {
                    IconButton(
                        onClick = {
                            val newPosition = (playbackState.currentPosition + 10000)
                                .coerceAtMost(playbackState.duration)
                            onSeek(newPosition)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Forward 10 seconds"
                        )
                    }
                }
            }
            
            // Error Message
            if (playbackState.error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = playbackState.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

private fun formatTime(milliseconds: Long): String {
    val totalSeconds = (milliseconds / 1000).toInt()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}