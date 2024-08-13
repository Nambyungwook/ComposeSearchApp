package kr.co.nbw.composesearchapp.core.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.nbw.composesearchapp.core.data.datasource.local.LocalBookDataSource
import kr.co.nbw.composesearchapp.core.data.datasource.remote.RemoteSearchBookDataSource
import kr.co.nbw.composesearchapp.core.data.mapper.toEntity
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.core.domain.model.ResultWrapper
import kr.co.nbw.composesearchapp.core.domain.repository.BooksRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepositoryImpl @Inject constructor(
    private val remoteSearchBookDataSource: RemoteSearchBookDataSource,
    private val localBookDataSource: LocalBookDataSource
) : BooksRepository {
    override suspend fun searchBooks(query: String): Flow<PagingData<BookEntity>> {
        return remoteSearchBookDataSource.searchBooks(query)
            .map { pagingData ->
                pagingData.map { book ->
                    book.toEntity()
                }
            }
    }

    override suspend fun saveBook(book: BookEntity): ResultWrapper<BookEntity> {
        return localBookDataSource.saveBook(book)
    }

    override suspend fun deleteBook(book: BookEntity): ResultWrapper<BookEntity> {
        return localBookDataSource.deleteBook(book)
    }

    override suspend fun getFavoriteBooks(): ResultWrapper<Flow<List<BookEntity>>> {
        return localBookDataSource.getFavoriteBooks()
    }

    override suspend fun getFavoritePagingBooks(): ResultWrapper<Flow<PagingData<BookEntity>>> {
        return localBookDataSource.getFavoritePagingBooks()
    }
}