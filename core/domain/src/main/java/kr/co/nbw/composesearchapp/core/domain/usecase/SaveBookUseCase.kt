package kr.co.nbw.composesearchapp.core.domain.usecase

import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.core.domain.repository.BooksRepository
import javax.inject.Inject

class SaveBookUseCase @Inject constructor(
    private val repository: BooksRepository
) {
    suspend operator fun invoke(
        book: BookEntity
    ) = repository.saveBook(book)
}