package kr.co.nbw.composesearchapp.feature.favorite.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import kr.co.nbw.composesearchapp.R
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.feature.search.model.AsyncImageState
import kr.co.nbw.composesearchapp.core.designsystem.theme.ComposeSearchAppTheme

@Composable
fun FavoriteBookItem(
    book: BookEntity,
    onClickMoveDetail: (BookEntity) -> Unit,
    onClickDeleteBook: (BookEntity) -> Unit
) {
    val context = LocalContext.current
    val asyncImageState = remember { mutableStateOf(AsyncImageState.Laoding) }
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable {
                onClickMoveDetail(book)
            }
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
                onClick = { onClickDeleteBook(book) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorite_on_24),
                    contentDescription = "즐겨찾기 삭제 버튼"
                )
            }
        }
    }
}

@Preview
@Composable
fun FavoriteBookItemPreview() {
    ComposeSearchAppTheme {
        FavoriteBookItem(
            BookEntity(
                isbn = "isbn",
                title = "title",
                authors = listOf("author"),
                price = 123000,
                contents = "인간은 변할 수 있고, 누구나 행복해 질 수 있다. 단 그러기 위해서는 ‘용기’가 필요하다고 말한 철학자가 있다. 바로 프로이트, 융과 함께 ‘심리학의 3대 거장’으로 일컬어지고 있는 알프레드 아들러다. 『미움받을 용기』는 아들러 심리학에 관한 일본의 1인자 철학자 기시미 이치로와 베스트셀러 작가인 고가 후미타케의 저서로, 아들러의 심리학을 ‘대화체’로 쉽고 맛깔나게 정리하고 있다. 아들러 심리학을 공부한 철학자와 세상에 부정적이고 열등감 많은",
                datetime = "yyyy년 MM월 dd일 HH시 mm분 ss초",
                thumbnail = "https://bookthumb-phinf.pstatic.net/cover/137/995/13799585.jpg?type=m1&udate=20210101",
                publisher = "출판사",
                url = "https://book.naver.com/bookdb/book_detail.nhn?bid=13799585",
                status = "정상",
                translators = listOf("translator"),
                salePrice = 123000,
            ),
            onClickDeleteBook = {},
            onClickMoveDetail = {},
        )
    }
}