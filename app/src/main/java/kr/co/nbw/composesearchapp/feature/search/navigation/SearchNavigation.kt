package kr.co.nbw.composesearchapp.feature.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.feature.search.SearchRoute

fun NavController.navigateSearch(navOptions: NavOptions) {
    navigate(SearchRoute.ROUTE_SEARCH, navOptions)
}

fun NavGraphBuilder.searchNavGraph(
    padding: PaddingValues,
    onClickMoveDetail: (BookEntity) -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    composable(route = SearchRoute.ROUTE_SEARCH) {
        SearchRoute(
            padding = padding,
            onClickMoveDetail = onClickMoveDetail,
            onShowSnackBar = onShowSnackBar
        )
    }
}

object SearchRoute {
    const val ROUTE_SEARCH = "search"
}