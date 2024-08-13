package kr.co.nbw.composesearchapp.feature.search

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity

@Stable
sealed interface SearchUiState {
    @Immutable
    data class Initial(
        val savedFavoriteBooks: Flow<List<BookEntity>>
    ) : SearchUiState

    @Immutable
    data object Loading : SearchUiState

    @Immutable
    data class Result(
        val books: Flow<PagingData<BookEntity>>,
        val savedFavoriteBooks: Flow<List<BookEntity>>
    ) : SearchUiState

    @Immutable
    data class Error(
        val message: String
    ) : SearchUiState
}