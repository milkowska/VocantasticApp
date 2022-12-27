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
                navController,
                wordList
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
            fontWeight = FontWeight.Bold,
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
            fontWeight = FontWeight.Bold,
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
            Text(stringResource(id = R.string.anagram))
        }

        if (openAnagramDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    openAnagramDialog.value = false
                },
                title = {
                    Text(text = stringResource(R.string.anagram_title))

                },
                text = {
                    Text(stringResource(R.string.anagram_description))
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openAnagramDialog.value = false
                            navController.navigate(route = Screen.AnagramTest.route)
                        },
                    ) {
                        Text(stringResource(R.string.start_test))
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openAnagramDialog.value = false
                        },
                    ) {
                        Text(stringResource(R.string.try_later))
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
                if (wordList.isEmpty()) {
                    Toast.makeText(context, "Add words to begin a test!", Toast.LENGTH_LONG).show()
                } else {
                    openFindAnswerDialog.value = true
                }
            }) {
            Text(stringResource(id = R.string.find_correct_answer))
        }
        if (openFindAnswerDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    openFindAnswerDialog.value = false
                },
                title = {
                    Text(text = stringResource(R.string.find_answer_title))

                },
                text = {
                    Text(stringResource(R.string.find_answer_description))
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openFindAnswerDialog.value = false
                            navController.navigate(route = Screen.FindTest.route)
                        },
                    ) {
                        Text(stringResource(R.string.start_test))
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openFindAnswerDialog.value = false
                        },
                    ) {
                        Text(stringResource(R.string.try_later))
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
    }

}
