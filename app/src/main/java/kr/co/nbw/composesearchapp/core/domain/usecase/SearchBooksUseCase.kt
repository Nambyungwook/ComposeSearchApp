package kr.co.nbw.composesearchapp.core.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.core.domain.repository.BooksRepository
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(
    private val repository: BooksRepository
) {
    suspend operator fun invoke(
        query: String
    ): Flow<PagingData<BookEntity>> {
        return repository.searchBooks(
            query = query
        )
    }
}