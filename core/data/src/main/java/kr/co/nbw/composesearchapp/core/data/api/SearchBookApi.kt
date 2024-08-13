package kr.co.nbw.composesearchapp.core.data.api

import kr.co.nbw.composesearchapp.core.data.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchBookApi {
    @GET("v3/search/book")
    suspend fun searchBooks(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("target") target: String
    ): BooksResponse
}