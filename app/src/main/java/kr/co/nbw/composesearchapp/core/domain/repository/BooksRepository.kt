package kr.co.nbw.composesearchapp.core.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.core.domain.model.ResultWrapper

interface BooksRepository {
    /**
     * Search books
     * @param query 검색을 원하는 질의어
     * @return Flow<PagingData<BookEntity>> - 책 정보 페이징 데이터
     */
    suspend fun searchBooks(
        query: String
    ): Flow<PagingData<BookEntity>>

    /**
     * Save book
     * @param book 저장할 책 정보
     * @return ResultWrapper<BookEntity> - 저장된 책 정보
     */
    suspend fun saveBook(book: BookEntity): ResultWrapper<BookEntity>

    /**
     * Delete book
     * @param book 삭제할 책 정보
     * @return ResultWrapper<BookEntity> - 삭제된 책 정보
     */
    suspend fun deleteBook(book: BookEntity): ResultWrapper<BookEntity>

    /**
     * Get favorite books
     * @return ResultWrapper<Flow<List<BookEntity>>> - 즐겨찾기한 책 목록
     */
    suspend fun getFavoriteBooks(): ResultWrapper<Flow<List<BookEntity>>>

    /**
     * Get favorite paging books
     * @return ResultWrapper<Flow<PagingData<BookEntity>>> - 즐겨찾기한 책 페이징 데이터
     */
    suspend fun getFavoritePagingBooks(): ResultWrapper<Flow<PagingData<BookEntity>>>
}