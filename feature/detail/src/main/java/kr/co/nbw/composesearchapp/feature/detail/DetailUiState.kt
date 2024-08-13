package kr.co.nbw.composesearchapp.feature.detail

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity

@Stable
interface DetailUiState {
    @Immutable
    data object Loading : DetailUiState

    @Immutable
    data class Success(
        val isFavoriteBook: Boolean,
        val book: BookEntity
    ) : DetailUiState

    @Immutable
    data class Error(
        val message: String
    ) : DetailUiState
}