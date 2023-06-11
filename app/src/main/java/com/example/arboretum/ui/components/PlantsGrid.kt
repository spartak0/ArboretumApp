package com.example.arboretum.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.arboretum.domain.model.Plant
import com.example.arboretum.ui.theme.spacing

@Composable
fun MovieGrid(
    modifier: Modifier,
    gridState: LazyGridState,
    title: String,
    movies: List<Plant>,
    failedPosterResponse: (String) -> Unit,
    plantCardOnClick: (Plant) -> Unit,
) {
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(140.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.small),
        horizontalArrangement = Arrangement.spacedBy(spacing.medium),
        contentPadding = PaddingValues(spacing.large),
        userScrollEnabled = movies.isNotEmpty(),
        modifier = modifier
    ) {
        itemTitle(title)
        itemMoviesGrid(movies, failedPosterResponse, plantCardOnClick)
        itemSpacer(128.dp)
    }
}

fun LazyGridScope.itemSpacer(large: Dp) {
    item(span = { GridItemSpan(this.maxLineSpan) }) {
        Spacer(modifier = Modifier.height(large))
    }
}

fun LazyGridScope.itemTitle(title: String) {
    item(span = { GridItemSpan(this.maxLineSpan) }) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

fun LazyGridScope.itemMoviesGrid(
    plants: List<Plant>,
    failedPosterResponse: (String) -> Unit,
    plantCardOnClick: (Plant) -> Unit
) {
    items(plants) { plant ->
        PlantCard(
            width = 0.dp,
            height = 250.dp,
            name = plant.name ?: "",
            year = plant.date.toString() ?: "",
            imageUrl = plant.imageUri ?: "",
            onError = failedPosterResponse,
            onClick = { plantCardOnClick(plant) },
        )
    }
}

fun LazyGridScope.itemEmptyGrid() {
    items(12) {
        EmptyMovieCard(width = 0.dp, height = 250.dp)
    }
}

fun Modifier.shimmerEffect(backgroundColor: Color): Modifier = composed {
    val shimmerColors = listOf(
        backgroundColor.copy(alpha = 0.6f),
        backgroundColor.copy(alpha = if (isSystemInDarkTheme()) 0.4f else 0.2f),
        backgroundColor.copy(alpha = 0.6f),
    )
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

