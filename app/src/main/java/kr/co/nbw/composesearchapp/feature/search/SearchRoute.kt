package kr.co.nbw.composesearchapp.feature.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity

@Composable
fun SearchRoute(
    padding: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
    onClickMoveDetail: (BookEntity) -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    val searchUiState by viewModel.searchUiState.collectAsState()

    LaunchedEffect(key1 = viewModel.searchUiEffect) {
        viewModel.searchUiEffect.collectLatest { effect ->
            when (effect) {
                is SearchUiEffect.ShowSnackBar -> {
                    onShowSnackBar(effect.message)
                }
            }
        }
    }

    SearchScreen(
        padding = padding,
        searchUiState = searchUiState,
        onClickSearch = { query ->
            viewModel.searchBooks(query)
        },
        onClickSaveBook = { book ->
            viewModel.saveBook(book)
        },
        onClickDeleteBook = { book ->
            viewModel.deleteBook(book)
        },
        onClickMoveDetail = onClickMoveDetail,
        onClickGetFavoriteBooks = {
            viewModel.getFavoriteBooks()
        }
    )
}