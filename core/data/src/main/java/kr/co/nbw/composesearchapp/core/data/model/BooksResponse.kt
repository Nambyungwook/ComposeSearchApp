package kr.co.nbw.composesearchapp.core.data.model

import com.google.gson.annotations.SerializedName

/**
 * BooksResponse DTO data class : Kakao API
 * @property books List<Book> 책 리스트
 * @property meta Meta 응답 관련 정보
 */
data class BooksResponse(
    @SerializedName("documents")
    val books: List<Book>,
    @SerializedName("meta")
    val meta: Meta
)
