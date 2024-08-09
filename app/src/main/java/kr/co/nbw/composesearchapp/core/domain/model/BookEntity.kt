package kr.co.nbw.composesearchapp.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    val title: String,
    val contents: String,
    val url: String,
    @PrimaryKey(autoGenerate = false)
    val isbn: String,
    val datetime: String,
    val authors: List<String>,
    val publisher: String,
    val translators: List<String>,
    val price: Int,
    val salePrice: Int,
    val thumbnail: String,
    val status: String
) {
    fun getAuthorsString(): String {
        return authors.joinToString(", ")
    }
}