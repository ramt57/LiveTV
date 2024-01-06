package ui.home

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal actual fun VideoPlayerImpl(
    url: String,
    isResumed: Boolean,
    volume: Float,
    speed: Float,
    seek: Float,
    isFullscreen: Boolean,
    progressState: MutableState<Progress>,
    modifier: Modifier,
    onFinish: (() -> Unit)?
) {
    val s = ExoPlayer.Builder(LocalContext.current).build()
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VideoPlaybackViewModel(s) as T
        }
    }
    val viewModelPlayer: VideoPlaybackViewModel = viewModel(
        factory = factory
    )
    viewModelPlayer.playVideo(Uri.parse(url))
    val player = s
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    AndroidView(modifier = modifier, factory = { context ->
        PlayerView(context).apply {
            this.player = player
        }
    }, update = {
        when (lifecycle) {
            Lifecycle.Event.ON_START -> {
                if (isResumed) {
                    it.onResume()
                    player.play()
                }
            }

            Lifecycle.Event.ON_RESUME -> {
                it.onResume()
                player.play()
            }

            Lifecycle.Event.ON_PAUSE -> {
                it.onPause()
                player.pause()
            }

            Lifecycle.Event.ON_STOP -> {
                it.onPause()
                player.pause()
            }

            Lifecycle.Event.ON_DESTROY -> {
                player.release()
            }

            else -> Unit
        }
    })

}