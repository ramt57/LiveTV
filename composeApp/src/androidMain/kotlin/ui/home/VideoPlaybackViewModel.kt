package ui.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class VideoItem(
    val contentUri: Uri,
    val mediaItem: MediaItem,
    val name: String
)

class VideoPlaybackViewModel(val player: Player) : ViewModel() {
    private val _videoUriList = MutableStateFlow<List<Uri>>(emptyList())
    val videoUriList = _videoUriList.asStateFlow()

    init {

        player.prepare()
    }

    fun setVideoUriList(list: List<Uri>) {
        _videoUriList.value = list
    }

    fun playVideo(uri: Uri) {
        player.setMediaItem(MediaItem.fromUri(uri))
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}