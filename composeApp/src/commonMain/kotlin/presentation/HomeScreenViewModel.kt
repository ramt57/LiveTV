package presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.CategoryItem
import model.Channel
import model.CountryItem
import model.GuideItem
import model.Language
import presentation.repo.Database
import presentation.repo.IptvApi

class HomeScreenViewModel(private val database: Database, private val api: IptvApi) :
    ScreenModel {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isActive = MutableStateFlow(false)
    val isActive = _isActive.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onActiveChange(isActive: Boolean) {
        _isActive.value = isActive
    }

    /* Channels */
    private val _stateChannels = MutableStateFlow<List<Channel>>(emptyList())

    @OptIn(FlowPreview::class)
    val filteredChannels = searchText.debounce(500L).combine(_stateChannels) { search, channels ->
        if (search.isNotBlank()) {
            channels.filter {
                it.doesMatchQuery(search)
            }
        } else {
            channels
        }
    }.stateIn(
        screenModelScope,
        initialValue = _stateChannels.value,
        started = SharingStarted.WhileSubscribed(5000)
    )

    /* Category */
    private val _categoryList = MutableStateFlow<List<CategoryItem>>(emptyList())

    @OptIn(FlowPreview::class)
    val filteredCategory = searchText.debounce(500L).combine(_categoryList) { search, category ->
        if (search.isNotBlank()) {
            category.filter {
                it.doesMatchQuery(search)
            }
        } else {
            category
        }
    }.stateIn(
        screenModelScope,
        initialValue = _categoryList.value,
        started = SharingStarted.WhileSubscribed(5000)
    )

    private val _countryList = MutableStateFlow<List<CountryItem>>(emptyList())

    @OptIn(FlowPreview::class)
    val filteredCountry = searchText.debounce(500L).combine(_countryList) { search, country ->
        if (search.isNotBlank()) {
            country.filter {
                it.doesMatchQuery(search)
            }
        } else {
            country
        }
    }.stateIn(
        screenModelScope,
        initialValue = _countryList.value,
        started = SharingStarted.WhileSubscribed(5000)
    )

    private val _languageList = MutableStateFlow<List<Language>>(emptyList())

    @OptIn(FlowPreview::class)
    val filteredLanguageList =
        searchText.debounce(500L).combine(_languageList) { search, language ->
            if (search.isNotBlank()) {
                language.filter {
                    it.doesMatchQuery(search)
                }
            } else {
                language
            }
        }.stateIn(
            screenModelScope,
            initialValue = _languageList.value,
            started = SharingStarted.WhileSubscribed(5000)
        )

    private val _guideList = MutableStateFlow<List<GuideItem>>(emptyList())

    @OptIn(FlowPreview::class)
    val filteredGuideList = searchText.debounce(500L).combine(_guideList) { search, guide ->
        if (search.isNotBlank()) {
            guide.filter {
                it.doesMatchQuery(search)
            }
        } else {
            guide
        }
    }.stateIn(
        screenModelScope,
        initialValue = _guideList.value,
        started = SharingStarted.WhileSubscribed(5000)
    )


    override fun onDispose() {
        super.onDispose()
        api.onClose()
    }

    private suspend fun getAllChannels(forceReload: Boolean): List<Channel> {
        val cachedChannels = database.getAllChannels()
        return if (cachedChannels.isNotEmpty() && !forceReload) {
            cachedChannels
        } else {
            val streamMap =
                api.getAllStream().filter { it.channel.isNotBlank() }.associateBy { it.channel }
            val blockedList = api.getAllBlockedList().associateBy { it.channel }
            api.getAllChannels().filter { !it.isNsfw && streamMap.containsKey(it.id) }.filterNot {
                blockedList.containsKey(it.id)
            }
                .mapNotNull { channel ->
                    streamMap[channel.id]?.let {
                        channel.copy(url = it.url)
                    }
                }.also {
                    database.clearDatabase()
                    database.createChannels(it)
                }
        }
    }

    private suspend fun getAllCategory(forceReload: Boolean): List<CategoryItem> {
        return api.getCategoryList()
    }

    private suspend fun getAllCountry(forceReload: Boolean): List<CountryItem> {
        return api.getCountriesList()
    }

    private suspend fun getAllLanguage(forceReload: Boolean): List<Language> {
        return api.getLanguageList()
    }

    private suspend fun getAllGuide(forceReload: Boolean): List<GuideItem> {
        return api.getChannelGuideList()
    }

    fun updateChannels(forceReload: Boolean) {
        screenModelScope.launch {
            val channelList = getAllChannels(forceReload)
            _stateChannels.value = channelList
        }
    }

    fun updateCategories(forceReload: Boolean) {
        screenModelScope.launch {
            _categoryList.value = getAllCategory(true)
        }
    }

    fun updateCountries(forceReload: Boolean) {
        screenModelScope.launch {
            _countryList.value = getAllCountry(true)
        }
    }

    fun updateLanguages(forceReload: Boolean) {
        screenModelScope.launch {
            _languageList.value = getAllLanguage(true)
        }
    }

    fun updateGuides(forceReload: Boolean) {
        screenModelScope.launch {
            _guideList.value = getAllGuide(true)
        }
    }
}