package com.example.arboretum.ui.search_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arboretum.domain.model.Plant
import com.example.arboretum.ui.components.HEADER_HEIGHT
import com.example.arboretum.ui.components.MovieGrid
import com.example.arboretum.ui.components.SearchHeader
import com.example.arboretum.ui.main_activity.SnackbarAction
import com.example.arboretum.ui.main_screen.StatusbarBackground

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    showSnackbar: (String, SnackbarAction) -> Unit,
    navigateToDetails: (Plant) -> Unit,
    navigateUp: () -> Unit
) {
    val searchedPlants by viewModel.searchedPlants.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val gridState = rememberLazyGridState()


    SearchScreenContent(
        title = "Searched plants",
        movies = searchedPlants,
        gridState = gridState,
        failedPosterResponse = { errorMessage -> showSnackbar(errorMessage, SnackbarAction.Error) },
        plantCardOnClick = navigateToDetails,
        backBtnOnClick = navigateUp,
        searchText = searchText,
        searchTextChange = { viewModel.setSearchText(it) },
    )
}


@Composable
fun SearchScreenContent(
    title: String,
    movies: List<Plant>,
    gridState: LazyGridState,
    failedPosterResponse: (String) -> Unit,
    plantCardOnClick: (Plant) -> Unit,
    backBtnOnClick: () -> Unit,
    searchText: String,
    searchTextChange: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .fillMaxSize()
    ) {
        if (movies.isNotEmpty())
            MovieGrid(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = HEADER_HEIGHT.dp),
                gridState = gridState,
                title = title,
                movies = movies,
                failedPosterResponse = failedPosterResponse,
                plantCardOnClick = plantCardOnClick,
            )
        else EmptySearch()
        AnimatedVisibility(visible = true,
            enter = slideInVertically { -it },
            exit = slideOutVertically { -it }) {
            SearchHeader(
                text = searchText,
                textChange = searchTextChange,
                modifier = Modifier.statusBarsPadding(),
                backOnClick = backBtnOnClick,
            )
        }
        StatusbarBackground()
    }
}

@Composable
fun EmptySearch() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Empty", style = MaterialTheme.typography.displaySmall)
    }
}
