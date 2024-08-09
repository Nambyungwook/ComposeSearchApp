package kr.co.nbw.composesearchapp.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import kr.co.nbw.composesearchapp.core.designsystem.theme.ComposeSearchAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigator: MainNavigator = rememberMainNavigator()
            ComposeSearchAppTheme {
                MainScreen(
                    navigator = navigator
                )
            }
        }
    }
}