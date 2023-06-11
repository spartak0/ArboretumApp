package com.example.arboretum.ui.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arboretum.domain.model.Plant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel() {
    private val _searchedPlants = MutableStateFlow<List<Plant>>(listOf())
    val searchedPlants = _searchedPlants.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun setSearchText(text: String) {
        viewModelScope.launch {
            _searchText.value = text
        }
    }

}