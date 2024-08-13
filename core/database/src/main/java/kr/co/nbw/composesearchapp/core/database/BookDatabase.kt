package kr.co.nbw.composesearchapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.co.nbw.composesearchapp.core.domain.model.BookEntity

@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(OrmConverter::class)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
}