package presentation.repo

import model.Channel
import sqlfiles.AppDatabase

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.channelQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllChannels()
        }
    }

    internal fun getAllChannels(): List<Channel> {
        return dbQuery.selectAllChannelsInfo(::mapChannelSelecting).executeAsList()
    }

    fun mapChannelSelecting(
        id: String,
        name: String,
        country: String,
        categories: String,
        languages: String,
        is_nsfw: Long,
        launched: String?,
        closed: String?,
        network: String?,
        owners: String?,
        replaced_by: String?,
        subdivision: String?,
        city: String?,
        broadcast_area: String?,
        alt_names: String?,
        logo: String?,
        website: String?,
        url: String?
    ): Channel {
        return Channel(
            id = id,
            name = name,
            country = country,
            categories = categories.split(","),
            languages = languages.split(","),
            isNsfw = is_nsfw.toInt() == 1,
            launched = launched,
            closed = closed,
            network = network,
            owners = owners?.split(","),
            replacedBy = replaced_by,
            subdivision = subdivision,
            city = city,
            broadcastArea = broadcast_area?.split(","),
            altNames = alt_names?.split(","),
            logo = logo,
            website = website,
            url = url ?: ""
        )
    }

    internal fun createChannels(channels: List<Channel>) {
        dbQuery.transaction {
            channels.forEach { channel ->
                insertChannel(channel)
            }
        }
    }

    private fun insertChannel(channel: Channel) {
        dbQuery.insertChannel(
            id = channel.id,
            name = channel.name,
            country = channel.country,
            categories = channel.categories.joinToString(","),
            languages = channel.languages.joinToString(","),
            is_nsfw = if (channel.isNsfw) 1 else 0,
            launched = channel.launched,
            closed = channel.closed,
            network = channel.network,
            owners = channel.owners?.joinToString(","),
            replaced_by = channel.replacedBy,
            subdivision = channel.subdivision,
            city = channel.city,
            broadcast_area = channel.broadcastArea?.joinToString(","),
            alt_names = channel.altNames?.joinToString(","),
            logo = channel.logo,
            website = channel.website,
            url = channel.url
        )
    }
}