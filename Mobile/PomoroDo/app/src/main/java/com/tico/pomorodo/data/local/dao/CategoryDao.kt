package com.tico.pomorodo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tico.pomorodo.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryEntity)

    @Update
    suspend fun update(category: CategoryEntity)

    @Update
    suspend fun updateAll(entities: List<CategoryEntity>)

    @Query("DELETE FROM category_table WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM category_table")
    fun getAllCategory(): Flow<List<CategoryEntity>>

    @Query("SELECT * from category_table WHERE id = :id")
    fun getCategoryInfo(id: Int): Flow<CategoryEntity>
}