package kr.co.nbw.composesearchapp.feature.favorite.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.core.ui.ErrorBody
import kr.co.nbw.composesearchapp.core.ui.LoadingProgressBar

private const val CELL_COUNT = 3
private val gridItemSpan: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(CELL_COUNT) }

@Composable
fun FavoriteBooksResult(
    savedFavoriteBooks: LazyPagingItems<BookEntity>,
    onClickMoveDetail: (BookEntity) -> Unit,
    onClickDeleteBook: (BookEntity) -> Unit,
) {
    when(savedFavoriteBooks.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingProgressBar()
        }
        is LoadState.Error -> {
            val errorMessage = (savedFavoriteBooks.loadState.refresh as? LoadState.Error)?.error?.message
                ?: "Unknown error occurred."
            ErrorBody(
                message = errorMessage,
                onClickRetry = { savedFavoriteBooks.retry() }
            )
        }
        else -> {
            val isEmpty = savedFavoriteBooks.itemCount == 0
            if (isEmpty) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No search results.")
                }
            } else {
                val gridState = rememberLazyGridState()

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    state = gridState,
                    columns = GridCells.Fixed(CELL_COUNT),
                    verticalArrangement = remember {
                        object : Arrangement.Vertical {
                            override fun Density.arrange(
                                totalSize: Int,
                                sizes: IntArray,
                                outPositions: IntArray
                            ) {
                                var currentOffset = 0
                                sizes.forEachIndexed { index, size ->
                                    if (index == sizes.lastIndex) {
                                        outPositions[index] = totalSize - size
                                    } else {
                                        outPositions[index] = currentOffset
                                        currentOffset += (size + 8.dp.toPx()).toInt()
                                    }
                                }
                            }
                        }
                    },
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = remember { PaddingValues(8.dp) },
                ) {
                    items(
                        count = savedFavoriteBooks.itemCount,
                        key = savedFavoriteBooks.itemKey(key = { book -> book.isbn })
                    ) { index ->
                        val book = savedFavoriteBooks[index]
                        book?.let {
                            FavoriteBookItem(
                                book = book,
                                onClickMoveDetail = onClickMoveDetail,
                                onClickDeleteBook = onClickDeleteBook
                            )
                        }
                    }

                    item(
                        span = gridItemSpan,
                    ) {
                        LoadStateFooter(
                            loadState = savedFavoriteBooks.loadState.append,
                            onClickRetry = { savedFavoriteBooks.retry() },
                        )
                    }
                }
            }
        }
    }
}