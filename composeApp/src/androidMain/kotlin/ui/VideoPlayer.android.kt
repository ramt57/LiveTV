package ui

import android.net.Uri
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

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
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(url)))
            prepare()
        }
    }

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
    AndroidView(modifier = modifier.wrapContentHeight(), factory = { context ->
        PlayerView(context).apply {
            this.player = exoPlayer
        }
    }, update = {
        when (lifecycle) {
            Lifecycle.Event.ON_START -> {
                it.onResume()
                exoPlayer.play()
            }

            Lifecycle.Event.ON_RESUME -> {
                it.onResume()
                exoPlayer.play()
            }

            Lifecycle.Event.ON_PAUSE -> {
                it.onPause()
                exoPlayer.pause()
            }

            Lifecycle.Event.ON_STOP -> {
                it.onPause()
                exoPlayer.pause()
            }

            Lifecycle.Event.ON_DESTROY -> {
                exoPlayer.release()
            }

            else -> Unit
        }
    })
}