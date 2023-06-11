package com.example.arboretum.domain.mapper

import com.example.arboretum.data.database.entity.PlantEntity
import com.example.arboretum.data.ml.model.ModelPlant
import com.example.arboretum.domain.model.Plant
import com.example.arboretum.utils.Mapper

class PlantsMapper : Mapper<PlantEntity, Plant> {
    override fun dataToDomain(data: PlantEntity): Plant = Plant(
        id = data.id,
        name = data.name,
        imageUri = data.imageUri,
        date = data.date,
        genus = data.genus,
        phylum = data.phylum,
        classPlant = data.classPlant,
    )


    override fun domainToData(domain: Plant): PlantEntity = PlantEntity(
        id = domain.id,
        name = domain.name,
        imageUri = domain.imageUri,
        date = domain.date,
        genus = domain.genus,
        phylum = domain.phylum,
        classPlant = domain.classPlant,
    )

}

class MlPlantsMapper : Mapper<ModelPlant, Plant> {
    override fun dataToDomain(data: ModelPlant): Plant = Plant(
        id = 0,
        name = data.name,
        imageUri = null,
        date = null,
        genus = data.genus,
        phylum = data.phylum,
        classPlant = data.classPlant,
    )

    override fun domainToData(domain: Plant): ModelPlant = ModelPlant(
        name = domain.name,
        genus = domain.genus,
        phylum = domain.phylum,
        classPlant = domain.classPlant,
    )
}