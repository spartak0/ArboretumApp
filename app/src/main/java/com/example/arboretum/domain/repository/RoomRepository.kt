package com.example.arboretum.domain.repository

import com.example.arboretum.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun getPlants():Flow<List<Plant>>
    suspend fun insertPlant(plant: Plant)
    suspend fun deletePlant(plant: Plant)
}