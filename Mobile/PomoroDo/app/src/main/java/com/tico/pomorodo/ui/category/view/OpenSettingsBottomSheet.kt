package com.tico.pomorodo.ui.category.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.data.local.entity.OpenSettings
import com.tico.pomorodo.ui.common.view.NoPaddingRadioButton
import com.tico.pomorodo.ui.common.view.SimpleIcon
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenSettingsBottomSheet(
    title: String,
    sheetState: SheetState,
    openSettingOption: OpenSettings,
    onShowBottomSheetChange: (Boolean) -> Unit,
    onOkButtonClicked: (OpenSettings) -> Unit
) {
    val radioButtonColors = RadioButtonColors(
        selectedColor = PomoroDoTheme.colorScheme.primaryContainer,
        unselectedColor = PomoroDoTheme.colorScheme.dialogGray50,
        disabledSelectedColor = Color.Unspecified,
        disabledUnselectedColor = Color.Unspecified
    )
    var selectedOption by rememberSaveable { mutableStateOf(openSettingOption) }

    ModalBottomSheet(
        onDismissRequest = {
            onShowBottomSheetChange(false)
        },
        sheetState = sheetState,
        containerColor = PomoroDoTheme.colorScheme.dialogSurface,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SimpleText(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = title,
                isEllipsis = true,
                style = PomoroDoTheme.typography.laundryGothicBold20,
                color = PomoroDoTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier.clickable { selectedOption = OpenSettings.FULL },
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SimpleIcon(
                    size = 15,
                    imageVector = PomoroDoTheme.iconPack[OpenSettings.FULL.iconString]!!,
                    contentDescriptionId = OpenSettings.FULL.descriptionId
                )
                SimpleText(
                    textId = OpenSettings.FULL.textId,
                    style = PomoroDoTheme.typography.laundryGothicRegular14,
                    color = PomoroDoTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.weight(1f))
                NoPaddingRadioButton(
                    selected = selectedOption == OpenSettings.FULL,
                    onClick = { selectedOption = OpenSettings.FULL },
                    colors = radioButtonColors
                )
            }
            HorizontalDivider(color = PomoroDoTheme.colorScheme.dialogGray90)
            Row(
                modifier = Modifier.clickable { selectedOption = OpenSettings.FOLLOWER },
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SimpleIcon(
                    size = 15,
                    imageVector = PomoroDoTheme.iconPack[OpenSettings.FOLLOWER.iconString]!!,
                    contentDescriptionId = OpenSettings.FOLLOWER.descriptionId
                )
                SimpleText(
                    textId = OpenSettings.FOLLOWER.textId,
                    style = PomoroDoTheme.typography.laundryGothicRegular14,
                    color = PomoroDoTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.weight(1f))
                NoPaddingRadioButton(
                    selected = selectedOption == OpenSettings.FOLLOWER,
                    onClick = { selectedOption = OpenSettings.FOLLOWER },
                    colors = radioButtonColors
                )
            }
            HorizontalDivider(color = PomoroDoTheme.colorScheme.dialogGray90)
            Row(
                modifier = Modifier.clickable { selectedOption = OpenSettings.ME },
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SimpleIcon(
                    size = 15,
                    imageVector = PomoroDoTheme.iconPack[OpenSettings.ME.iconString]!!,
                    contentDescriptionId = OpenSettings.ME.descriptionId
                )
                SimpleText(
                    textId = OpenSettings.ME.textId,
                    style = PomoroDoTheme.typography.laundryGothicRegular14,
                    color = PomoroDoTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.weight(1f))
                NoPaddingRadioButton(
                    selected = selectedOption == OpenSettings.ME,
                    onClick = { selectedOption = OpenSettings.ME },
                    colors = radioButtonColors
                )
            }
            Box(
                modifier = Modifier
                    .clickable { onOkButtonClicked(selectedOption) }
                    .background(
                        PomoroDoTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(5.dp)
                    )
                    .padding(vertical = 12.dp)
            ) {
                SimpleText(
                    modifier = Modifier.fillMaxWidth(),
                    textId = R.string.content_ok,
                    textAlign = TextAlign.Center,
                    style = PomoroDoTheme.typography.laundryGothicRegular16,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}