package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcProfileDefault
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun Profile(url: String, modifier: Modifier = Modifier, size: Int) {
    GlideImage(
        imageModel = { url },
        modifier = modifier
            .size(size.dp)
            .clip(shape = CircleShape),
        requestOptions = { RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) },
        imageOptions = ImageOptions(
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    )
}

@Composable
fun ProfileVertical(
    modifier: Modifier = Modifier,
    size: Int,
    defaultProfileUri: String? = null,
    name: String,
    textStyle: TextStyle,
    isClicked: Boolean = false
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .border(
                    1.5.dp,
                    color = if (isClicked) PomoroDoTheme.colorScheme.primaryContainer else Color.Unspecified,
                    shape = CircleShape
                )
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (defaultProfileUri == null) {
                SimpleIcon(
                    modifier = Modifier,
                    size = size,
                    imageVector = IconPack.IcProfileDefault,
                    contentDescriptionId = R.string.content_ic_profile_default
                )
            } else {
                GlideImage(
                    modifier = Modifier.size(size.dp),
                    imageModel = { defaultProfileUri },
                    requestOptions = { RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                )
            }
        }
        SimpleText(
            modifier = Modifier.width(size.dp),
            text = name,
            style = textStyle,
            color = PomoroDoTheme.colorScheme.onBackground,
            isEllipsis = true,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProfileHorizontal(
    modifier: Modifier = Modifier,
    size: Int,
    defaultProfileUri: String? = null,
    name: String,
    textStyle: TextStyle
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (defaultProfileUri == null) {
            SimpleIcon(
                modifier = Modifier.clip(CircleShape),
                size = size,
                imageVector = IconPack.IcProfileDefault,
                contentDescriptionId = R.string.content_ic_profile_default
            )
        } else {
            GlideImage(
                modifier = Modifier.size(size.dp),
                imageModel = { defaultProfileUri },
                requestOptions = { RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )
        }
        SimpleText(
            text = name,
            style = textStyle,
            color = PomoroDoTheme.colorScheme.onBackground
        )
    }
}