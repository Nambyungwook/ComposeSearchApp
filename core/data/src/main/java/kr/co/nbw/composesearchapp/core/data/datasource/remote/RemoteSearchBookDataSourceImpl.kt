package kr.co.nbw.composesearchapp.core.data.datasource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.nbw.composesearchapp.core.data.api.SearchBookApi
import kr.co.nbw.composesearchapp.core.data.paging.SearchBookPagingSource
import kr.co.nbw.composesearchapp.core.data.utils.Constants.PAGING_SIZE
import kr.co.nbw.composesearchapp.core.data.model.Book
import javax.inject.Inject

class RemoteSearchBookDataSourceImpl @Inject constructor(
    private val api: SearchBookApi
): RemoteSearchBookDataSource {
    override fun searchBooks(
        query: String
    ): Flow<PagingData<Book>> {
        val pagingSourceFactory = { SearchBookPagingSource(api, query) }

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}