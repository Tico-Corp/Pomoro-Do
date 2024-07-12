package com.tico.pomorodo.ui.member.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.SimpleIconButton
import com.tico.pomorodo.ui.theme.IC_ARROW_BACK
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Preview
@Composable
fun FollowListScreen() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    PomoroDoTheme {
        Column {
            TopAppBarWithSingleButton()

            FollowTabRow(selectedTabIndex = selectedTabIndex) { index ->
                selectedTabIndex = index
            }
        }
    }
}

@Composable
fun TopAppBarWithSingleButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = PomoroDoTheme.colorScheme.background)
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
            text = stringResource(R.string.title_follow_list),
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