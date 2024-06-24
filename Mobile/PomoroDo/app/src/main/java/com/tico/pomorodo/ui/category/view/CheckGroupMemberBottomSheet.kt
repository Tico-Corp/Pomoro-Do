package com.tico.pomorodo.ui.category.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.data.local.entity.User
import com.tico.pomorodo.ui.common.view.ProfileHorizontal
import com.tico.pomorodo.ui.common.view.SimpleText
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CheckGroupMemberBottomSheet(
    sheetState: SheetState,
    onShowBottomSheetChange: (Boolean) -> Unit,
    memberList: List<User>,
) {
    ModalBottomSheet(
        onDismissRequest = {
            onShowBottomSheetChange(false)
        },
        sheetState = sheetState,
        containerColor = PomoroDoTheme.colorScheme.dialogSurface,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.title_check_group_member, memberList.size),
                isEllipsis = true,
                style = PomoroDoTheme.typography.laundryGothicBold20,
                color = PomoroDoTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(memberList) {
                    ProfileHorizontal(
                        size = 36,
                        name = it.name,
                        textStyle = PomoroDoTheme.typography.laundryGothicRegular14
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}