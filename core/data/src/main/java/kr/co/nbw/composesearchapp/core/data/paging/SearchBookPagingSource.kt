package kr.co.nbw.composesearchapp.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kr.co.nbw.composesearchapp.core.data.api.SearchBookApi
import kr.co.nbw.composesearchapp.core.data.utils.Constants.PAGING_SIZE
import kr.co.nbw.composesearchapp.core.data.utils.Constants.STARTING_PAGE_INDEX
import kr.co.nbw.composesearchapp.core.data.utils.safeApiCall
import kr.co.nbw.composesearchapp.core.domain.model.ResultWrapper
import kr.co.nbw.composesearchapp.core.data.model.Book
import retrofit2.HttpException
import java.io.IOException

class SearchBookPagingSource(
    private val api: SearchBookApi,
    private val query: String
): PagingSource<Int, Book>() {
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = safeApiCall(Dispatchers.IO) {
                api.searchBooks(query, "recent", pageNumber, params.loadSize, "title")
            }
            when (response) {
                is ResultWrapper.Success -> {
                    val endOfPaginactionReached = response.value.meta.isEnd
                    val data = response.value.books
                    val prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1
                    val nextKey = if (endOfPaginactionReached) {
                        null
                    } else {
                        pageNumber + (params.loadSize / PAGING_SIZE)
                    }
                    LoadResult.Page(
                        data = data,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                is ResultWrapper.Error -> {
                    response.error?.let {
                        LoadResult.Error(it)
                    } ?: kotlin.run {
                        LoadResult.Invalid()
                    }
                }
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}