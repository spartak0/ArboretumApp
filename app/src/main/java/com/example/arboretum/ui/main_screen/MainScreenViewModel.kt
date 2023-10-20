package com.example.arboretum.ui.main_screen

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arboretum.domain.model.Plant
import com.example.arboretum.domain.repository.ModelRepository
import com.example.arboretum.domain.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.Date
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val modelRepository: ModelRepository,
) :
    ViewModel() {
    private val _plants =
        MutableStateFlow<List<Plant>>(listOf())
    val plants = _plants.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchPlants()
    }

    private fun fetchPlants() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getPlants().collect {
                _plants.value = it
            }
        }
    }

    private fun insertPlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.insertPlant(plant)
        }
    }

    fun onCameraResult(imageUri: Uri, imagePath: String, navigateToDetails: (Plant) -> Unit) {
        viewModelScope.launch {
            val differed = async(Dispatchers.IO) {
                val millis = System.currentTimeMillis()
                val predictPlant = predict(imagePath)
                val plant =
                    predictPlant.copy(
                        imageUri = imageUri.toString(),
                        date = Date(millis)
                    )
                plant
            }
            val plant = differed.await()
            navigateToDetails(plant)
            launch(Dispatchers.IO) {
                insertPlant(plant)
            }
            launch(Dispatchers.IO) {
                delay(1000)
                _isLoading.value = false
            }

        }
    }

    private suspend fun predict(path: String): Plant {
        val file = BitmapFactory.decodeFile(path)
        return modelRepository.predict(file)
    }

    fun changeIsLoading(boolean: Boolean) {
        _isLoading.value = boolean
    }
}
