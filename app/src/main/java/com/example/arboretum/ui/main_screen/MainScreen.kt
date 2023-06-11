package com.example.arboretum.ui.main_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arboretum.domain.model.Plant
import com.example.arboretum.ui.components.Header
import com.example.arboretum.ui.components.MovieGrid
import com.example.arboretum.ui.main_activity.SnackbarAction
import com.example.arboretum.ui.theme.spacing


@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    showSnackbar: (String, SnackbarAction) -> Unit,
    navigateToDetails: (Plant) -> Unit,
    navigateToSearch: () -> Unit,
) {
    val plants by viewModel.plants.collectAsState()
    val context = LocalContext.current
    val gridState =
        rememberLazyGridState()
    val imageUri = remember { mutableStateOf(Uri.EMPTY) }
    val isLoading by viewModel.isLoading.collectAsState()
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                viewModel.changeIsLoading(true)
                val imagePath = (context.applicationInfo.dataDir + imageUri.value.path)
                    .replace("user/0", "data")
                    .replace("files", "files/images")
                viewModel.onCameraResult(imageUri.value, imagePath, navigateToDetails)
            }
        }
    val bottomNavigationItems = listOf(
        BottomNavigationItem.CameraItem to {
            val uri = PlantsFileProvider.getImageUri(context)
            imageUri.value = uri
            cameraLauncher.launch(uri)
        },
    )
    MainScreenContent(
        title = "Plants",
        plants = plants,
        gridState = gridState,
        failedPosterResponse = { errorMessage ->
            showSnackbar(errorMessage, SnackbarAction.Error)
        },
        plantCardOnClick = navigateToDetails,
        searchBtnOnClick = navigateToSearch,
        bottomNavigationItems = bottomNavigationItems,
        isLoading = isLoading
    )
}


@Composable
fun MainScreenContent(
    title: String,
    plants: List<Plant>,
    gridState: LazyGridState,
    failedPosterResponse: (String) -> Unit,
    plantCardOnClick: (Plant) -> Unit,
    searchBtnOnClick: () -> Unit,
    bottomNavigationItems: List<Pair<BottomNavigationItem, () -> Unit>>,
    isLoading: Boolean,
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            MovieGrid(
                modifier = Modifier,
                gridState = gridState,
                title = title,
                movies = plants,
                failedPosterResponse = failedPosterResponse,
                plantCardOnClick = plantCardOnClick,
            )
            AnimatedVisibility(visible = gridState.canScrollBackward,
                enter = slideInVertically { -it },
                exit = slideOutVertically { -it }) {
                Header(
                    title = title,
                    modifier = Modifier.statusBarsPadding(),
                    searchBtnOnClick = searchBtnOnClick
                )
            }
            NavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = spacing.large * 2)
                    .height(50.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                for (navItem in bottomNavigationItems) {
                    NavigationBarItem(
                        selected = false,
                        onClick = navItem.second,
                        icon = {
                            Icon(
                                painter = painterResource(id = navItem.first.iconId),
                                modifier = Modifier
                                    .padding(vertical = spacing.small)
                                    .fillMaxHeight(),
                                contentDescription = null
                            )
                        })
                }
            }
        }
    }
    StatusbarBackground()
}

@Composable
fun StatusbarBackground() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsTopHeight(WindowInsets.statusBars)
            .background(MaterialTheme.colorScheme.primary)
    )
}