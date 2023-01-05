package uk.ac.aber.dcs.cs31620.vocantastic.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.screens
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.Railway

/**
 * Implementation of the navigation bar.
 */
@Composable
fun MainPageNavigationBar(
    navController: NavController
) {
    val icons = mapOf(
        Screen.Home to IconGroup(
            filledIcon = painterResource(id = R.drawable.filled_home_icon_19),
            outlinedIcon = painterResource(R.drawable.outline_home_icon_19),
            label = stringResource(id = R.string.home_icon)
        ),
        Screen.Words to IconGroup(
            filledIcon = painterResource(R.drawable.filled_add_icon_19),
            outlinedIcon = painterResource(R.drawable.outline_add_icon_19),
            label = stringResource(id = R.string.add_icon)
        ),
        Screen.List to IconGroup(
            filledIcon = painterResource(R.drawable.filled_school_icon_19),
            outlinedIcon = painterResource(R.drawable.outline_school_icon_19),
            label = stringResource(id = R.string.show_list)
        ),
        Screen.Test to IconGroup(
            filledIcon = painterResource(R.drawable.filled_quiz_icon),
            outlinedIcon = painterResource(R.drawable.outlined_quiz_icon),
            label = stringResource(id = R.string.quiz)
        )
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            val labelText = icons[screen]!!.label
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = (
                                if (isSelected)
                                    icons[screen]!!.filledIcon
                                else
                                    icons[screen]!!.outlinedIcon),
                        contentDescription = labelText
                    )
                },
                label = { Text(text = labelText, fontFamily = Railway) },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}