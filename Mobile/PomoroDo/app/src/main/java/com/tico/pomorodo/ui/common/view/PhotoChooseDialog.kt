package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun PhotoChooseDialog(
    isDefaultImage: Boolean,
    onDismissRequest: () -> Unit,
    onTakePhotoClicked: () -> Unit,
    onPickPhotoClicked: () -> Unit,
    onApplyDefaultImageClicked: () -> Unit
) {
    val colors = CardDefaults.cardColors(containerColor = PomoroDoTheme.colorScheme.dialogSurface)
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.padding(horizontal = 40.dp),
            shape = RoundedCornerShape(15.dp),
            colors = colors,
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SimpleText(
                    modifier = Modifier.fillMaxWidth(),
                    textId = R.string.title_profile_photo_setting,
                    textAlign = TextAlign.Start,
                    style = PomoroDoTheme.typography.laundryGothicBold20,
                    color = PomoroDoTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(4.dp))

                SimpleText(
                    modifier = Modifier
                        .clickableWithRipple(10.dp, true) { onTakePhotoClicked() }
                        .padding(vertical = 9.dp, horizontal = 10.dp)
                        .fillMaxWidth(),
                    textId = R.string.content_take_photo,
                    textAlign = TextAlign.Start,
                    style = PomoroDoTheme.typography.laundryGothicRegular14,
                    color = PomoroDoTheme.colorScheme.onBackground,
                )

                SimpleText(
                    modifier = Modifier
                        .clickableWithRipple(10.dp, true) { onPickPhotoClicked() }
                        .padding(vertical = 9.dp, horizontal = 10.dp)
                        .fillMaxWidth(),
                    textId = R.string.content_pick_photo,
                    textAlign = TextAlign.Start,
                    style = PomoroDoTheme.typography.laundryGothicRegular14,
                    color = PomoroDoTheme.colorScheme.onBackground,
                )

                if (!isDefaultImage) {
                    SimpleText(
                        modifier = Modifier
                            .clickableWithRipple(10.dp, true) { onApplyDefaultImageClicked() }
                            .fillMaxWidth()
                            .padding(vertical = 9.dp, horizontal = 10.dp),
                        textId = R.string.content_default_image,
                        textAlign = TextAlign.Start,
                        style = PomoroDoTheme.typography.laundryGothicRegular14,
                        color = PomoroDoTheme.colorScheme.onBackground,
                    )
                }
            }
        }
    }
}