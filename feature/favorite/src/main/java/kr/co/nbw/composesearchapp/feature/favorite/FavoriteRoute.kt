package kr.co.nbw.composesearchapp.feature.favorite

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity

@Composable
fun FavoriteRoute(
    padding: PaddingValues,
    viewModel: FavoriteViewModel = hiltViewModel(),
    onClickMoveDetail: (BookEntity) -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    val favoriteUiState by viewModel.favoriteUiState.collectAsState()

    LaunchedEffect(key1 = viewModel.favoriteUiEffect) {
        viewModel.favoriteUiEffect.collectLatest { effect ->
            when (effect) {
                is FavoriteUiEffect.ShowSnackBar -> {
                    onShowSnackBar(effect.message)
                }
            }
        }
    }

    FavoriteScreen(
        padding = padding,
        favoriteUiState = favoriteUiState,
        onClickMoveDetail = onClickMoveDetail,
        onClickDeleteBook = { book ->
            viewModel.deleteBook(book)
        },
        onClickRetry = {
            viewModel.getFavoriteBooks()
        }
    )
}