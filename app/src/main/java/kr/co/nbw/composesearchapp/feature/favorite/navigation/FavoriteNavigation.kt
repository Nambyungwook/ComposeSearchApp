package kr.co.nbw.composesearchapp.feature.favorite.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.feature.favorite.FavoriteRoute
import kr.co.nbw.composesearchapp.feature.favorite.FavoriteScreen

fun NavController.navigateFavorite(navOptions: NavOptions) {
    navigate(FavoriteRoute.ROUTE_FAVORITE, navOptions)
}

fun NavGraphBuilder.favoriteNavGraph(
    padding: PaddingValues,
    onClickMoveDetail: (BookEntity) -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    composable(route = FavoriteRoute.ROUTE_FAVORITE) {
        FavoriteRoute(
            padding = padding,
            onClickMoveDetail = onClickMoveDetail,
            onShowSnackBar = onShowSnackBar
        )
    }
}

object FavoriteRoute {
    const val ROUTE_FAVORITE = "favorite"
}