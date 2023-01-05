package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.*
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.model.PreferencesViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.storage.TEST_SCORE
import uk.ac.aber.dcs.cs31620.vocantastic.ui.getNumberOfQuestions
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.randomIndexGenerator
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.Railway

/**
 * This is finding the correct answer test screen. The user is given a word in a native language and four answers, one of which is correct.
 * The length of the test depends on the size of the vocabulary list. If it is larger than 15, there are fifteen steps in total. Otherwise,
 * there are as many as there are word pairs in the dictionary.
 */

@Composable
fun FindAnswerScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel(),
    dataViewModel: PreferencesViewModel = hiltViewModel()
) {
    val wordList by wordPairViewModel.wordList.observeAsState(listOf())

    FindAnswerScreen(
        navController = navController,
        wordList = wordList,
        number = getNumberOfQuestions(wordList),
        dataViewModel = dataViewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
private fun FindAnswerScreen(
    navController: NavHostController,
    wordList: List<WordPair>,
    number: Int,
    dataViewModel: PreferencesViewModel = hiltViewModel()
) {
    // Indicates the question number, starts with 1
    var step by rememberSaveable { mutableStateOf(1) }

    // This is a word pair set, eg.  a window -> okno
    var wordA by rememberSaveable { mutableStateOf("") } // a window
    var wordB by rememberSaveable { mutableStateOf("") } // okno

    // Stores word pairs IDs that were already used in a test to avoid repeatability of questions.
    val questionBank = rememberSaveable { mutableListOf<Int>() }

    // Stores answers IDs to avoid repeatability of answers.
    val answerBank = rememberSaveable { mutableListOf<Int>() }

    // A list of possible answers including a correct one
    val multipleChoiceList = rememberSaveable { mutableListOf<WordPair>() }

    // Stores the user's chosen answer
    var userAnswer by rememberSaveable { mutableStateOf("") }

    // Test score displayed after the test is finished
    var resultScore by rememberSaveable { mutableStateOf(0) }

    var hasNextStep by rememberSaveable { mutableStateOf(true) }

    val composableScope = rememberCoroutineScope()

    if (wordList.isNotEmpty() && hasNextStep) {

        // Generate next word pair
        val nextWordPair = randomIndexGenerator(wordList.size - 1, questionBank)

        questionBank.add(nextWordPair)
        answerBank.add(nextWordPair)

        // Stores a randomly generated word pair that will be the shown in the current step
        val thisWordPair = wordList[nextWordPair]

        wordA = thisWordPair.entryWord
        wordB = thisWordPair.translatedWord

        // The word pair is added to a multiple choices list so that correct answer is there
        multipleChoiceList.add(thisWordPair)

        // The other word pairs are added that the current one so the possible choices will be unique (and incorrect)
        for (index in 1..3) {
            val wrongAnswer = randomIndexGenerator(wordList.size - 1, answerBank)
            multipleChoiceList.add(wordList[wrongAnswer])
            answerBank.add(wrongAnswer)
        }

        // Shuffling the list of answers so that the correct answer is not displayed as first option every time
        multipleChoiceList.shuffle()

        hasNextStep = false
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {

        val progressValue = step / number.toFloat()
        val openAlertDialog = remember { mutableStateOf(false) }

        Text(
            text = stringResource(id = R.string.find_correct_answer),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(start = 20.dp)
        )

        Spacer(modifier = Modifier.height(45.dp))

        LinearProgressIndicator(progressValue)

        Spacer(modifier = Modifier.height(45.dp))

        Card(
            modifier = Modifier
                .width(278.dp)
                .height(100.dp)
        )
        {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = wordA,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        for (option in multipleChoiceList) {
            val answer = option.translatedWord
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        bottom = 20.dp
                    )
            )
            {
                Checkbox(
                    checked = answer == userAnswer,
                    onCheckedChange = {
                        userAnswer = answer
                    }
                )
                Text(
                    text = answer,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all = 35.dp)
        ) {
            FilledTonalButton(modifier = Modifier
                .height(60.dp)
                .width(200.dp)
                .weight(0.5f),
                onClick = {
                    openAlertDialog.value = true

                }
            )
            {
                Text(
                    text = stringResource(id = R.string.quit),
                    fontSize = 16.sp,
                    fontFamily = Railway
                )
            }

            if (openAlertDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                    },
                    title = {
                        Text(
                            text = stringResource(R.string.are_you_sure),
                            fontFamily = Railway
                        )
                    },
                    text = {
                        Text(
                            stringResource(R.string.confirm_exit),
                            fontFamily = Railway
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openAlertDialog.value = false
                                navController.navigate(Screen.Test.route)
                            },
                        ) {
                            Text(
                                stringResource(R.string.exit),
                                fontFamily = Railway,

                                )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openAlertDialog.value = false
                            },
                        ) {
                            Text(
                                stringResource(R.string.dismiss),
                                fontFamily = Railway,
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.width(30.dp))

            FilledTonalButton(modifier = Modifier
                .height(60.dp)
                .width(200.dp)
                .weight(0.5f),
                enabled = userAnswer.isNotEmpty(),
                onClick = {
                    if (userAnswer.lowercase().trim() == wordB.lowercase().trim()) {
                        resultScore++
                    }
                    if (step >= number) {
                        val finalScore = (resultScore * 100) / number
                        GlobalScope.launch {
                            dataViewModel.saveInt(finalScore, TEST_SCORE)
                            delay(1000)
                        }

                        navController.navigate(Screen.TestScore.route)

                    } else if (wordList.isNotEmpty() && (step < number)) {
                        multipleChoiceList.clear()
                        answerBank.clear()
                        hasNextStep = true
                        step++
                        userAnswer = ""
                    }
                }
            )
            {
                Text(
                    text = stringResource(id = R.string.next),
                    fontSize = 16.sp,
                    fontFamily = Railway
                )
            }
        }
    }
}