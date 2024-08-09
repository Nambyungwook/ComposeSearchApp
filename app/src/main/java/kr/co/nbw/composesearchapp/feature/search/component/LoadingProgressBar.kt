package kr.co.nbw.composesearchapp.feature.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kr.co.nbw.composesearchapp.core.designsystem.theme.ComposeSearchAppTheme

@Composable
fun LoadingProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun LoadingProgressBarPreview() {
    ComposeSearchAppTheme {
        LoadingProgressBar()
    }
}