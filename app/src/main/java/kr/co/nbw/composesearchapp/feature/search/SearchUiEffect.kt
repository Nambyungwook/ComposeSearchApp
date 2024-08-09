package kr.co.nbw.composesearchapp.feature.search

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface SearchUiEffect {
    @Immutable
    data class ShowSnackBar(val message: String) : SearchUiEffect
}