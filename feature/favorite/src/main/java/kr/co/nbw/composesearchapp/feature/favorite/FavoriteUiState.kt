package kr.co.nbw.composesearchapp.feature.favorite

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity

@Stable
sealed interface FavoriteUiState {
    @Immutable
    data object Loading : FavoriteUiState

    @Immutable
    data class Success(
        val savedFavoriteBooks: Flow<PagingData<BookEntity>>,
    ) : FavoriteUiState

    @Immutable
    data class Error(
        val message: String
    ) : FavoriteUiState
}