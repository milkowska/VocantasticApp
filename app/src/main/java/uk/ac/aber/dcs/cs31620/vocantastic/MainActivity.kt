package uk.ac.aber.dcs.cs31620.vocantastic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.aber.dcs.cs31620.vocantastic.model.ListViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.model.PreferencesViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.storage.WELCOME_SCREEN
import uk.ac.aber.dcs.cs31620.vocantastic.ui.home.HomeScreenTopLevel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.home.SettingsScreenTopLevel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.list.FlashcardScreenTopLevel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.list.ViewListScreenTopLevel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.testing.*
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.VocantasticTheme
import uk.ac.aber.dcs.cs31620.vocantastic.ui.welcome.WelcomeScreen

import uk.ac.aber.dcs.cs31620.vocantastic.ui.words.AddWordScreenTopLevel

@AndroidEntryPoint
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
                    val listViewModel = viewModel<ListViewModel>()
                    BuildNavigationGraph()
                }
            }
        }
    }
}

@Composable
private fun BuildNavigationGraph(
    wordPairViewModel: WordPairViewModel = viewModel(),
    dataViewModel: PreferencesViewModel = hiltViewModel(),
    listViewModel: ListViewModel = viewModel()
) {

    val configSet = dataViewModel.getBoolean(WELCOME_SCREEN)
    val navController = rememberNavController()

    var startRoute = Screen.Home.route

    if (configSet == false) {
        startRoute = Screen.Welcome.route
    }

    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable(Screen.Home.route) { HomeScreenTopLevel(navController) }
        composable(Screen.List.route) {
            ViewListScreenTopLevel(
                navController,
                wordPairViewModel,
                listViewModel
            )
        }
        composable(Screen.Test.route) { TestScreen(navController, wordPairViewModel) }
        composable(Screen.Words.route) { AddWordScreenTopLevel(navController) }
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.TestScore.route) { TestScoreScreenTopLevel(navController) }
        composable(Screen.AnagramTest.route) {
            AnagramScreenTopLevel(
                navController,
                wordPairViewModel
            )
        }
        composable(Screen.FindTest.route) {
            FindAnswerScreenTopLevel(
                navController,
                wordPairViewModel
            )
        }
        composable(Screen.Settings.route) { SettingsScreenTopLevel(navController) }
        composable(Screen.Flashcard.route) {
            FlashcardScreenTopLevel(
                navController = navController,
                wordPairViewModel = wordPairViewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VocantasticTheme(dynamicColor = false) {
        BuildNavigationGraph()
    }
}