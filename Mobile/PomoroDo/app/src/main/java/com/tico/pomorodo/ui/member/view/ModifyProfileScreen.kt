package com.tico.pomorodo.ui.member.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.tico.pomorodo.BuildConfig
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.auth.viewModel.AuthViewModel
import com.tico.pomorodo.ui.common.view.CustomTopAppBar
import com.tico.pomorodo.ui.common.view.EditableProfile
import com.tico.pomorodo.ui.common.view.PhotoChooseDialog
import com.tico.pomorodo.ui.common.view.createImageFile
import com.tico.pomorodo.ui.common.view.executeToast
import com.tico.pomorodo.ui.theme.IC_OK
import com.tico.pomorodo.ui.theme.IC_UNOK
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.coroutines.runBlocking
import java.util.Objects

@Composable
fun ModifyProfileScreen(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    val authViewModel: AuthViewModel = hiltViewModel(navBackStackEntry)
    val context = LocalContext.current
    val initialInputText by authViewModel.name.collectAsState()
    val profile by authViewModel.profile.collectAsState()
    var inputText by remember { mutableStateOf(initialInputText) }
    val enable = inputText.isNotBlank() && initialInputText != inputText
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
                authViewModel.setProfile(pickUri)
            }
        }
    val takePhotoCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                authViewModel.setProfile(uri)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PomoroDoTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTopAppBar(
            titleTextId = R.string.title_modify_profile,
            enabled = enable,
            iconString = IC_OK,
            disableIconString = IC_UNOK,
            descriptionId = R.string.content_ic_ok,
            onClickedListener = {
                runBlocking {
                    authViewModel.setName(inputText)
                    navController.popBackStack()
                }
            },
            onBackClickedListener = { navController.popBackStack() }
        )

        EditableProfile(
            modifier = Modifier.padding(horizontal = 30.dp),
            profileUri = profile,
            onProfileClicked = { showPhotoChooseDialog = true },
            inputText = inputText,
            onInputTextChanged = { inputText = it }
        )
    }

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
                authViewModel.setProfile(null)
                showPhotoChooseDialog = false
            }
        )
    }
}