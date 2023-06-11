package com.example.arboretum.domain.repository

import android.graphics.Bitmap
import com.example.arboretum.domain.model.Plant

interface ModelRepository {
    suspend fun predict(image: Bitmap): Plant
}