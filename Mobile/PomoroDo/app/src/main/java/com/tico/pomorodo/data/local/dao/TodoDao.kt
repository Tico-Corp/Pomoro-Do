package com.tico.pomorodo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tico.pomorodo.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<TodoEntity>)

    @Update
    suspend fun update(entity: TodoEntity)

    @Update
    suspend fun updateAll(entities: List<TodoEntity>)

    @Query("SELECT * FROM todo_table")
    fun getAllTodo(): Flow<List<TodoEntity>>

    @Query("SELECT * from todo_table WHERE id = :id")
    fun getTodo(id: Int): Flow<TodoEntity>

    @Query("DELETE FROM todo_table WHERE id = :id")
    suspend fun deleteTodo(id: Int)

    @Query("SELECT * FROM todo_table WHERE category_id = :categoryId")
    fun getTodosByCategory(categoryId: Int): Flow<List<TodoEntity>>
}