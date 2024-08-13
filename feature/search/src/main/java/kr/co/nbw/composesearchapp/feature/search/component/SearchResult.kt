package kr.co.nbw.composesearchapp.feature.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import kr.co.nbw.composesearchapp.core.ui.LoadingProgressBar

@Composable
fun SearchResult(
    savedFavoriteBooks: List<kr.co.nbw.composesearchapp.core.domain.model.BookEntity>,
    searchResultBooks: LazyPagingItems<kr.co.nbw.composesearchapp.core.domain.model.BookEntity>,
    onClickMoveDetail: (kr.co.nbw.composesearchapp.core.domain.model.BookEntity) -> Unit,
    onClickSaveBook: (kr.co.nbw.composesearchapp.core.domain.model.BookEntity) -> Unit,
    onClickDeleteBook: (kr.co.nbw.composesearchapp.core.domain.model.BookEntity) -> Unit
) {
    when (searchResultBooks.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingProgressBar()
        }
        is LoadState.Error -> {
            val errorMessage = (searchResultBooks.loadState.refresh as? LoadState.Error)?.error?.message
                ?: "Unknown error occurred."
            SearchError(
                message = errorMessage,
                onClickRetry = { searchResultBooks.retry() }
            )
        }
        else -> {
            val isEmpty = searchResultBooks.itemCount == 0
            if (isEmpty) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No search results.")
                }
            } else {
                LazyColumn {
                    items(
                        count = searchResultBooks.itemCount,
                        key = searchResultBooks.itemKey(key = { book -> book.isbn })
                    ) { index ->
                        val book = searchResultBooks[index]
                        book?.let {
                            val isSavedBook = savedFavoriteBooks.any { savedBook -> savedBook.isbn == book.isbn }

                            SearchBookItem(
                                book = book,
                                isFavoriteBook = isSavedBook,
                                onClickMoveDetail = onClickMoveDetail,
                                onClickSaveBook = onClickSaveBook,
                                onClickDeleteBook = onClickDeleteBook
                            )
                        }
                        HorizontalDivider(color = Color.Black)
                    }
                    item {
                        LoadStateFooter(
                            loadState = searchResultBooks.loadState.append,
                            onClickRetry = { searchResultBooks.retry() },
                        )
                    }
                }
            }
        }
    }
}