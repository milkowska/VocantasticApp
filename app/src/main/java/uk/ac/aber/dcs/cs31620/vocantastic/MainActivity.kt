package uk.ac.aber.dcs.cs31620.vocantastic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.home.HomeScreenTopLevel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.list.ViewListScreenTopLevel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.testing.TestScreen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.VocantasticTheme

import uk.ac.aber.dcs.cs31620.vocantastic.ui.words.AddWordScreenTopLevel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VocantasticTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    BuildNavigationGraph()
                }
            }
        }
    }
}

@Composable
private fun BuildNavigationGraph(
    wordPairViewModel: WordPairViewModel = viewModel()
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
       // composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.Home.route) { HomeScreenTopLevel(navController, wordPairViewModel) }
        composable(Screen.List.route) { ViewListScreenTopLevel(navController, wordPairViewModel) }
        composable(Screen.Test.route) { TestScreen(navController, wordPairViewModel) }
        composable(Screen.Words.route) { AddWordScreenTopLevel(navController,wordPairViewModel) }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VocantasticTheme(dynamicColor = false) {
        BuildNavigationGraph()
    }
}