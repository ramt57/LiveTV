package ui

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.AdsConfiguration
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.ima.ImaAdsLoader
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ads.AdsLoader
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
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
    val adTagUri = Uri.parse("https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_ad_samples&sz=640x480&cust_params=sample_ct%3Dlinear&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator=]")
    val context = LocalContext.current
    val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(context)

    val imaLoader = ImaAdsLoader.Builder(context).build()
    val mediaSourceFactory = DefaultMediaSourceFactory(dataSourceFactory)
        .setAdsLoaderProvider(AdsLoader.Provider { unusedAdTagUri: AdsConfiguration? -> imaLoader })
    val exoPlayer =
        remember { ExoPlayer.Builder(context).setMediaSourceFactory(mediaSourceFactory).build() }

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
            mediaSourceFactory.setAdViewProvider(this)
            this.player = exoPlayer.apply {
                imaLoader.setPlayer(this)
                val mediaItem: MediaItem = MediaItem.Builder()
                    .setUri(url)
                    .setAdsConfiguration(MediaItem.AdsConfiguration.Builder(adTagUri).build())
                    .build()

                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
            }
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