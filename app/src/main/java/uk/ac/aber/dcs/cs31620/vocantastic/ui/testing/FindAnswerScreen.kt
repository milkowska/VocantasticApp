package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold

@Composable
fun FindAnswerScreenTopLevel(navController: NavHostController,
                             wordPairViewModel: WordPairViewModel = viewModel()) {
    val words by wordPairViewModel.wordList.observeAsState(listOf())


}



@Composable
fun FindAnswerScreen(navController: NavHostController,
    words: List<WordPair>,

) {
    TopLevelScaffold(
        navController = navController,
    )
}