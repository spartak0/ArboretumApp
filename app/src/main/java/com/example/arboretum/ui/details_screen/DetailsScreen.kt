package com.example.arboretum.ui.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arboretum.R
import com.example.arboretum.domain.model.Plant
import com.example.arboretum.ui.components.IconButton
import com.example.arboretum.ui.components.Poster
import com.example.arboretum.ui.main_activity.SnackbarAction
import com.example.arboretum.ui.theme.spacing

@Composable
fun DetailsScreen(
    plant: Plant,
    navigateUp: () -> Unit,
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    showSnackbar: (String, SnackbarAction) -> Unit,
) {
    val widthPoster = LocalConfiguration.current.screenWidthDp + 1
    val heightPoster = widthPoster * 1.5
    var showAlertDialog by remember { mutableStateOf(false) }
    DetailsScreenContent(
        heightPoster = heightPoster.dp,
        widthPoster = widthPoster.dp,
        showAlertDialog = showAlertDialog,
        changeAlertDialogVisabl = { show -> showAlertDialog = show },
        plant = plant,
        onError = { message -> showSnackbar(message, SnackbarAction.Error) },
        backOnClick = navigateUp,
        trashOnClick = {
            showAlertDialog = true
        },
        deletePlant = { plant ->
            viewModel.deletePlant(plant)
            navigateUp()
        }
    )
}

@Composable
fun DetailsScreenContent(
    heightPoster: Dp,
    widthPoster: Dp,
    showAlertDialog: Boolean,
    changeAlertDialogVisabl: (Boolean) -> Unit,
    plant: Plant,
    onError: (String) -> Unit,
    backOnClick: () -> Unit,
    trashOnClick: () -> Unit,
    deletePlant: (Plant) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            ImagePoster(
                modifier = Modifier
                    .width(widthPoster)
                    .height(heightPoster)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                plant = plant,
                onError = onError,
                trashOnClick = trashOnClick,
                backOnClick = backOnClick,
            )
            Description(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(spacing.medium), plant = plant
            )

        }
        if (showAlertDialog) {
            ConfirmDialog(
                onDismissRequest = { changeAlertDialogVisabl(false) },
                confirmOnClick = {
                    changeAlertDialogVisabl(false)
                    deletePlant(plant)
                },
                dismissBtnOnClick = { changeAlertDialogVisabl(false) },
            )
        }
    }

}

@Composable
fun ConfirmDialog(
    onDismissRequest: () -> Unit,
    confirmOnClick: () -> Unit,
    dismissBtnOnClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = confirmOnClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)
            ) {
                Text(text = "ДА", color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            Button(onClick = dismissBtnOnClick) {
                Text(text = "НЕТ")
            }
        },
        title = { Text(text = "Подтверждение действия") },
        text = { Text("Вы действительно хотите удалить выбранный элемент?") },
    )
}

@Composable
fun ImagePoster(
    modifier: Modifier,
    plant: Plant,
    onError: (String) -> Unit,
    backOnClick: () -> Unit,
    trashOnClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Poster(
            imageUri = plant.imageUri ?: "",
            onError = { error -> onError(error) },
            modifier = Modifier.fillMaxSize()
        )
        BackIconBtn(
            modifier = Modifier
                .padding(
                    top = 24.dp + spacing.medium, start = spacing.medium
                )
                .align(Alignment.TopStart)
                .border(
                    1.dp, Color.White, CircleShape
                ),
            onClick = backOnClick
        )
        TrashIconBtn(
            modifier = Modifier
                .padding(
                    top = 24.dp + spacing.medium, end = spacing.medium
                )
                .align(Alignment.TopEnd)
                .border(
                    1.dp, Color.White, CircleShape
                ),
            onClick = trashOnClick
        )
    }
}

@Composable
fun Description(modifier: Modifier, plant: Plant) {
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = plant.name.toString(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp
                )
            )
            Text(
                text = "${plant.date}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(spacing.small))
        Text(text = "Genus: ${plant.genus}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(spacing.small))
        Text(text = "Class: ${plant.classPlant}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(spacing.small))
        Text(text = "Phylum: ${plant.phylum}", style = MaterialTheme.typography.bodyLarge)

    }
}

@Composable
fun BackIconBtn(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier,
        icon = R.drawable.baseline_arrow_back_24,
        tint = Color.White,
        onClick = onClick,
    )
}

@Composable
fun TrashIconBtn(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier,
        icon = R.drawable.baseline_delete_24,
        tint = Color.White,
        onClick = onClick,
    )
}