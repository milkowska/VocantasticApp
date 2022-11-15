package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold

@Composable
fun ScoreScreen(
    navController: NavHostController
) {
    TopLevelScaffold(
        navController = navController,
    )
}