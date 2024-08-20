package com.tico.pomorodo.ui.home.view

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
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun BottomBar(
    appState: AppState
) {
    NavigationBar(
        modifier = Modifier.height(60.dp),
        containerColor = PomoroDoTheme.colorScheme.secondaryContainer,
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
                            imageVector = requireNotNull(PomoroDoTheme.iconPack[destination.iconString]),
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
                    selectedIconColor = PomoroDoTheme.colorScheme.primary,
                    selectedTextColor = PomoroDoTheme.colorScheme.primary,
                    selectedIndicatorColor = Color.Unspecified,
                    unselectedIconColor = PomoroDoTheme.colorScheme.onBackground,
                    unselectedTextColor = PomoroDoTheme.colorScheme.onBackground,
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomBarPreview() {
    PomoroDoTheme() {
        BottomBar(AppState(rememberNavController()))
    }
}