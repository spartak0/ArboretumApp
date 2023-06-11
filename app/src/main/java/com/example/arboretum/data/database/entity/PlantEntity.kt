package com.example.arboretum.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.arboretum.data.database.converter.TimestampConverter
import java.sql.Date

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String?,
    val imageUri: String?,
    @TypeConverters(TimestampConverter::class)
    val date: Date?,
    val genus: String?,
    val phylum: String?,
    val classPlant: String?,
)