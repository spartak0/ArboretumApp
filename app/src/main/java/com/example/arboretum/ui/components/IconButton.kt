package com.example.arboretum.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource


@Composable
fun IconButton(
    modifier: Modifier,
    @DrawableRes icon: Int,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
) {
    androidx.compose.material3.IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painter = painterResource(id = icon),
            tint = tint,
            contentDescription = null
        )
    }
}
