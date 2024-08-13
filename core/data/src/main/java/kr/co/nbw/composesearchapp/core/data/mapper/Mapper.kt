package kr.co.nbw.composesearchapp.core.data.mapper

import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.core.data.model.Book
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Book.toEntity() = BookEntity(
    title = this.title,
    contents = this.contents,
    url = this.url,
    isbn = this.isbn,
    datetime = getDateTimeString(this.datetime),
    authors = this.authors,
    publisher = this.publisher,
    translators = this.translators,
    price = this.price,
    salePrice = this.salePrice,
    thumbnail = this.thumbnail,
    status = this.status
)

fun getDateTimeString(datetime: Date): String {
    return try {
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초", Locale.KOREA)
        sdf.format(datetime)
    } catch (e: Exception) {
        // Date 포맷 변환 실패 시 "-" 반환
        e.printStackTrace()
        "-"
    }
}