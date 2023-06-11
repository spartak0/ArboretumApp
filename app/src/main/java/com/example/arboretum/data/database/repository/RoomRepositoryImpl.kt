package com.example.arboretum.data.database.repository

import com.example.arboretum.data.database.dao.PlantsDao
import com.example.arboretum.domain.mapper.PlantsMapper
import com.example.arboretum.domain.model.Plant
import com.example.arboretum.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomRepositoryImpl(private val plantsDao: PlantsDao, private val plantsMapper: PlantsMapper) :
    RoomRepository {
    override suspend fun getPlants(): Flow<List<Plant>> {
        return plantsDao.getAllPlants().map { list ->
            list.map {
                plantsMapper.dataToDomain(it)
            }
        }
    }

    override suspend fun insertPlant(plant: Plant) {
        plantsDao.insertPlant(plantsMapper.domainToData(plant))
    }

    override suspend fun deletePlant(plant: Plant) {
        plantsDao.deletePlant(plantsMapper.domainToData(plant))
    }
}