package kr.co.nbw.composesearchapp.core.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Delete
    suspend fun deleteBook(book: BookEntity)

    // BookEntity를 contains로 확인하는 방식으로 즐겨찾기 여부를 확인하고 있음
    @Query("SELECT * FROM books")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    // 전체 즐겨찾기한 책 목록을 가져오는 PagingSource
    @Query("SELECT * FROM books")
    fun getFavoritePagingBooks(): PagingSource<Int, BookEntity>
}