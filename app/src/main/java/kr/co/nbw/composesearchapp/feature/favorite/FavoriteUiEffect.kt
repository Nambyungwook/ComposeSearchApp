package kr.co.nbw.composesearchapp.feature.favorite

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
interface FavoriteUiEffect {
    @Immutable
    data class ShowSnackBar(val message: String) : FavoriteUiEffect
}