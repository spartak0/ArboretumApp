package com.example.arboretum.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.arboretum.data.database.converter.TimestampConverter
import com.example.arboretum.data.database.dao.PlantsDao
import com.example.arboretum.data.database.entity.PlantEntity

@Database(entities = [PlantEntity::class], version = 7)
@TypeConverters(TimestampConverter::class)
abstract class PlantsDatabase : RoomDatabase() {
    abstract fun plantsDao(): PlantsDao
}