package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.rememberNavController
import com.tico.pomorodo.ui.AppState
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.theme.onBackgroundLight
import com.tico.pomorodo.ui.theme.primaryLight
import com.tico.pomorodo.ui.theme.secondaryContainerLight

@Composable
fun BottomBar(
    appState: AppState
) {
    NavigationBar(
        modifier = Modifier.height(60.dp),
        containerColor = secondaryContainerLight,
    ) {
        appState.bottomNavigationDestinationList.forEach { destination ->
            NavigationBarItem(
                selected = appState.currentDestination?.hierarchy?.any {
                    it.route?.contains(destination.name, true) ?: false
                } ?: false,
                onClick = {
                    appState.navigateToDestination(destination)
                },
                icon = {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            imageVector = destination.iconVector,
                            tint = Color.Unspecified,
                            contentDescription = stringResource(id = destination.iconTextId)
                        )
                        Text(
                            text = stringResource(id = destination.iconTextId),
                            style = PomoroDoTheme.typography.laundryGothicRegular12
                        )
                    }
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = primaryLight,
                    selectedTextColor = primaryLight,
                    selectedIndicatorColor = Color.Unspecified,
                    unselectedIconColor = onBackgroundLight,
                    unselectedTextColor = onBackgroundLight,
                    disabledIconColor = Color.Blue,
                    disabledTextColor = Color.Blue
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomBarPreview() {
    BottomBar(AppState(rememberNavController()))
}