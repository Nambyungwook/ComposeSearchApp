package kr.co.nbw.composesearchapp.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.core.domain.model.ResultWrapper
import kr.co.nbw.composesearchapp.core.domain.usecase.DeleteBookUseCase
import kr.co.nbw.composesearchapp.core.domain.usecase.GetFavoriteBooksUseCase
import kr.co.nbw.composesearchapp.core.domain.usecase.SaveBookUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val saveBookUseCase: SaveBookUseCase,
    private val deleteBookUseCase: DeleteBookUseCase,
    private val getFavoriteBooksUseCase: GetFavoriteBooksUseCase
): ViewModel() {
    private val _detailUiEffect = MutableSharedFlow<DetailUiEffect>(replay = 0)
    val detailUiEffect: SharedFlow<DetailUiEffect> get() = _detailUiEffect

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> get() = _detailUiState

    fun saveBook(book: BookEntity) {
        viewModelScope.launch {
            val result = saveBookUseCase(book)

            when (result) {
                is ResultWrapper.Success -> {
                    _detailUiEffect.emit(DetailUiEffect.ShowSnackBar("Book saved"))
                }
                is ResultWrapper.Error -> {
                    _detailUiEffect.emit(DetailUiEffect.ShowSnackBar("Failed to save book"))
                }
            }
        }
    }

    fun deleteBook(book: BookEntity) {
        viewModelScope.launch {
            val result = deleteBookUseCase(book)

            when (result) {
                is ResultWrapper.Success -> {
                    _detailUiEffect.emit(DetailUiEffect.ShowSnackBar("Book deleted"))
                }
                is ResultWrapper.Error -> {
                    _detailUiEffect.emit(DetailUiEffect.ShowSnackBar("Failed to delete book"))
                }
            }
        }
    }

    fun initDetailScreen(book: BookEntity) {
        viewModelScope.launch {
            when (val result = getFavoriteBooksUseCase()) {
                is ResultWrapper.Success -> {
                    result.value.collect { favoriteBooks ->
                        val isFavoriteBook = favoriteBooks.any { it.isbn == book.isbn }
                        _detailUiState.value = DetailUiState.Success(isFavoriteBook, book)
                    }
                }
                is ResultWrapper.Error -> {
                    _detailUiState.value = DetailUiState.Error(result.message)
                    _detailUiEffect.emit(DetailUiEffect.ShowSnackBar("Failed to get favorite books"))
                }
            }
        }
    }
}