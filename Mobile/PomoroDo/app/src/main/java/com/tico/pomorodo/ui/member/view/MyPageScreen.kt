package com.tico.pomorodo.ui.member.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tico.pomorodo.BuildConfig
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.common.view.CustomSwitch
import com.tico.pomorodo.ui.common.view.Profile
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.common.view.clickableWithRipple
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcSetting
import com.tico.pomorodo.ui.member.viewmodel.MyPageViewModel
import com.tico.pomorodo.ui.theme.IC_DROP_DOWN
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun MyPageScreen(navigateToSettingScreen: () -> Unit) {
    val myPageViewModel: MyPageViewModel = hiltViewModel()
    val name by myPageViewModel.name.collectAsState()
    val profileUri by myPageViewModel.profile.collectAsState()
    var concentrationAlarmBottomSheet by remember { mutableStateOf(false) }
    var breakAlarmBottomSheet by remember { mutableStateOf(false) }
    var concentrationAlarmOption by remember { mutableStateOf(AlarmOptions.SOUND) }
    var breakAlarmOption by remember { mutableStateOf(AlarmOptions.SOUND) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 18.dp, end = 18.dp, top = 24.dp),
        horizontalAlignment = Alignment.End
    ) {
        Icon(
            imageVector = IconPack.IcSetting,
            contentDescription = stringResource(R.string.content_ic_setting),
            modifier = Modifier
                .size(28.dp)
                .clickableWithRipple(
                    roundedCornerRadius = 150.dp,
                    onClick = { navigateToSettingScreen() }),
            tint = Color.Unspecified
        )

        MyProfile(
            profileUri = profileUri,
            userName = name,
            followingCount = 4,
            followerCount = 2,
        )

        Spacer(modifier = Modifier.height(28.dp))

        MyPageMenuList(
            isNetworkConnected = myPageViewModel.isNetworkConnected,
            concentrationAlarmOptions = concentrationAlarmOption,
            breakAlarmOptions = breakAlarmOption,
            onConcentrationAlarmClicked = { concentrationAlarmBottomSheet = true },
            onBreakAlarmClicked = { breakAlarmBottomSheet = true }
        )

        if (concentrationAlarmBottomSheet) {
            SettingAlarmBottomSheet(
                title = stringResource(id = R.string.title_alarm_concentration),
                initialSelect = concentrationAlarmOption,
                onDismissRequest = { concentrationAlarmBottomSheet = false },
                onConfirmation = { alarmOptions ->
                    concentrationAlarmOption = alarmOptions
                    concentrationAlarmBottomSheet = false
                }
            )
        }

        if (breakAlarmBottomSheet) {
            SettingAlarmBottomSheet(
                title = stringResource(id = R.string.title_alarm_break),
                initialSelect = breakAlarmOption,
                onDismissRequest = { breakAlarmBottomSheet = false },
                onConfirmation = { alarmOptions ->
                    breakAlarmOption = alarmOptions
                    breakAlarmBottomSheet = false
                }
            )
        }
    }
}

@Composable
fun MyProfile(
    profileUri: String?,
    userName: String,
    followingCount: Int,
    followerCount: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Profile(
            uri = profileUri,
            size = 60
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = userName,
                color = PomoroDoTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                style = PomoroDoTheme.typography.laundryGothicRegular20
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FollowText(title = stringResource(R.string.title_following), count = followingCount)
                FollowText(title = stringResource(R.string.title_follower), count = followerCount)
            }
        }
    }
}

@Composable
fun FollowText(title: String, count: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        val textStyle = PomoroDoTheme.typography.laundryGothicRegular14
        val textColor = PomoroDoTheme.colorScheme.onBackground

        SimpleText(text = title, style = textStyle, color = textColor)
        SimpleText(text = count.toString(), style = textStyle, color = textColor)
    }
}

@Composable
fun MyPageMenuList(
    isNetworkConnected: Boolean,
    concentrationAlarmOptions: AlarmOptions,
    breakAlarmOptions: AlarmOptions,
    onConcentrationAlarmClicked: () -> Unit,
    onBreakAlarmClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            color = PomoroDoTheme.colorScheme.myPageMenuBackgroundColor
        ) {
            val lineSpacer: @Composable () -> Unit = {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = PomoroDoTheme.colorScheme.outlineVariant
                )
            }

            Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 4.dp)) {
                SetAlarm(
                    title = stringResource(R.string.title_alarm_concentration),
                    alarmOptions = concentrationAlarmOptions,
                    onClick = onConcentrationAlarmClicked
                )

                lineSpacer()

                SetAlarm(
                    title = stringResource(R.string.title_alarm_break),
                    alarmOptions = breakAlarmOptions,
                    onClick = onBreakAlarmClicked
                )

                if (BuildConfig.APP_VERSION >= "2.0.0") {
                    lineSpacer()

                    val (checked1, setChecked1) = remember { mutableStateOf(false) }
                    SetStopWatchMode(
                        title = stringResource(R.string.title_stopwatch_concentration),
                        checked = checked1
                    ) {
                        setChecked1(it)
                    }

                    lineSpacer()

                    val (checked2, setChecked2) = remember { mutableStateOf(false) }
                    SetStopWatchMode(
                        title = stringResource(R.string.title_stopwatch_break),
                        checked = checked2
                    ) {
                        setChecked2(it)
                    }
                }
            }
        }

        if (!isNetworkConnected) {
            NetworkSyncMenu()
        }
    }
}

@Composable
fun SetAlarm(title: String, alarmOptions: AlarmOptions, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = PomoroDoTheme.typography.laundryGothicRegular14
        )

        Row(
            modifier = Modifier
                .clickableWithRipple(roundedCornerRadius = 150.dp, onClick = onClick)
                .padding(top = 12.dp, bottom = 12.dp, start = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(alarmOptions.title),
                color = PomoroDoTheme.colorScheme.onBackground,
                textAlign = TextAlign.End,
                style = PomoroDoTheme.typography.laundryGothicRegular14
            )
            SimpleIcon(
                size = 15,
                imageVector = requireNotNull(PomoroDoTheme.iconPack[IC_DROP_DOWN]),
                contentDescriptionId = R.string.content_ic_drop_down
            )
        }
    }
}

@Composable
fun SetStopWatchMode(
    title: String,
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit
) {
    val content = if (checked) stringResource(R.string.content_stopwatch_option_true)
    else stringResource(R.string.content_stopwatch_option_false)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                color = PomoroDoTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                style = PomoroDoTheme.typography.laundryGothicRegular14
            )

            Text(
                text = content,
                color = PomoroDoTheme.colorScheme.outline,
                textAlign = TextAlign.Start,
                style = PomoroDoTheme.typography.laundryGothicRegular10
            )
        }

        CustomSwitch(checked = checked, onCheckedChange = onCheckChanged)
    }
}

@Composable
fun NetworkSyncMenu() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = PomoroDoTheme.colorScheme.myPageMenuBackgroundColor
    ) {
        Text(
            modifier = Modifier
                .clickableWithRipple { TODO("network sync") }
                .padding(horizontal = 18.dp, vertical = 14.dp),
            text = stringResource(id = R.string.title_network_sync),
            color = PomoroDoTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = PomoroDoTheme.typography.laundryGothicRegular14
        )
    }
}