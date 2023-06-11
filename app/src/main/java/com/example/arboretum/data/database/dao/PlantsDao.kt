package com.example.arboretum.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.arboretum.data.database.entity.PlantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantsDao {
    @Query("SELECT * FROM plants ORDER BY date DESC")
    fun getAllPlants(): Flow<List<PlantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlant(plant: PlantEntity)

    @Delete
    fun deletePlant(plant: PlantEntity)
}