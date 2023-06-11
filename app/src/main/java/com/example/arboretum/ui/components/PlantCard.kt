package com.example.arboretum.ui.components

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


@Composable
fun PlantCard(
    width: Dp,
    height: Dp,
    name: String,
    year: String,
    imageUrl: String,
    onError: (String) -> Unit,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
    ) {
        Poster(
            imageUri = imageUrl,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .weight(1f),
            onError = onError
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = year, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun EmptyMovieCard(
    width: Dp,
    height: Dp,
    color: Color = MaterialTheme.colorScheme.secondaryContainer
) {
    Column(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect(backgroundColor = color)
                .fillMaxWidth()
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect(backgroundColor = color)
                .height(24.dp - 6.dp)
                .fillMaxWidth(),
        )
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect(backgroundColor = color)
                .height(24.dp - 6.dp)
                .width(50.dp),
        )

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Poster(
    imageUri: String,
    onError: (String) -> Unit,
    modifier: Modifier,
) {
    GlideImage(
        model = Uri.parse(imageUri),
        modifier = modifier,
        contentScale = ContentScale.Crop,
        contentDescription = null,
    ) {
        it.addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                e?.message?.let { msg -> onError(msg) }
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        })
    }
}