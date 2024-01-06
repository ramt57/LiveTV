package ui.home

sealed class TabItem(val title: String, val endPoint: String) {
    data object Country : TabItem("Country", "countries")
    data object Category : TabItem("Category", "categories")
    data object Language : TabItem("Browse by Languages", "languages")
    companion object {
        fun values(): List<TabItem> {
            return listOf(Country, Category, Language)
        }
    }
}