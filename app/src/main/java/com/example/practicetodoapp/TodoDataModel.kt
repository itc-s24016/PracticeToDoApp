package com.example.practicetodoapp
// １件分のToDoを表すデータ構造

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // キー
    val title: String, // タイトル
    val memo: String, // メモ
    val isCompleted: Boolean = false, // 完了状態
    val position: Int = 0 // 表示順
){
    val isNew: Boolean // 新規のToDoを表す
        get() = id == 0L
}

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY position")
    fun getAll(): Flow<List<Todo>>

    @Insert
    suspend fun insert(todo: Todo): Long

    @Update
    suspend fun update(todo: Todo)

    @Query("SELECT MAX(position) FROM todos")
    suspend fun getMaxPosition(): Int?

    @Query("DELETE FROM todos WHERE isCompleted = TRUE")
    suspend fun deleteCompletedTodos()
}

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao

    companion object{
        private var instance: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            instance?.let{return it}
            val db = Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "todo_database"
            ).build()
            instance = db
            return db
        }
    }
}