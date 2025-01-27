package com.tico.pomorodo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<TodoEntity>)

    @Update
    suspend fun update(entity: TodoEntity)

    @Query("DELETE FROM todo_table WHERE id = :id")
    suspend fun deleteTodo(id: Int)

    @Query(
        """
    SELECT * 
    FROM category_table 
    LEFT OUTER JOIN todo_table 
    ON category_table.id = todo_table.category_id 
    AND todo_table.target_date = :targetDate
    ORDER BY category_table.type ASC, category_table.id ASC, todo_table.created_at DESC
    """
    )
    fun getCategoryWithTodoItems(targetDate: LocalDate): Flow<Map<CategoryEntity, List<TodoEntity>>>
}