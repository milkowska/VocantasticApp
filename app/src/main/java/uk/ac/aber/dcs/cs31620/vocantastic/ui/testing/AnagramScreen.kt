package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.Storage
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen

@Composable
fun AnagramScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel
) {
    val words by wordPairViewModel.wordList.observeAsState(listOf())

    val context = LocalContext.current
    val storage = Storage(context)

    //sets the number of steps in the test by default
    val numberOfQuestions: Int = if (words.size >= 15) {
        15
    } else {
        words.size
    }

    AnagramScreen(
        navController = navController,
        wordList = words,
        number = numberOfQuestions,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnagramScreen(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel(),
    wordList: List<WordPair>,
    number: Int,
) {

    // test score displayed after the test is finished
    var resultScore by rememberSaveable { mutableStateOf(0) }

    var step by rememberSaveable { mutableStateOf(1) }


    var userAnswer by rememberSaveable { mutableStateOf("") }
    var correctFirstValue by rememberSaveable { mutableStateOf("") }
    var correctAnswer by rememberSaveable { mutableStateOf("") }

    val storeUsedIndex = rememberSaveable { mutableListOf<Int>() }

    var hasNext by rememberSaveable { mutableStateOf(true) }
    var anagramed by rememberSaveable { mutableStateOf("") }

    if (wordList.isNotEmpty() && hasNext) {
        val randomId = randomIndexGenerator(wordList.size - 1, storeUsedIndex)
        storeUsedIndex.add(randomId)

        val thisWordPair = wordList[randomId]

        correctFirstValue = thisWordPair.entryWord
        correctAnswer = thisWordPair.translatedWord

        anagramed = anagramCreator(thisWordPair.translatedWord)
        hasNext = false

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val progressValue = step / number.toFloat()

        Text(
            text = stringResource(id = R.string.anagram),
            modifier = Modifier.padding(start = 8.dp, top = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        LinearProgressIndicator(progressValue)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.the_anagram_text),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .width(240.dp)
                .height(85.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = correctFirstValue,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = stringResource(id = R.string.is_word),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier
                .width(240.dp)
                .height(85.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = anagramCreator(anagramed),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.correct_answer),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = userAnswer,
            onValueChange = {
                userAnswer = it
            },
            label = {
                Text(stringResource(id = R.string.type_here))
            },
            modifier = Modifier,
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(horizontalArrangement = Arrangement.SpaceAround) {

            Button(modifier = Modifier
                .height(60.dp)
                .width(200.dp),
                onClick = {
                    navController.navigate(Screen.Test.route)
                }

            ) {
                Text(text = "Quit")
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(modifier = Modifier
                .height(60.dp)
                .width(200.dp),
                enabled = userAnswer.isNotEmpty(),
                onClick = { // is equal to???
                    if (userAnswer.lowercase().trim() == correctAnswer.lowercase().trim()) {
                        resultScore++
                    }
                    if (step >= number) {
                        val finalScore = (resultScore * 100) / number
                        //store it by viewmodel

                        navController.navigate(Screen.TestScore.route)

                    } else if (wordList.isNotEmpty() && (step < number)) {
                        hasNext = true
                        step++
                        userAnswer = ""
                    }
                })
            {
                Text(text = "Next")
            }
        }
    }
}

private fun anagramCreator(originalWord: String): String {
    val stringValueToArray = originalWord.trim().toCharArray()
    return stringValueToArray.sortedDescending().joinToString("")
}

private fun randomIndexGenerator(lastIndex: Int, idValues: List<Int>): Int {
    while (true) {
        // inclusive or exclusive?
        val index = (0 until lastIndex + 1).random()
        if (index !in idValues) {
            return index
        }
    }

}
