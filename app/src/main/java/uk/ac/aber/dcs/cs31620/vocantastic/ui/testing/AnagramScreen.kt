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
import androidx.navigation.NavHostController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.model.PreferencesViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.storage.TEST_SCORE
import uk.ac.aber.dcs.cs31620.vocantastic.ui.anagramCreator
import uk.ac.aber.dcs.cs31620.vocantastic.ui.getNumberOfQuestions
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.randomIndexGenerator
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.Railway

/**
 * This is solving an anagram test screen. The user is given a word in a native language and the anagram of its translation. The user is prompted to
 * enter the (translated word in a foreign language) that is correctly rearranged. The length of the test depends on the size of the vocabulary list.
 * If it is larger than 15, there are fifteen steps in total. Otherwise, there are as many as there are word pairs in the dictionary.
 */

@Composable
fun AnagramScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel,
    dataViewModel: PreferencesViewModel = hiltViewModel()
) {
    val wordList by wordPairViewModel.wordList.observeAsState(listOf())

    AnagramScreen(
        navController = navController,
        wordList = wordList,
        number = getNumberOfQuestions(wordList),
        dataViewModel = dataViewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
private fun AnagramScreen(
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

    // Stores the user's chosen answer
    var userAnswer by rememberSaveable { mutableStateOf("") }

    // Stores random IDs that were used in a test to avoid repeatability
    val storeUsedIndex = rememberSaveable { mutableListOf<Int>() }

    //Stores the anagram of the translated word in a current step
    var anagramed by rememberSaveable { mutableStateOf("") }

    // Test score displayed after the test is finished
    var resultScore by rememberSaveable { mutableStateOf(0) }

    var hasNextStep by rememberSaveable { mutableStateOf(true) }

    if (wordList.isNotEmpty() && hasNextStep) {
        val randomId = randomIndexGenerator(wordList.size - 1, storeUsedIndex)
        storeUsedIndex.add(randomId)

        // Stores a randomly generated word pair that will be the shown in the current step
        val thisWordPair = wordList[randomId]

        wordA = thisWordPair.entryWord
        wordB = thisWordPair.translatedWord

        anagramed = anagramCreator(thisWordPair.translatedWord.toUpperCase())
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
                .width(278.dp)
                .height(85.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = wordA,
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
                .width(278.dp)
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
                    fontSize = 22.sp,
                    modifier = Modifier.padding(all = 10.dp)
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

        Spacer(modifier = Modifier.height(10.dp))

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
            ) {
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

