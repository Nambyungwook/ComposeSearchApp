package kr.co.nbw.composesearchapp.feature.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import kr.co.nbw.composesearchapp.core.designsystem.theme.ComposeSearchAppTheme

@Composable
fun LoadStateFooter(
    loadState: LoadState,
    onClickRetry: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        when (loadState) {
            is LoadState.Loading -> {
                CircularProgressIndicator()
            }
            is LoadState.Error -> {
                val errorMessage = "${loadState.error.message}"
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(text = errorMessage)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = onClickRetry) {
                        Text(text = "Retry")
                    }
                }
            }
            else -> {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "End of result")
                }
            }
        }
    }
}

@Preview
@Composable
fun LoadStateFooterPreview() {
    ComposeSearchAppTheme {
        LoadStateFooter(
            loadState = LoadState.Error(Exception("에러가 발생했습니다.")),
            onClickRetry = {}
        )
    }
}