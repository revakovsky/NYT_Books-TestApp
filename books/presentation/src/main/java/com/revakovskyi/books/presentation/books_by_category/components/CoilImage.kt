package com.revakovskyi.books.presentation.books_by_category.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import com.revakovskyi.books.presentation.R
import org.koin.compose.koinInject

@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    url: String,
    imageLoader: ImageLoader = koinInject(),
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp

    val imageWidth by rememberSaveable { mutableIntStateOf(screenWidthDp / 3) }


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(imageWidth.dp)
            .aspectRatio(0.66f)
            .clip(MaterialTheme.shapes.small)
    ) {

        SubcomposeAsyncImage(
            model = url,
            contentDescription = null,
            imageLoader = imageLoader,
            contentScale = ContentScale.Crop,
            loading = {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            error = {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.no_image),
                        contentDescription = stringResource(id = R.string.image_is_not_available),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

    }

}
