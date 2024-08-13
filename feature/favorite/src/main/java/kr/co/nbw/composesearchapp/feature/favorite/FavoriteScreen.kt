package kr.co.nbw.composesearchapp.feature.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.core.ui.ErrorBody
import kr.co.nbw.composesearchapp.core.ui.LoadingProgressBar
import kr.co.nbw.composesearchapp.feature.favorite.component.FavoriteBooksResult

@Composable
fun FavoriteScreen(
    padding: PaddingValues,
    favoriteUiState: FavoriteUiState,
    onClickMoveDetail: (BookEntity) -> Unit,
    onClickDeleteBook: (BookEntity) -> Unit,
    onClickRetry: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        Column {
            Text(
                text = "Favorite",
                modifier = Modifier.padding(16.dp)
            )

            when (favoriteUiState) {
                is FavoriteUiState.Loading -> {
                    LoadingProgressBar()
                }

                is FavoriteUiState.Success -> {
                    val savedFavoriteBooks = favoriteUiState.savedFavoriteBooks.collectAsLazyPagingItems()

                    FavoriteBooksResult(
                        savedFavoriteBooks = savedFavoriteBooks,
                        onClickMoveDetail = onClickMoveDetail,
                        onClickDeleteBook = onClickDeleteBook
                    )
                }

                is FavoriteUiState.Error -> {
                   ErrorBody(
                       message = favoriteUiState.message,
                       onClickRetry = onClickRetry
                   )
                }
            }
        }
    }
}