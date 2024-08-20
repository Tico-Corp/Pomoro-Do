package com.tico.pomorodo.ui.member.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.tico.pomorodo.R
import com.tico.pomorodo.domain.model.Follow
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.CustomTopAppBarWithSingleButton
import com.tico.pomorodo.ui.common.view.SimpleAlertDialog
import com.tico.pomorodo.ui.member.viewmodel.FollowViewModel
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.coroutines.launch

@Composable
fun FollowListScreen() {
    val followViewModel: FollowViewModel = hiltViewModel()
    val followerList by followViewModel.followerList.collectAsState()
    val followingList by followViewModel.followingList.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()
    var unFollowingDialogVisible by remember { mutableStateOf(false) }
    var removeFollowerDialogVisible by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.background(color = PomoroDoTheme.colorScheme.background)) {
        CustomTopAppBarWithSingleButton(
            title = stringResource(R.string.title_follow),
            navigationAction = { /*TODO: top app bar - pop back stack*/ },
            top = 24,
            bottom = 14,
            start = 16,
            end = 16
        )

        FollowTabRow(selectedTabIndex = pagerState.currentPage) { index ->
            coroutineScope.launch { pagerState.animateScrollToPage(index) }
        }

        HorizontalPager(state = pagerState) { page ->
            if (page == 0) FollowingList(
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
            else FollowerList(
                followList = followerList,
                onClick = { index ->
                    selectedIndex = index
                    removeFollowerDialogVisible = true
                }
            )
        }
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

    if (removeFollowerDialogVisible) {
        SimpleAlertDialog(
            dialogTitleId = R.string.title_remove_follower_dialog,
            confirmTextId = R.string.content_delete,
            dismissTextId = R.string.content_cancel,
            onConfirmation = {
                followViewModel.removeFollower(selectedIndex)
                removeFollowerDialogVisible = false
            },
            onDismissRequest = { removeFollowerDialogVisible = false },
            content = {
                Text(
                    text = stringResource(R.string.content_remove_follower_dialog),
                    color = PomoroDoTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    style = PomoroDoTheme.typography.laundryGothicRegular14
                )
            })
    }
}

@Composable
fun FollowTabRow(selectedTabIndex: Int, onSelectedTabIndexChange: (Int) -> Unit) {
    val textColor = PomoroDoTheme.colorScheme.onBackground

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = PomoroDoTheme.colorScheme.background,
        contentColor = textColor,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    tabPositions[selectedTabIndex]
                ), color = PomoroDoTheme.colorScheme.primaryContainer
            )
        }
    ) {
        Tab(
            selected = selectedTabIndex == 0,
            onClick = { onSelectedTabIndexChange(0) },
            text = {
                Text(
                    text = stringResource(R.string.title_following),
                    color = textColor,
                    textAlign = TextAlign.Center,
                    style = PomoroDoTheme.typography.laundryGothicRegular16
                )
            }
        )

        Tab(
            selected = selectedTabIndex == 1,
            onClick = { onSelectedTabIndexChange(1) },
            text = {
                Text(
                    text = stringResource(id = R.string.title_follower),
                    color = textColor,
                    textAlign = TextAlign.Center,
                    style = PomoroDoTheme.typography.laundryGothicRegular16
                )
            }
        )
    }
}

@Composable
fun FollowingList(followList: List<Follow>, onClick: (Int, Boolean) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        itemsIndexed(items = followList) { index, user ->
            if (index == 0)
                Spacer(modifier = Modifier.height(18.dp))

            FollowItem(
                user = user,
                followButtonText = stringResource(id = R.string.title_following),
                unFollowButtonText = stringResource(id = R.string.title_follow),
                followButtonContainerColor = PomoroDoTheme.colorScheme.gray90,
                followButtonContentColor = PomoroDoTheme.colorScheme.gray20,
                unFollowButtonContainerColor = PomoroDoTheme.colorScheme.primaryContainer,
                unFollowButtonContentColor = Color.White,
                onClick = { onClick(index, user.isFollowing) }
            )
        }
    }
}

@Composable
fun FollowerList(followList: List<Follow>, onClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 18.dp, start = 18.dp, end = 18.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        itemsIndexed(items = followList) { index, user ->
            FollowItem(
                user = user,
                followButtonText = stringResource(id = R.string.content_delete),
                followButtonContainerColor = PomoroDoTheme.colorScheme.gray90,
                followButtonContentColor = PomoroDoTheme.colorScheme.gray20,
                onClick = { onClick(index) }
            )
        }
    }
}

@Composable
fun FollowItem(
    user: Follow,
    followButtonText: String,
    unFollowButtonText: String? = null,
    followButtonContainerColor: Color,
    followButtonContentColor: Color,
    unFollowButtonContainerColor: Color? = null,
    unFollowButtonContentColor: Color? = null,
    onClick: () -> Unit
) {
    val buttonText =
        if (user.isFollowing) followButtonText
        else unFollowButtonText ?: followButtonText
    val buttonContainerColor =
        if (user.isFollowing) followButtonContainerColor
        else unFollowButtonContainerColor ?: followButtonContainerColor
    val buttonContentColor =
        if (user.isFollowing) followButtonContentColor
        else unFollowButtonContentColor ?: followButtonContentColor

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageModel = { user.profileUrl },
            modifier = Modifier
                .size(48.dp)
                .clip(shape = CircleShape),
            requestOptions = { RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        )

        Text(
            text = user.name,
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = PomoroDoTheme.typography.laundryGothicRegular18
        )

        Spacer(modifier = Modifier.weight(1f))

        CustomTextButton(
            text = buttonText,
            containerColor = buttonContainerColor,
            contentColor = buttonContentColor,
            textStyle = PomoroDoTheme.typography.laundryGothicRegular16,
            verticalPadding = 8.dp,
            horizontalPadding = 15.dp,
            onClick = onClick
        )
    }
}