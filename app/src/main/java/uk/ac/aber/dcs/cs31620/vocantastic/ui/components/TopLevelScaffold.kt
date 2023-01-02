package uk.ac.aber.dcs.cs31620.vocantastic.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopLevelScaffold(
    navController: NavHostController,
    pageContent: @Composable (innerPadding: PaddingValues) -> Unit = {}
){
    Scaffold(
        bottomBar = {
            MainPageNavigationBar(navController)
        },
        content = { innerPadding ->
            pageContent(innerPadding)
        }
    )
}