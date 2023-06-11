package com.example.arboretum.data.ml.repository

import android.graphics.Bitmap
import com.example.arboretum.data.ml.service.ModelService
import com.example.arboretum.domain.mapper.BitmapMapper
import com.example.arboretum.domain.mapper.MlPlantsMapper
import com.example.arboretum.domain.model.Plant
import com.example.arboretum.domain.repository.ModelRepository

class ModelRepositoryImpl(
    private val bitmapMapper: BitmapMapper,
    private val modelService: ModelService,
    private val mlPlantsMapper: MlPlantsMapper,
) : ModelRepository {
    override suspend fun predict(image: Bitmap): Plant {
        val bytebuffer = bitmapMapper.bitmapToByteBuffer(image)
        val modelPlant = modelService.predict(bytebuffer)
        return mlPlantsMapper.dataToDomain(modelPlant)
    }
}