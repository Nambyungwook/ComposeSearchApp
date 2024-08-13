package kr.co.nbw.composesearchapp.core.domain.usecase

import kr.co.nbw.composesearchapp.core.domain.repository.BooksRepository
import javax.inject.Inject

class GetFavoritePagingBooksUseCase @Inject constructor(
    private val repository: BooksRepository
) {
    suspend operator fun invoke() = repository.getFavoritePagingBooks()
}