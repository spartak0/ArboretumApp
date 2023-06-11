package com.example.arboretum.ui.details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arboretum.domain.model.Plant
import com.example.arboretum.domain.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(private val roomRepository: RoomRepository) :
    ViewModel() {
    fun deletePlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.deletePlant(plant)
        }
    }
}