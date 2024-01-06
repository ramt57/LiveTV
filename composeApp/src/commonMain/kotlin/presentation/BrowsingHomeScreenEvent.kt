package presentation

sealed class BrowsingHomeScreenEvent {
    data class OnChannelClicked(val id: String) : BrowsingHomeScreenEvent()
    data class OnCategoryClicked(val id: String) : BrowsingHomeScreenEvent()
    data class OnCountryClicked(val id: String) : BrowsingHomeScreenEvent()
    data class OnLanguageClicked(val id: String) : BrowsingHomeScreenEvent()
}