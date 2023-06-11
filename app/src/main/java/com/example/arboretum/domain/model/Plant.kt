package com.example.arboretum.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Date


@Parcelize
data class Plant(
    val id: Int,
    val name: String?,
    val imageUri: String?,
    val date: Date?,
    val genus: String?,
    val phylum: String?,
    val classPlant: String?,
) : Parcelable