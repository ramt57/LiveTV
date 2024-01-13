package ui.home.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.core.screen.Screen
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Channel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


class ScreenChannelDetail(val channel: Channel) : Screen {
    @Composable
    override fun Content() {
        ChannelDetails(channel)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChannelDetails(channel: Channel) {
    // Create the layout
    Column {
        // Show the channel's name
        Text(text = channel.name)

        // Show the channel's country
        Text(text = channel.country)

        // Show the channel's categories
        Text(text = channel.categories.joinToString(", "))

        // Show the channel's languages
        Text(text = channel.languages.joinToString(", "))

        // Show the channel's isNsfw flag
        Text(text = if (channel.isNsfw) "Yes" else "No")

        // Show the channel's launched date
        channel.launched?.let { Text(text = it) }

        // Show the channel's closed date
        channel.closed?.let { Text(text = it) }

        // Show the channel's network
        channel.network?.let { Text(text = it) }

        // Show the channel's owners
        channel.owners?.joinToString(", ")?.let { Text(text = it) }

        // Show the channel's replacedBy channel
        channel.replacedBy?.let { Text(text = it) }

        // Show the channel's subdivision
        channel.subdivision?.let { Text(text = it) }

        // Show the channel's city
        channel.city?.let { Text(text = it) }

        // Show the channel's broadcast area
        channel.broadcastArea?.joinToString(", ")?.let { Text(text = it) }

        // Show the channel's altNames
        channel.altNames?.joinToString(", ")?.let { Text(text = it) }

        // Show the channel's logo
        KamelImage(
            resource = asyncPainterResource(data = channel.logo ?: ""),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier.wrapContentHeight(),
            animationSpec = tween(),
            onLoading = { progress -> CircularProgressIndicator(progress) },
            onFailure = { _ ->
                val fallbackPainter = painterResource("logo.xml")
                Image(fallbackPainter, contentDescription = "failed loading image")
            }
        )

        // Show the channel's website
        channel.website?.let { Text(text = it) }
    }
}