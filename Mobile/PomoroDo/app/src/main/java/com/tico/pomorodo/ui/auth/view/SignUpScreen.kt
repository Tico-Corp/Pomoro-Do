package com.tico.pomorodo.ui.auth.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.iconpack.IcProfileDefault
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun SignUpScreen() {

}

@Preview
@Composable
fun IconDefaultProfile(defaultProfileUri: Uri? = null) {
    Box(modifier = Modifier
        .size(110.dp)
        .clip(shape = CircleShape)
        .clickable { /*TODO*/ }) {
        if (defaultProfileUri == null) {
            Icon(
                imageVector = IconPack.IcProfileDefault,
                contentDescription = stringResource(R.string.content_ic_profile_default),
                tint = Color.Unspecified
            )
        } else {
            GlideImage(
                imageModel = { defaultProfileUri },
                requestOptions = { RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0x80000000)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.content_profile_change),
                color = MaterialTheme.colorScheme.background,
                style = PomoroDoTheme.typography.laundryGothicRegular26
            )
        }
    }
}

@Preview
@Composable
fun ProfileEditTextBox() {

}