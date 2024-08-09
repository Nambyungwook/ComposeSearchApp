package kr.co.nbw.composesearchapp.core.data.datasource.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kr.co.nbw.composesearchapp.core.database.BookDatabase
import kr.co.nbw.composesearchapp.core.data.utils.safeApiCall
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.core.domain.model.ResultWrapper
import javax.inject.Inject

class LocalBookDataSourceImpl @Inject constructor(
    private val db: BookDatabase
): LocalBookDataSource {
    override suspend fun saveBook(book: BookEntity): ResultWrapper<BookEntity> {
        val result = safeApiCall(Dispatchers.IO) {
            db.bookDao().insertBook(book)
        }

        return when (result) {
            is ResultWrapper.Success -> {
                ResultWrapper.Success(book)
            }

            is ResultWrapper.Error -> {
                result
            }
        }
    }

    override suspend fun deleteBook(book: BookEntity): ResultWrapper<BookEntity> {
        val result = safeApiCall(Dispatchers.IO) {
            db.bookDao().deleteBook(book)
        }

        return when (result) {
            is ResultWrapper.Success -> {
                ResultWrapper.Success(book)
            }

            is ResultWrapper.Error -> {
                result
            }
        }
    }

    override suspend fun getFavoriteBooks(): ResultWrapper<Flow<List<BookEntity>>> {
        return safeApiCall(Dispatchers.IO) {
            db.bookDao().getFavoriteBooks()
        }
    }

    override suspend fun getFavoritePagingBooks(): ResultWrapper<Flow<PagingData<BookEntity>>> {
        return safeApiCall(Dispatchers.IO) {
            val pagingSourceFactory = { db.bookDao().getFavoritePagingBooks() }

            val pagingData = Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false,
                    maxSize = 20 * 3
                ),
                pagingSourceFactory = pagingSourceFactory
            ).flow

            pagingData
        }
    }
}
