package kr.co.nbw.composesearchapp.core.data.datasource.local

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.core.domain.model.ResultWrapper

interface LocalBookDataSource {
    suspend fun saveBook(book: BookEntity): ResultWrapper<BookEntity>

    suspend fun deleteBook(book: BookEntity): ResultWrapper<BookEntity>

    suspend fun getFavoriteBooks(): ResultWrapper<Flow<List<BookEntity>>>

    suspend fun getFavoritePagingBooks(): ResultWrapper<Flow<PagingData<BookEntity>>>
}