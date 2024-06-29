package com.tico.pomorodo.ui.auth.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcProfileDefault
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.theme.laundryGothic

@Composable
fun SignUpScreen() {
    val text = rememberSaveable { mutableStateOf("") }
    val enable = text.value.isNotBlank()

    Column(
        modifier = Modifier.padding(horizontal = 30.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_sign_up),
            color = PomoroDoTheme.colorScheme.onBackground,
            style = PomoroDoTheme.typography.laundryGothicBold20
        )
        Spacer(modifier = Modifier.height(20.dp))
        IconDefaultProfile()
        Spacer(modifier = Modifier.height(18.dp))
        ProfileEditText(text)
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextButton(
            text = stringResource(id = R.string.content_button_sign_up),
            enabled = enable,
            containerColor = PomoroDoTheme.colorScheme.primaryContainer,
            contentColor = PomoroDoTheme.colorScheme.background,
            disabledContainerColor = PomoroDoTheme.colorScheme.gray70,
            disabledContentColor = PomoroDoTheme.colorScheme.background,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            verticalPadding = 12.dp
        ) { /*TODO*/ }
    }
}

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
                .background(color = PomoroDoTheme.colorScheme.trim),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditText(text: MutableState<String>) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text.value,
        onValueChange = { text.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp),
        textStyle = PomoroDoTheme.typography.laundryGothicRegular16,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        interactionSource = interactionSource
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = text.value,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            isError = false,
            label = {
                Text(
                    text = stringResource(R.string.content_user_name_label),
                    fontFamily = laundryGothic
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.content_user_name_placeholder),
                    color = PomoroDoTheme.colorScheme.gray50,
                    style = PomoroDoTheme.typography.laundryGothicRegular16
                )
            },
            shape = RoundedCornerShape(size = 5.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = PomoroDoTheme.colorScheme.background,
                unfocusedContainerColor = PomoroDoTheme.colorScheme.background,
                focusedIndicatorColor = PomoroDoTheme.colorScheme.primaryContainer,
                focusedLabelColor = PomoroDoTheme.colorScheme.primaryContainer
            ),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 14.dp)
        )
    }
}