package com.tico.pomorodo.ui.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.tico.pomorodo.R
import com.tico.pomorodo.navigation.BottomNavigationDestination
import com.tico.pomorodo.ui.common.view.executeToast
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun BottomBar(
    currentDestination: NavDestination?,
    isOffline: Boolean,
    navigateToDestination: (BottomNavigationDestination) -> Unit,
    destinations: List<BottomNavigationDestination>
) {
    val context = LocalContext.current
    NavigationBar(
        modifier = Modifier.height(60.dp),
        containerColor = PomoroDoTheme.colorScheme.secondaryContainer,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination
                ?.hierarchy
                ?.any { it.route == destination.name } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (isOffline && destination == BottomNavigationDestination.FOLLOW) {
                        context.executeToast(R.string.title_not_support_offline_mode)
                    } else {
                        navigateToDestination(destination)
                    }
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
                            contentDescription = stringResource(id = destination.iconTextId)
                        )
                        Text(
                            text = stringResource(id = destination.iconTextId),
                            style = PomoroDoTheme.typography.laundryGothicRegular12
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PomoroDoTheme.colorScheme.primary,
                    selectedTextColor = PomoroDoTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = PomoroDoTheme.colorScheme.onBackground,
                    unselectedTextColor = PomoroDoTheme.colorScheme.onBackground
                )
            )
        }
    }
}