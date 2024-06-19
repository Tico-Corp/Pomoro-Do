package com.tico.pomorodo.ui.todo.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.R
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.ui.common.view.ProfileVertical
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun TodoProfileItems(userList: List<User>, selectedIndex: Int, onClicked: (Int) -> Unit) {
    val size = remember { 50 }
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileVertical(
            modifier = Modifier.clickable { onClicked(-1) },
            size = size,
            name = stringResource(R.string.content_me),
            textStyle = PomoroDoTheme.typography.laundryGothicBold14,
            isClicked = selectedIndex == -1
        )
        VerticalDivider(
            color = PomoroDoTheme.colorScheme.gray90,
            modifier = Modifier.fillMaxHeight(),
            thickness = 1.dp
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            repeat(userList.size) { userIndex ->
                ProfileVertical(
                    modifier = Modifier.clickable { onClicked(userIndex) },
                    size = size,
                    name = userList[userIndex].name,
                    textStyle = PomoroDoTheme.typography.laundryGothicRegular14,
                    isClicked = selectedIndex == userIndex
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoProfilesPreview() {
    var selectedProfileIndex by rememberSaveable {
        mutableIntStateOf(-1)
    }
    PomoroDoTheme() {
        TodoProfileItems(
            userList = DataSource.userList,
            selectedIndex = selectedProfileIndex,
            onClicked = { selectedProfileIndex = it })
    }
}

data class User(
    val name: String,
    val profileUrl: String? = null
)