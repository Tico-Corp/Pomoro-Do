package com.tico.pomorodo.ui.follow.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomTextField
import com.tico.pomorodo.ui.common.view.CustomTopAppBarWithNavigation
import com.tico.pomorodo.ui.common.view.SimpleAlertDialog
import com.tico.pomorodo.ui.common.view.SimpleIconButton
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.member.viewmodel.FollowViewModel
import com.tico.pomorodo.ui.theme.IC_ALL_CLEAN
import com.tico.pomorodo.ui.theme.IC_SEARCH
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun AddFollowerScreen(popBackToFollowScreen: () -> Unit) {
    val followViewModel: FollowViewModel = hiltViewModel()
    val followingList by followViewModel.followingList.collectAsState()
    var unFollowingDialogVisible by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }

    var searchName by remember { mutableStateOf("") }
    val textFieldColors = TextFieldDefaults.colors(
        focusedTextColor = PomoroDoTheme.colorScheme.onBackground,
        unfocusedTextColor = PomoroDoTheme.colorScheme.onBackground,
        disabledTextColor = PomoroDoTheme.colorScheme.dialogGray20,
        errorTextColor = PomoroDoTheme.colorScheme.error50,
        focusedContainerColor = PomoroDoTheme.colorScheme.dialogGray90,
        unfocusedContainerColor = PomoroDoTheme.colorScheme.dialogGray90,
        cursorColor = PomoroDoTheme.colorScheme.primaryContainer,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
    )

    Column(
        modifier = Modifier.background(color = PomoroDoTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        CustomTopAppBarWithNavigation(
            title = stringResource(R.string.title_add_following),
            navigationAction = popBackToFollowScreen,
            top = 24,
            start = 16,
            end = 16
        )

        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            value = searchName,
            onValueChange = { searchName = it },
            placeholder = {
                SimpleText(
                    text = stringResource(id = R.string.content_search_group_member),
                    style = PomoroDoTheme.typography.laundryGothicRegular14,
                    color = PomoroDoTheme.colorScheme.gray50
                )
            },
            colors = textFieldColors,
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp
            ),
            shape = RoundedCornerShape(5.dp),
            trailingIcon = {
                Row {
                    if (searchName.isNotBlank()) {
                        SimpleIconButton(
                            size = 34,
                            imageVector = requireNotNull(PomoroDoTheme.iconPack[IC_ALL_CLEAN]),
                            contentDescriptionId = R.string.content_ic_all_clean,
                            enabled = true,
                            onClickedListener = { searchName = "" }
                        )
                    }

                    SimpleIconButton(
                        size = 24,
                        imageVector = requireNotNull(PomoroDoTheme.iconPack[IC_SEARCH]),
                        contentDescriptionId = R.string.content_ic_search,
                        enabled = searchName.isNotBlank(),
                        onClickedListener = { /*TODO: */ }
                    )
                }
            },
            textStyle = PomoroDoTheme.typography.laundryGothicRegular14
        )

        FollowingList(
            followList = followingList,
            onClick = { index, isFollowing ->
                if (isFollowing) {
                    selectedIndex = index
                    unFollowingDialogVisible = true
                } else {
                    followViewModel.toggleFollowState(index)
                }
            }
        )
    }

    if (unFollowingDialogVisible) {
        SimpleAlertDialog(
            dialogTitleId = R.string.title_unfollow_dialog,
            confirmTextId = R.string.content_unfollow,
            dismissTextId = R.string.content_cancel,
            onConfirmation = {
                followViewModel.toggleFollowState(selectedIndex)
                unFollowingDialogVisible = false
            },
            onDismissRequest = { unFollowingDialogVisible = false },
            content = {
                Text(
                    text = stringResource(R.string.content_unfollow_dialog),
                    color = PomoroDoTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = PomoroDoTheme.typography.laundryGothicRegular14
                )
            })
    }
}

//@Composable
//fun FollowingList(followList: List<Follow>, onClick: (Int, Boolean) -> Unit) {
//    LazyColumn(
//        modifier = Modifier
//            .padding(horizontal = 18.dp)
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.spacedBy(18.dp)
//    ) {
//        itemsIndexed(items = followList) { index, user ->
//            if (index == 0)
//                Spacer(modifier = Modifier.height(18.dp))
//
//            FollowItem(
//                user = user,
//                followButtonText = stringResource(id = R.string.title_following),
//                unFollowButtonText = stringResource(id = R.string.title_follow),
//                followButtonContainerColor = PomoroDoTheme.colorScheme.gray90,
//                followButtonContentColor = PomoroDoTheme.colorScheme.gray20,
//                unFollowButtonContainerColor = PomoroDoTheme.colorScheme.primaryContainer,
//                unFollowButtonContentColor = Color.White,
//                onClick = { onClick(index, user.isFollowing) }
//            )
//        }
//    }
//}
//
//@Composable
//fun FollowItem(
//    user: Follow,
//    followButtonText: String,
//    unFollowButtonText: String? = null,
//    followButtonContainerColor: Color,
//    followButtonContentColor: Color,
//    unFollowButtonContainerColor: Color? = null,
//    unFollowButtonContentColor: Color? = null,
//    onClick: () -> Unit
//) {
//    val buttonText =
//        if (user.isFollowing) followButtonText
//        else unFollowButtonText ?: followButtonText
//    val buttonContainerColor =
//        if (user.isFollowing) followButtonContainerColor
//        else unFollowButtonContainerColor ?: followButtonContainerColor
//    val buttonContentColor =
//        if (user.isFollowing) followButtonContentColor
//        else unFollowButtonContentColor ?: followButtonContentColor
//
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.Start,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        GlideImage(
//            imageModel = { user.profileUrl },
//            modifier = Modifier
//                .size(48.dp)
//                .clip(shape = CircleShape),
//            requestOptions = { RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) },
//            imageOptions = ImageOptions(
//                contentScale = ContentScale.Crop,
//                alignment = Alignment.Center
//            )
//        )
//
//        Text(
//            text = user.name,
//            color = PomoroDoTheme.colorScheme.onBackground,
//            textAlign = TextAlign.Start,
//            style = PomoroDoTheme.typography.laundryGothicRegular18
//        )
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        CustomTextButton(
//            text = buttonText,
//            containerColor = buttonContainerColor,
//            contentColor = buttonContentColor,
//            textStyle = PomoroDoTheme.typography.laundryGothicRegular16,
//            verticalPadding = 8.dp,
//            horizontalPadding = 15.dp,
//            onClick = onClick
//        )
//    }
//}