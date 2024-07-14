package com.tico.pomorodo.ui.member.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.tico.pomorodo.ui.common.view.SimpleIconButton
import com.tico.pomorodo.ui.member.viewmodel.FollowViewModel
import com.tico.pomorodo.ui.theme.IC_ARROW_BACK
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.coroutines.launch

@Composable
fun FollowListScreen() {
    val followViewModel: FollowViewModel = hiltViewModel()
    val followerList by followViewModel.followerList.collectAsState()
    val followingList by followViewModel.followingList.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.background(color = PomoroDoTheme.colorScheme.background)) {
        TopAppBarWithSingleButton()

        FollowTabRow(selectedTabIndex = pagerState.currentPage) { index ->
            coroutineScope.launch { pagerState.animateScrollToPage(index) }
        }

        HorizontalPager(state = pagerState) { page ->
            if (page == 0) FollowingList(
                followList = followingList,
                toggleFollowState = followViewModel::toggleFollowState
            )
            else FollowerList(
                followList = followerList,
                removeFollower = followViewModel::removeFollower
            )
        }
    }
}

@Composable
fun TopAppBarWithSingleButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, top = 24.dp, bottom = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        SimpleIconButton(
            size = 28,
            imageVector = PomoroDoTheme.iconPack[IC_ARROW_BACK]!!,
            contentDescriptionId = R.string.content_ic_arrow_back,
            enabled = true,
            onClickedListener = { /*TODO: top app bar - pop back stack*/ }
        )

        Text(
            text = stringResource(R.string.title_follow),
            modifier = Modifier.fillMaxWidth(),
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = PomoroDoTheme.typography.laundryGothicBold20
        )
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
fun FollowingList(followList: List<Follow>, toggleFollowState: (Int) -> Unit) {
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
                onClick = { toggleFollowState(index) }
            )
        }
    }
}

@Composable
fun FollowerList(followList: List<Follow>, removeFollower: (Int) -> Unit) {
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
                onClick = { removeFollower(index) }
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