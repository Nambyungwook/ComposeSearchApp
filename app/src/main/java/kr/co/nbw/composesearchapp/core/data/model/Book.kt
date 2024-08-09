package kr.co.nbw.composesearchapp.core.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Book data class : Kakao API response DTO
 * @property title String 책 제목
 * @property contents String 책 소개
 * @property url String 책 링크
 * @property isbn String ISBN
 * @property datetime Date 책 출간일
 * @property authors List<String> 책 저자 리스트
 * @property publisher String 출판사
 * @property translators List<String> 번역자 리스트
 * @property price Int 가격
 * @property salePrice Int 세일 가격
 * @property thumbnail String 썸네일 이미지 URL
 * @property status String 판매 상태
 */
data class Book(
    @SerializedName("title")
    val title: String,
    @SerializedName("contents")
    val contents: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("isbn")
    val isbn: String,
    @SerializedName("datetime")
    val datetime: Date,
    @SerializedName("authors")
    val authors: List<String>,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("translators")
    val translators: List<String>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("sale_price")
    val salePrice: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("status")
    val status: String
)
