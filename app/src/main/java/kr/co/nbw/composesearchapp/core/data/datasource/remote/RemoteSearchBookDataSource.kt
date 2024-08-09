package kr.co.nbw.composesearchapp.core.data.datasource.remote

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.nbw.composesearchapp.core.data.model.Book

interface RemoteSearchBookDataSource {
    fun searchBooks(
        query: String,
    ): Flow<PagingData<Book>>
}