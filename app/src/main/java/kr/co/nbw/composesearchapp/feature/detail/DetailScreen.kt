package kr.co.nbw.composesearchapp.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import kr.co.nbw.composesearchapp.R
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.feature.search.component.LoadingProgressBar
import kr.co.nbw.composesearchapp.feature.search.component.SearchError
import kr.co.nbw.composesearchapp.feature.search.model.AsyncImageState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    padding: PaddingValues,
    detailUiState: DetailUiState,
    onClickBack: () -> Unit,
    onClickRetry: () -> Unit,
    onClickDeleteBook: (BookEntity) -> Unit,
    onClickSaveBook: (BookEntity) -> Unit
) {
    val context = LocalContext.current
    val asyncImageState = remember { mutableStateOf(AsyncImageState.Laoding) }
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        // 상세 화면
        CenterAlignedTopAppBar(
            title = {
                Text(text = "Detail")
            },
            navigationIcon = {
                IconButton(
                    onClick = onClickBack
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back_24),
                        contentDescription = "back"
                    )
                }
            }
        )
        Box(
            modifier = Modifier.weight(1f)
        ) {
            when (detailUiState) {
                is DetailUiState.Loading -> {
                    LoadingProgressBar()
                }
                is DetailUiState.Success -> {
                    // 성공
                    val book = detailUiState.book
                    val isFavoriteBook = detailUiState.isFavoriteBook
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Gray),
                                    model = ImageRequest.Builder(context)
                                        .data(book.thumbnail)
                                        .build(),
                                    contentDescription = "미리보기 이미지",
                                    contentScale = ContentScale.Crop,
                                    onState = { imageLoadState ->
                                        when(imageLoadState) {
                                            is AsyncImagePainter.State.Loading -> { asyncImageState.value = AsyncImageState.Laoding }
                                            is AsyncImagePainter.State.Success -> { asyncImageState.value = AsyncImageState.Success }
                                            is AsyncImagePainter.State.Error -> { asyncImageState.value = AsyncImageState.Error }
                                            is AsyncImagePainter.State.Empty -> { asyncImageState.value = AsyncImageState.Empty }
                                        }
                                    }
                                )
                                when (asyncImageState.value) {
                                    AsyncImageState.Laoding -> {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.White),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                    AsyncImageState.Error -> {
                                        Image(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.White),
                                            painter = painterResource(id = R.drawable.ic_error_24),
                                            contentDescription = "이미지 로딩 실패 아이콘"
                                        )
                                    }
                                    AsyncImageState.Empty -> {
                                        Image(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.White),
                                            painter = painterResource(id = R.drawable.ic_empty_image_24),
                                            contentDescription = "이미지 없음"
                                        )
                                    }
                                    AsyncImageState.Success -> {}
                                }
                                IconButton(
                                    onClick = {
                                        if (isFavoriteBook) {
                                            // 즐겨찾기 삭제
                                            onClickDeleteBook(book)
                                        } else {
                                            // 즐겨찾기 추가
                                            onClickSaveBook(book)
                                        }
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (isFavoriteBook) R.drawable.ic_favorite_on_24 else R.drawable.ic_favorite_off_24
                                        ),
                                        contentDescription = "즐겨찾기 삭제 버튼"
                                    )
                                }
                            }
                        }
                        // 책 제목
                        Text(
                            text = "제목 : ${book.title}"
                        )
                        // 출판사
                        Text(
                            text = "출판사 : ${book.publisher}"
                        )
                        // 지은이
                        Text(
                            text = "지은이 : ${book.getAuthorsString()}"
                        )
                        // 출판일
                        Text(
                            text = "출판일 : ${book.datetime}"
                        )
                        // 가격
                        Text(
                            text = "가격 : ${book.price}"
                        )
                        // 요약
                        Text(
                            text = "요약 : ${book.contents}"
                        )
                    }
                }
                is DetailUiState.Error -> {
                    // 에러
                    SearchError(
                        message = detailUiState.message,
                        onClickRetry = onClickRetry
                    )
                }
            }
        }
    }
}