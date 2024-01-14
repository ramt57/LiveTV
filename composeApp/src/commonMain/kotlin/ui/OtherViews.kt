package ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.core.config.DefaultHttpCacheSize
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpFetcher
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import model.Channel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@Composable
fun ChannelView(channel: Channel, onChannelClicked: (String) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .wrapContentHeight().wrapContentWidth()
    ){
        Column(
            modifier = Modifier
                .padding(12.dp).clickable {
                    onChannelClicked(channel.id)
                },
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(Modifier.wrapContentHeight(), channel.logo?:"")
            Text(text = channel.name)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AsyncImage(modifier: Modifier, url: String){
    KamelConfig {
        httpFetcher {
            httpCache(DefaultHttpCacheSize)
        }
    }
    KamelImage(
        resource = asyncPainterResource(data = url),
        contentDescription = null,
        contentScale = ContentScale.Inside,
        modifier = modifier,
        animationSpec = tween(),
        onLoading = { progress -> CircularProgressIndicator(progress) },
        onFailure = { _ ->
            val fallbackPainter = painterResource("logo.xml")
            Image(fallbackPainter, contentDescription = "failed loading image")
        }
    )
}
@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppVideo(channel: Channel?) {
    val state = rememberVideoPlayerState()
    Column {
        channel?.let {
            VideoPlayerImpl(
                url = it.url,
                isResumed = state.isResumed,
                volume = state.volume,
                speed = state.speed,
                seek = state.seek,
                isFullscreen = state.isFullscreen,
                progressState = state._progress,
                onFinish = state::stopPlayback,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }
    }
}