package kr.co.nbw.composesearchapp.feature.favorite

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
import kr.co.nbw.composesearchapp.core.domain.usecase.GetFavoritePagingBooksUseCase
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritePagingBooksUseCase: GetFavoritePagingBooksUseCase,
    private val deleteBookUseCase: DeleteBookUseCase
): ViewModel() {
    private val _favoriteUiEffect = MutableSharedFlow<FavoriteUiEffect>(replay = 0)
    val favoriteUiEffect: SharedFlow<FavoriteUiEffect> get() = _favoriteUiEffect

    private val _favoriteUiState = MutableStateFlow<FavoriteUiState>(FavoriteUiState.Loading)
    val favoriteUiState: StateFlow<FavoriteUiState> get() = _favoriteUiState

    init {
        getFavoriteBooks()
    }

    fun getFavoriteBooks() {
        viewModelScope.launch {
            _favoriteUiState.value = FavoriteUiState.Loading
            when (val result = getFavoritePagingBooksUseCase()) {
                is ResultWrapper.Success -> {
                    _favoriteUiState.value = FavoriteUiState.Success(savedFavoriteBooks = result.value)
                }
                is ResultWrapper.Error -> {
                    _favoriteUiState.value = FavoriteUiState.Error(message = result.message)
                }
            }
        }
    }

    fun deleteBook(book: BookEntity) {
        viewModelScope.launch {
            val result = deleteBookUseCase(book)
            when(result) {
                is ResultWrapper.Success -> {
                    _favoriteUiEffect.emit(FavoriteUiEffect.ShowSnackBar("Book deleted"))
                    // TODO:: 이 부분이 필요한가?
                    getFavoriteBooks()
                }

                is ResultWrapper.Error -> {
                    _favoriteUiEffect.emit(FavoriteUiEffect.ShowSnackBar("Failed to delete book"))
                }
            }
        }
    }
}