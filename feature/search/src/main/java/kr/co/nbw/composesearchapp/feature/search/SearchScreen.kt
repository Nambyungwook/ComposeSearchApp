package kr.co.nbw.composesearchapp.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.feature.search.component.SearchError
import kr.co.nbw.composesearchapp.feature.search.component.SearchInitial
import kr.co.nbw.composesearchapp.feature.search.component.SearchResult
import kr.co.nbw.composesearchapp.core.designsystem.theme.ComposeSearchAppTheme
import kr.co.nbw.composesearchapp.core.ui.LoadingProgressBar

@Composable
fun SearchScreen(
    padding: PaddingValues,
    searchUiState: SearchUiState,
    onClickMoveDetail: (BookEntity) -> Unit,
    onClickSearch: (String) -> Unit,
    onClickSaveBook: (BookEntity) -> Unit,
    onClickDeleteBook: (BookEntity) -> Unit,
    onClickGetFavoriteBooks: () -> Unit
) {
    val (getQuery, setQuery) = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Text(
            text = "Search",
            modifier = Modifier.padding(16.dp)
        )
        // 상단 검색창
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = getQuery,
                onValueChange = setQuery,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onClickSearch(getQuery)
                        keyboardController?.hide()
                    }
                )
            )
            IconButton(
                onClick = {
                    keyboardController?.hide()
                    onClickSearch(getQuery)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_24),
                    contentDescription = "검색"
                )
            }
        }
        // 검색 결과 리스트
        when (searchUiState) {
            is SearchUiState.Initial -> {
                SearchInitial()
            }
            is SearchUiState.Loading -> {
                LoadingProgressBar()
            }
            is SearchUiState.Result -> {
                val savedFavoriteBooks = searchUiState.savedFavoriteBooks.collectAsState(initial = emptyList()).value
                val searchResultBooks = searchUiState.books.collectAsLazyPagingItems()

                SearchResult(
                    savedFavoriteBooks = savedFavoriteBooks,
                    searchResultBooks = searchResultBooks,
                    onClickMoveDetail = onClickMoveDetail,
                    onClickSaveBook = onClickSaveBook,
                    onClickDeleteBook = onClickDeleteBook
                )
            }
            is SearchUiState.Error -> {
                SearchError(
                    message = searchUiState.message,
                    onClickRetry = { onClickGetFavoriteBooks() }
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    ComposeSearchAppTheme {
        SearchScreen(
            padding = PaddingValues(16.dp),
            searchUiState = SearchUiState.Error("에러가 발생했습니다."),
            onClickMoveDetail = {},
            onClickSearch = {},
            onClickSaveBook = {},
            onClickDeleteBook = {},
            onClickGetFavoriteBooks = {}
        )
    }
}