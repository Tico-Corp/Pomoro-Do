package com.tico.pomorodo.ui.setting.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.tico.pomorodo.BuildConfig
import com.tico.pomorodo.R
import com.tico.pomorodo.data.model.NameErrorType
import com.tico.pomorodo.domain.model.ProfileImageType
import com.tico.pomorodo.ui.auth.viewModel.AuthViewModel
import com.tico.pomorodo.ui.common.view.CustomTopAppBar
import com.tico.pomorodo.ui.common.view.EditableProfile
import com.tico.pomorodo.ui.common.view.PhotoChooseDialog
import com.tico.pomorodo.ui.common.view.clickableWithoutRipple
import com.tico.pomorodo.ui.common.view.createImageFile
import com.tico.pomorodo.ui.common.view.executeToast
import com.tico.pomorodo.ui.common.view.uriToFile
import com.tico.pomorodo.ui.theme.IC_OK
import com.tico.pomorodo.ui.theme.IC_UNOK
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.Objects

@Composable
fun ModifyProfileScreen(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    val authViewModel: AuthViewModel = hiltViewModel(navBackStackEntry)
    val context = LocalContext.current
    val initialInputText by authViewModel.name.collectAsState()
    val profileUri by authViewModel.profileUri.collectAsState()
    var inputText by remember { mutableStateOf(initialInputText) }
    var enable = inputText.isNotBlank() && initialInputText != inputText
    var showPhotoChooseDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var errorType by remember { mutableStateOf(NameErrorType.NONE) }
    var file by remember { mutableStateOf<File>(context.createImageFile()) }
    var uri by remember {
        mutableStateOf<Uri>(
            FileProvider.getUriForFile(
                Objects.requireNonNull(context),
                BuildConfig.APPLICATION_ID + ".provider", file
            )
        )
    }
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
            pickUri?.let {
                authViewModel.setProfile(pickUri, context.uriToFile(it), ProfileImageType.FILE)
            }
        }
    val takePhotoCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                authViewModel.setProfile(uri, file, ProfileImageType.FILE)
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
            isActionEnabled = enable,
            actionIconString = IC_OK,
            actionDisableIconString = IC_UNOK,
            actionIconDescriptionId = R.string.content_ic_ok,
            onActionClickedListener = {
                runBlocking {
                    authViewModel.setName(inputText)
                    navController.popBackStack()
                }
            },
            onBackClickedListener = { navController.popBackStack() }
        )

        EditableProfile(
            modifier = Modifier.padding(horizontal = 30.dp),
            profileUri = profileUri,
            onProfileClicked = { showPhotoChooseDialog = true },
            inputText = inputText,
            onInputTextChanged = {
                inputText = it
                errorType = authViewModel.nameValidate(it)
                enable = errorType == NameErrorType.NONE && initialInputText != inputText
            },
            errorType = errorType
        )
    }

    if (showPhotoChooseDialog) {
        PhotoChooseDialog(
            isDefaultImage = profileUri == null,
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
                authViewModel.setProfile(null, null, ProfileImageType.DEFAULT)
                showPhotoChooseDialog = false
            }
        )
    }

    Text(
        text = "회원 탈퇴",
        modifier = Modifier.clickableWithoutRipple { /*TODO: 회원 탈퇴 로직*/ },
        color = PomoroDoTheme.colorScheme.gray50,
        textAlign = TextAlign.Center,
        style = PomoroDoTheme.typography.laundryGothicRegular10
    )
}