package kr.co.nbw.composesearchapp.feature.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.collectLatest
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity

@Composable
fun DetailRoute(
    padding: PaddingValues,
    bookString: String?,
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val detailUiState by viewModel.detailUiState.collectAsState()
    val book: BookEntity = try {
        Gson().fromJson(bookString, BookEntity::class.java)
    } catch (e: JsonSyntaxException) {
        onShowSnackBar("Json parsing error")
        return
    } catch (e: NullPointerException) {
        onShowSnackBar("Book is null")
        return
    } catch (e: Exception) {
        onShowSnackBar("Unknown error")
        return
    }

    LaunchedEffect(key1 = book) {
        viewModel.initDetailScreen(book)
    }

    LaunchedEffect(key1 = viewModel.detailUiEffect) {
        viewModel.detailUiEffect.collectLatest { effect ->
            when (effect) {
                is DetailUiEffect.ShowSnackBar -> {
                    onShowSnackBar(effect.message)
                }
            }
        }
    }

    DetailScreen(
        padding = padding,
        detailUiState = detailUiState,
        onClickBack = onBackClick,
        onClickSaveBook = { viewModel.saveBook(book) },
        onClickDeleteBook = { viewModel.deleteBook(book) },
        onClickRetry = { viewModel.initDetailScreen(book) }
    )
}