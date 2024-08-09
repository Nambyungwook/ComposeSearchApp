package kr.co.nbw.composesearchapp.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity
import kr.co.nbw.composesearchapp.core.domain.model.ResultWrapper
import kr.co.nbw.composesearchapp.core.domain.usecase.DeleteBookUseCase
import kr.co.nbw.composesearchapp.core.domain.usecase.GetFavoriteBooksUseCase
import kr.co.nbw.composesearchapp.core.domain.usecase.SaveBookUseCase
import kr.co.nbw.composesearchapp.core.domain.usecase.SearchBooksUseCase
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase,
    private val saveBookUseCase: SaveBookUseCase,
    private val deleteBookUseCase: DeleteBookUseCase,
    private val getFavoriteBooksUseCase: GetFavoriteBooksUseCase
): ViewModel() {
    private val _searchUiEffect = MutableSharedFlow<SearchUiEffect>(replay = 0)
    val searchUiEffect: SharedFlow<SearchUiEffect> get() = _searchUiEffect

    private val _searchUiState = MutableStateFlow<SearchUiState>(
        SearchUiState.Initial(
            savedFavoriteBooks = emptyFlow()
        )
    )
    val searchUiState: StateFlow<SearchUiState> get() = _searchUiState

    init {
        getFavoriteBooks()
    }

    fun searchBooks(query: String) {
        _searchUiState.value = SearchUiState.Loading
        viewModelScope.launch {
            val booksPagingDataFlow = searchBooksUseCase(query).cachedIn(viewModelScope)

            if (searchUiState.value is SearchUiState.Result) {
                _searchUiState.value = (searchUiState.value as SearchUiState.Result).copy(books = booksPagingDataFlow)
            } else {
                _searchUiState.value = SearchUiState.Result(
                    books = booksPagingDataFlow,
                    savedFavoriteBooks = getFavoriteBooksUseCase().let {
                        if (it is ResultWrapper.Success) {
                            it.value
                        } else {
                            _searchUiEffect.emit(SearchUiEffect.ShowSnackBar("Failed to get favorite books"))
                            emptyFlow()
                        }
                    }
                )
            }
        }
    }

    fun saveBook(book: BookEntity) {
        viewModelScope.launch {
            val result = saveBookUseCase(book)

            when(result) {
                is ResultWrapper.Success -> {
                    _searchUiEffect.emit(SearchUiEffect.ShowSnackBar("Book saved"))
                }

                is ResultWrapper.Error -> {
                    _searchUiEffect.emit(SearchUiEffect.ShowSnackBar("Failed to save book"))
                }
            }
        }
    }

    fun deleteBook(book: BookEntity) {
        viewModelScope.launch {
            val result = deleteBookUseCase(book)

            when(result) {
                is ResultWrapper.Success -> {
                    _searchUiEffect.emit(SearchUiEffect.ShowSnackBar("Book deleted"))
                }

                is ResultWrapper.Error -> {
                    _searchUiEffect.emit(SearchUiEffect.ShowSnackBar("Failed to delete book"))
                }
            }
        }
    }

    fun getFavoriteBooks() {
        viewModelScope.launch {
            when(val result = getFavoriteBooksUseCase()) {
                is ResultWrapper.Success -> {
                    when (val oldSearchUiState = searchUiState.value) {
                        is SearchUiState.Initial -> {
                            _searchUiState.value = SearchUiState.Initial(
                                savedFavoriteBooks = result.value
                            )
                        }

                        is SearchUiState.Result -> {
                            _searchUiState.value = oldSearchUiState.copy(
                                savedFavoriteBooks = result.value
                            )
                        }

                        else -> {
                            _searchUiState.value = SearchUiState.Initial(
                                savedFavoriteBooks = result.value
                            )
                        }
                    }
                }

                is ResultWrapper.Error -> {
                    _searchUiState.value = SearchUiState.Error(
                        message = result.error?.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}