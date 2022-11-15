package uk.ac.aber.dcs.cs31620.vocantastic.ui.list

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold

@Composable
fun ViewList(
    navController: NavHostController
) {
    TopLevelScaffold(
        navController = navController,
    )
}