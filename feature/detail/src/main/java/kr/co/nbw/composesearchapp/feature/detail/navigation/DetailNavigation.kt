package kr.co.nbw.composesearchapp.feature.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.co.nbw.composesearchapp.feature.detail.DetailRoute
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun NavController.navigateDetail(
    bookString: String
) {
    navigate(DetailRoute.detailRoute(bookString))
}

fun NavGraphBuilder.detailNavGraph(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    composable(
        route = DetailRoute.detailRoute("{bookString}"),
        arguments = listOf(
            navArgument("bookString") {
                type = NavType.StringType
            }
        )
    ) {
        val bookString = try {
            URLDecoder.decode(
                it.arguments?.getString("bookString"),
                StandardCharsets.UTF_8.toString()
            )
        } catch (e: UnsupportedEncodingException) {
            onShowSnackBar("Unsupported encoding error")
            null
        }

        DetailRoute(
            padding = padding,
            bookString = bookString,
            onBackClick = onBackClick,
            onShowSnackBar = onShowSnackBar
        )
    }
}

object DetailRoute {
    private const val ROUTE_DETAIL = "detail"

    fun detailRoute(
        bookString: String
    ): String = "$ROUTE_DETAIL/$bookString"
}