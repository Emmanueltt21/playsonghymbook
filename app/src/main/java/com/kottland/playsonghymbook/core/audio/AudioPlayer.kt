package com.kottland.playsonghymbook.core.audio

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.database.StandaloneDatabaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

data class PlaybackState(
    val isPlaying: Boolean = false,
    val isPaused: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val isLoading: Boolean = false,
    val currentMediaItem: String? = null,
    val error: String? = null
)

@Singleton
class AudioPlayer @Inject constructor(
    private val context: Context
) {
    private var exoPlayer: ExoPlayer? = null
    private var cache: SimpleCache? = null
    
    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()
    
    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            updatePlaybackState()
        }
        
        override fun onPlaybackStateChanged(playbackState: Int) {
            updatePlaybackState()
        }
    }
    
    init {
        initializePlayer()
    }
    
    private fun initializePlayer() {
        // Initialize cache
        val cacheDir = File(context.cacheDir, "audio_cache")
        cache = SimpleCache(
            cacheDir,
            LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024), // 100MB cache
            StandaloneDatabaseProvider(context)
        )
        
        // Create data source factory with cache
        val dataSourceFactory = CacheDataSource.Factory()
            .setCache(cache!!)
            .setUpstreamDataSourceFactory(DefaultDataSource.Factory(context))
        
        exoPlayer = ExoPlayer.Builder(context)
            .setMediaSourceFactory(
                androidx.media3.exoplayer.source.DefaultMediaSourceFactory(dataSourceFactory)
            )
            .build()
            .apply {
                addListener(playerListener)
            }
    }
    
    fun playAudio(audioUrl: String) {
        exoPlayer?.let { player ->
            val mediaItem = MediaItem.fromUri(audioUrl)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
            
            _playbackState.value = _playbackState.value.copy(
                currentMediaItem = audioUrl,
                isLoading = true
            )
        }
    }
    
    fun playPause() {
        exoPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
        }
    }
    
    fun seekTo(position: Long) {
        exoPlayer?.seekTo(position)
    }
    
    fun stop() {
        exoPlayer?.stop()
        _playbackState.value = PlaybackState()
    }
    
    fun stopAudio() {
        stop()
    }
    
    fun getCurrentPosition(): Long {
        return exoPlayer?.currentPosition ?: 0L
    }
    
    fun getDuration(): Long {
        return exoPlayer?.duration ?: 0L
    }
    
    private fun updatePlaybackState() {
        exoPlayer?.let { player ->
            _playbackState.value = _playbackState.value.copy(
                isPlaying = player.isPlaying,
                isPaused = !player.isPlaying && player.playbackState == Player.STATE_READY,
                currentPosition = player.currentPosition,
                duration = if (player.duration > 0) player.duration else 0L,
                isLoading = player.playbackState == Player.STATE_BUFFERING
            )
        }
    }
    
    fun release() {
        exoPlayer?.removeListener(playerListener)
        exoPlayer?.release()
        exoPlayer = null
        cache?.release()
        cache = null
    }
}