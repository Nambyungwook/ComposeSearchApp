package kr.co.nbw.composesearchapp.feature.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kr.co.nbw.composesearchapp.core.designsystem.theme.ComposeSearchAppTheme

@Composable
fun SearchInitial() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "검색어를 입력해주세요."
        )
    }
}

@Preview
@Composable
fun SearchInitialPreview() {
    ComposeSearchAppTheme {
        SearchInitial()
    }
}