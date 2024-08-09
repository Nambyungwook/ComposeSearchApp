package kr.co.nbw.composesearchapp.feature.main

import kr.co.nbw.composesearchapp.R
import kr.co.nbw.composesearchapp.feature.favorite.navigation.FavoriteRoute
import kr.co.nbw.composesearchapp.feature.search.navigation.SearchRoute

enum class MainTab(
    val iconResId: Int,
    internal val contentDescription: String,
    val route: String,
) {
    SEARCH(
        iconResId = R.drawable.ic_search_24,
        contentDescription = "검색",
        SearchRoute.ROUTE_SEARCH,
    ),
    FAVORITE(
        iconResId = R.drawable.ic_favorite_off_24,
        contentDescription = "즐겨찾기",
        FavoriteRoute.ROUTE_FAVORITE,
    );

    companion object {

        operator fun contains(route: String): Boolean {
            return entries.map { it.route }.contains(route)
        }

        fun find(route: String): MainTab? {
            return entries.find { it.route == route }
        }
    }
}
