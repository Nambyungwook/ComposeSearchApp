package kr.co.nbw.composesearchapp.feature.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import kr.co.nbw.composesearchapp.R
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.feature.search.model.AsyncImageState
import kr.co.nbw.composesearchapp.core.designsystem.theme.ComposeSearchAppTheme

@Composable
fun SearchBookItem(
    book: BookEntity,
    isFavoriteBook: Boolean,
    onClickMoveDetail: (BookEntity) -> Unit,
    onClickSaveBook: (BookEntity) -> Unit,
    onClickDeleteBook: (BookEntity) -> Unit
) {
    val context = LocalContext.current
    val asyncImageState = remember { mutableStateOf(AsyncImageState.Laoding) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                onClickMoveDetail(book)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(150.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(150.dp)
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
                            .size(150.dp)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                AsyncImageState.Error -> {
                    Image(
                        modifier = Modifier
                            .size(150.dp)
                            .background(Color.White),
                        painter = painterResource(id = R.drawable.ic_error_24),
                        contentDescription = "이미지 로딩 실패 아이콘"
                    )
                }
                AsyncImageState.Empty -> {
                    Image(
                        modifier = Modifier
                            .size(150.dp)
                            .background(Color.White),
                        painter = painterResource(id = R.drawable.ic_empty_image_24),
                        contentDescription = "이미지 없음"
                    )
                }
                AsyncImageState.Success -> {}
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "제목 : ${book.title}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "출판사 : ${book.publisher}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "지은이 : ${book.getAuthorsString()}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "출판일 : ${book.datetime}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "정가 : ${book.price}원",
                textAlign = TextAlign.Center
            )
            if (book.salePrice > 0) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "판매가 : ${book.price}원",
                    textAlign = TextAlign.Center
                )
            }
        }
        Image(
            modifier = Modifier
                .size(24.dp)
                .padding(4.dp)
                .clickable {
                    if (isFavoriteBook) {
                        onClickDeleteBook(book)
                    } else {
                        onClickSaveBook(book)
                    }
                },
            painter = if (isFavoriteBook) painterResource(id = R.drawable.ic_favorite_on_24) else painterResource(id = R.drawable.ic_favorite_off_24),
            contentDescription = "현재 이미지의 북마크 상태표시 및 북마크/북마크 해제 버튼"
        )
    }
}

@Preview
@Composable
fun BookItemPreview() {
    ComposeSearchAppTheme {
        SearchBookItem(
            isFavoriteBook = false,
            book = BookEntity(
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
            onClickSaveBook = {}
        )
    }
}