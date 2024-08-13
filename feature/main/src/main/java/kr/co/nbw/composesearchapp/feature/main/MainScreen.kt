package kr.co.nbw.composesearchapp.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.feature.detail.navigation.detailNavGraph
import kr.co.nbw.composesearchapp.feature.favorite.navigation.favoriteNavGraph
import kr.co.nbw.composesearchapp.feature.main.component.MainBottomBar
import kr.co.nbw.composesearchapp.feature.search.navigation.searchNavGraph
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val onShowSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }
    val onClickMoveDetail = { book: BookEntity ->
        try {
            val bookString = URLEncoder.encode(
                Gson().toJson(book),
                StandardCharsets.UTF_8.toString()
            )
            navigator.navigateDetail(bookString)
        } catch (e: UnsupportedEncodingException) {
            onShowSnackBar("Unsupported encoding error")
        } catch (e: JsonSyntaxException) {
            onShowSnackBar("Json parsing error")
        } catch (e: Exception) {
            onShowSnackBar("Unknown error")
        }
    }

    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceDim)
            ) {
                NavHost(
                    navController = navigator.navController,
                    startDestination = navigator.startDestination,
                ) {
                    searchNavGraph(
                        padding = padding,
                        onClickMoveDetail = onClickMoveDetail,
                        onShowSnackBar = onShowSnackBar
                    )
                    favoriteNavGraph(
                        padding = padding,
                        onClickMoveDetail = onClickMoveDetail,
                        onShowSnackBar = onShowSnackBar
                    )
                    detailNavGraph(
                        padding = padding,
                        onBackClick = navigator::popBackStackIfNotSearch,
                        onShowSnackBar = onShowSnackBar
                    )
                }
            }
        },
        bottomBar = {
            MainBottomBar(
                visible = navigator.shouldShowBottomBar(),
                tabs = MainTab.entries.toList(),
                currentTab = navigator.currentTab,
                onTabSelected = { navigator.navigate(it) }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    )
}