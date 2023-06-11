package com.example.arboretum.di

import android.content.Context
import androidx.room.Room
import com.example.arboretum.data.database.converter.TimestampConverter
import com.example.arboretum.data.database.dao.PlantsDao
import com.example.arboretum.data.database.db.PlantsDatabase
import com.example.arboretum.data.database.repository.RoomRepositoryImpl
import com.example.arboretum.domain.mapper.PlantsMapper
import com.example.arboretum.domain.repository.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlantsRepositoryModule {

    @Provides
    @Singleton
    fun providePlantsMapper(): PlantsMapper {
        return PlantsMapper()
    }

    @Provides
    @Singleton
    fun providePlantsDatabase(@ApplicationContext context: Context): PlantsDatabase {
        return Room.databaseBuilder(
            context,
            PlantsDatabase::class.java, "plants_database"
        )
            .addTypeConverter(TimestampConverter())
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePlantsDao(plantsDatabase: PlantsDatabase): PlantsDao {
        return plantsDatabase.plantsDao()
    }

    @Provides
    @Singleton
    fun provideRoomRepository(plantsDao: PlantsDao, plantsMapper: PlantsMapper): RoomRepository {
        return RoomRepositoryImpl(plantsDao, plantsMapper)
    }
}