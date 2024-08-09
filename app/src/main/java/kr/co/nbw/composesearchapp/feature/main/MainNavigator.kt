package kr.co.nbw.composesearchapp.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.google.gson.Gson
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.feature.detail.navigation.navigateDetail
import kr.co.nbw.composesearchapp.feature.favorite.navigation.navigateFavorite
import kr.co.nbw.composesearchapp.feature.search.navigation.SearchRoute
import kr.co.nbw.composesearchapp.feature.search.navigation.navigateSearch

internal class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = MainTab.SEARCH.route

    val currentTab: MainTab?
        @Composable get() = currentDestination
            ?.route
            ?.let(MainTab::find)

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.SEARCH -> navController.navigateSearch(navOptions)
            MainTab.FAVORITE -> navController.navigateFavorite(navOptions)
        }
    }

    fun navigateDetail(
        bookString: String
    ) {
        navController.navigateDetail(bookString)
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    fun popBackStackIfNotSearch() {
        if (!isSameCurrentDestination(SearchRoute.ROUTE_SEARCH)) {
            popBackStack()
        }
    }

    private fun isSameCurrentDestination(route: String) =
        navController.currentDestination?.route == route

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainTab
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}