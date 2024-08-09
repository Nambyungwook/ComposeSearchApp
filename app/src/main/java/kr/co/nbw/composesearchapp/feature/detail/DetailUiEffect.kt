package kr.co.nbw.composesearchapp.feature.detail

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
interface DetailUiEffect {
    @Immutable
    data class ShowSnackBar(val message: String) : DetailUiEffect
}