package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.Railway
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.md_theme_light_primary

/**
 * This is where the user can test their knowledge and begin a test. There are two tests to choose from, one of which requires at least four words in
 * the vocabulary list to start. The "solving the anagram" test requires only one word in the dictionary to start a test. The user can read a short
 * description of the test they are about to begin when clicked on the desired button. The user can quit at or proceed, during each step of a test there
 * is a quitting option there as well.
 */
@Composable
fun TestScreen(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel()
) {
    val wordList by wordPairViewModel.wordList.observeAsState(listOf())
    TopLevelScaffold(
        navController = navController,
    )
    { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TestScreenContent(
                modifier = Modifier.padding(10.dp),
                navController = navController,
                wordList = wordList
            )
        }
    }
}

@Composable
fun TestScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    wordList: List<WordPair>
) {
    val context = LocalContext.current
    val openAnagramDialog = remember { mutableStateOf(false) }
    val openFindAnswerDialog = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.test_progress_title),
            fontSize = 24.sp,
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(10.dp))

        Image(
            modifier = Modifier
                .size(280.dp),
            painter = painterResource(id = R.drawable.transparent_brain),
            contentDescription = stringResource(id = R.string.brain_image),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.test_choice),
            fontSize = 22.sp,
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            onClick = {
                if (wordList.isEmpty()) {
                    Toast.makeText(context, "Add words to begin a test!", Toast.LENGTH_LONG).show()
                } else {
                    openAnagramDialog.value = true
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.anagram),
                fontSize = 16.sp,
                fontFamily = Railway
            )
        }

        if (openAnagramDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    openAnagramDialog.value = false
                },
                title = {
                    Text(
                        text = stringResource(R.string.anagram_title),
                        fontFamily = Railway
                    )

                },
                text = {
                    Text(
                        stringResource(R.string.anagram_description),
                        fontFamily = Railway
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openAnagramDialog.value = false
                            navController.navigate(route = Screen.AnagramTest.route)
                        },
                    ) {
                        Text(
                            stringResource(R.string.start_test),
                            fontFamily = Railway
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openAnagramDialog.value = false
                        },
                    ) {
                        Text(
                            stringResource(R.string.try_later),
                            fontFamily = Railway
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            onClick = {
                if (wordList.size < 4) {
                    Toast.makeText(
                        context,
                        "Add at least four word pairs to begin a test!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    openFindAnswerDialog.value = true
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.find_correct_answer),
                fontSize = 16.sp,
                fontFamily = Railway
            )
        }
        if (openFindAnswerDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    openFindAnswerDialog.value = false
                },
                title = {
                    Text(
                        text = stringResource(R.string.find_answer_title),
                        fontFamily = Railway
                    )

                },
                text = {
                    Text(
                        stringResource(R.string.find_answer_description),
                        fontFamily = Railway
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openFindAnswerDialog.value = false
                            navController.navigate(route = Screen.FindTest.route)
                        },
                    ) {
                        Text(
                            stringResource(R.string.start_test),
                            fontFamily = Railway
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openFindAnswerDialog.value = false
                        },
                    ) {
                        Text(
                            stringResource(R.string.try_later),
                            fontFamily = Railway
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
    }

}
