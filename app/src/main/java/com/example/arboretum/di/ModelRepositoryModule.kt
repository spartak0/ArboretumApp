package com.example.arboretum.di

import android.content.Context
import com.example.arboretum.data.ml.repository.ModelRepositoryImpl
import com.example.arboretum.data.ml.service.ModelService
import com.example.arboretum.domain.mapper.BitmapMapper
import com.example.arboretum.domain.mapper.MlPlantsMapper
import com.example.arboretum.domain.repository.ModelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModelRepositoryModule {
    @Provides
    @Singleton
    fun provideBitmapMapper(): BitmapMapper {
        return BitmapMapper()
    }

    @Provides
    @Singleton
    fun provideModelService(@ApplicationContext context: Context): ModelService {
        return ModelService(context)
    }

    @Provides
    @Singleton
    fun provideMlPlantsMapper(): MlPlantsMapper {
        return MlPlantsMapper()
    }

    @Provides
    @Singleton
    fun provideModelRepository(
        bitmapMapper: BitmapMapper,
        modelService: ModelService,
        mlPlantsMapper: MlPlantsMapper,
    ): ModelRepository {
        return ModelRepositoryImpl(bitmapMapper, modelService, mlPlantsMapper)
    }

}