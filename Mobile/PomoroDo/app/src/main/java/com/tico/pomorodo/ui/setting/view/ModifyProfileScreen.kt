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
import androidx.compose.runtime.LaunchedEffect
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
import com.tico.pomorodo.domain.model.ProfileImageType
import com.tico.pomorodo.ui.common.view.CustomTopAppBar
import com.tico.pomorodo.ui.common.view.EditableProfile
import com.tico.pomorodo.ui.common.view.NameErrorType
import com.tico.pomorodo.ui.common.view.PhotoChooseDialog
import com.tico.pomorodo.ui.common.view.checkNameValidation
import com.tico.pomorodo.ui.common.view.clickableWithoutRipple
import com.tico.pomorodo.ui.common.view.createImageFile
import com.tico.pomorodo.ui.common.view.executeToast
import com.tico.pomorodo.ui.common.view.uriToFile
import com.tico.pomorodo.ui.member.viewmodel.MyPageViewModel
import com.tico.pomorodo.ui.theme.IC_OK
import com.tico.pomorodo.ui.theme.IC_UNOK
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.Objects

@Composable
fun ModifyProfileScreen(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    val myPageViewModel: MyPageViewModel = hiltViewModel(navBackStackEntry)
    val context = LocalContext.current
    val userProfile by myPageViewModel.userProfile.collectAsState()

    // 유저 네임
    var inputText by remember { mutableStateOf(userProfile?.nickname ?: "") }
    var enable = inputText.isNotBlank() && userProfile?.nickname != inputText
    var errorType by remember { mutableStateOf(NameErrorType.NONE) }

    // 유저 프로필 이미지
    var profileImageType by remember { mutableStateOf(ProfileImageType.GOOGLE) }
    var profileUri by remember { mutableStateOf(userProfile?.profileImageUrl?.let { Uri.parse(it) }) }
    var profileFile by remember { mutableStateOf<File?>(context.createImageFile()) }
    var showPhotoChooseDialog by rememberSaveable {
        mutableStateOf(false)
    }

    // 촬영하거나 갤러리에서 선택한 사진을 임시 저장할 파일과 경로 변수
    var file by remember { mutableStateOf(context.createImageFile()) }
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
                profileImageType = ProfileImageType.FILE
                profileUri = it
                profileFile = context.uriToFile(it)
            }
        }
    val takePhotoCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                profileFile?.let {
                    profileImageType = ProfileImageType.FILE
                    profileFile = file
                    profileUri = uri

                    file = context.createImageFile()
                    uri = FileProvider.getUriForFile(
                        Objects.requireNonNull(context),
                        BuildConfig.APPLICATION_ID + ".provider", file
                    )
                }
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

    LaunchedEffect(key1 = userProfile) {
        inputText = userProfile?.nickname ?: ""
        profileUri = userProfile?.profileImageUrl?.let { Uri.parse(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp)
            .background(PomoroDoTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
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
                        //TODO: 마이페이지 프로필 수정
                        myPageViewModel.updateUserProfile(
                            nickname = inputText,
                            profileUri = profileUri
                        )
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
                    errorType = it.checkNameValidation()
                    enable = errorType == NameErrorType.NONE && userProfile?.nickname != inputText
                },
                errorType = errorType
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
                profileImageType = ProfileImageType.DEFAULT
                profileUri = null
                profileFile = null
                showPhotoChooseDialog = false
            }
        )
    }
}