package com.tico.pomorodo.ui.auth.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.tico.pomorodo.BuildConfig
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.auth.viewModel.AuthViewModel
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.EditableProfile
import com.tico.pomorodo.ui.common.view.createImageFile
import com.tico.pomorodo.ui.common.view.executeToast
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import java.util.Objects


@Composable
fun SignUpRoute(
    navBackStackEntry: NavBackStackEntry,
    viewModel: AuthViewModel = hiltViewModel(navBackStackEntry),
    navigate: () -> Unit
) {
    val context = LocalContext.current
    val inputText by viewModel.name.collectAsState()
    val profile by viewModel.profile.collectAsState()
    val enable = inputText.isNotBlank()
    var showPhotoChooseDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var file = context.createImageFile()
    var uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
    var grantCameraState by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val pickPhotoLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { pickUri ->
            if (pickUri != null) {
                viewModel.setProfile(pickUri)
            }
        }
    val takePhotoCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                viewModel.setProfile(uri)
                file = context.createImageFile()
                uri = FileProvider.getUriForFile(
                    Objects.requireNonNull(context),
                    BuildConfig.APPLICATION_ID + ".provider", file
                )
            } else {
                context.executeToast(messageId = R.string.content_try_one_more)
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        grantCameraState = granted
        if (granted) {
            takePhotoCameraLauncher.launch(uri)
        } else {
            context.executeToast(messageId = R.string.content_please_permission)
        }
    }

    Surface(color = PomoroDoTheme.colorScheme.background) {
        Column(
            modifier = Modifier,
        ) {
            if (showPhotoChooseDialog) {
                PhotoChooseDialog(
                    isDefaultImage = profile == null,
                    onDismissRequest = { showPhotoChooseDialog = false },
                    onTakePhotoClicked = {
                        if (grantCameraState) {
                            takePhotoCameraLauncher.launch(uri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                        showPhotoChooseDialog = false
                    },
                    onPickPhotoClicked = {
                        pickPhotoLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                        showPhotoChooseDialog = false
                    },
                    onApplyDefaultImageClicked = {
                        viewModel.setProfile(null)
                        showPhotoChooseDialog = false
                    }
                )
            }
            SignUpScreen(
                profileUri = profile,
                onProfileClicked = { showPhotoChooseDialog = true },
                inputText = inputText,
                onInputTextChanged = viewModel::setName,
                enable = enable,
                onSignUpButtonClicked = navigate
            )
        }
    }
}

@Composable
fun SignUpScreen(
    profileUri: Uri?,
    onProfileClicked: () -> Unit,
    inputText: String,
    onInputTextChanged: (String) -> Unit,
    enable: Boolean,
    onSignUpButtonClicked: () -> Unit
) {
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
        EditableProfile(
            profileUri = profileUri,
            onProfileClicked = onProfileClicked,
            inputText = inputText,
            onInputTextChanged = onInputTextChanged
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextButton(
            text = stringResource(id = R.string.content_button_sign_up),
            enabled = enable,
            containerColor = PomoroDoTheme.colorScheme.primaryContainer,
            contentColor = Color.White,
            disabledContainerColor = PomoroDoTheme.colorScheme.gray70,
            disabledContentColor = PomoroDoTheme.colorScheme.background,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular18,
            verticalPadding = 12.dp,
            onClick = onSignUpButtonClicked
        )
    }
}