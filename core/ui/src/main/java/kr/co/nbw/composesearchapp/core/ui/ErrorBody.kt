package kr.co.nbw.composesearchapp.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorBody(
    message: String,
    onClickRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = message)
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onClickRetry) {
                Text(text = "Retry")
            }
        }
    }
}

@Preview
@Composable
fun ErrorBodyPreview() {
    ErrorBody(
        message = "에러가 발생했습니다.",
        onClickRetry = {}
    )
}