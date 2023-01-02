package uk.ac.aber.dcs.cs31620.vocantastic.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.PreferencesViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.randomIndexGenerator
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.Railway

@Composable
fun FlashcardScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel,
) {
    val wordList by wordPairViewModel.wordList.observeAsState(listOf())

    FlashcardScreen(
        navController = navController,
        wordList = wordList,
        number = wordList.size,
    )
}

@Composable
fun FlashcardScreen(
    navController: NavHostController,
    wordList: List<WordPair>,
    number: Int,
) {
    // Indicates the question number, starts with 1
    var step by rememberSaveable { mutableStateOf(1) }

    // This is a word pair set, eg.  a window -> okno
    var wordA by rememberSaveable { mutableStateOf("") } // a window
    var wordB by rememberSaveable { mutableStateOf("") } // okno

    // Stores random IDs that were used in a test to avoid repeatability
    val storeUsedIndex = rememberSaveable { mutableListOf<Int>() }

    var hasNextCard by rememberSaveable { mutableStateOf(true) }

    if (wordList.isNotEmpty() && hasNextCard) {
        val randomId = randomIndexGenerator(wordList.size - 1, storeUsedIndex)
        storeUsedIndex.add(randomId)

        // Stores a randomly generated word pair that will be the shown in the current step
        val thisWordPair = wordList[randomId]

        wordA = thisWordPair.entryWord
        wordB = thisWordPair.translatedWord

        hasNextCard = false
    }
    var cardFace by remember {
        mutableStateOf(CardFace.Front)
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
            text = stringResource(id = R.string.flashcard),
            modifier = Modifier.padding(start = 8.dp, top = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        LinearProgressIndicator(progressValue)

        Spacer(modifier = Modifier.height(20.dp))

        FlipCard(
            cardFace = cardFace,
            onClick = { cardFace = cardFace.next },
            modifier = Modifier
                .fillMaxWidth(.5f)
                .aspectRatio(1f),
            front = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = wordA,
                        fontFamily = Railway,
                    )
                }
            },
            back = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = wordB,
                        fontFamily = Railway,
                    )
                }
            },
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
                onClick = {

                    if (step >= number) {
                        step = 0
                    } else if (wordList.isNotEmpty() && (step < number)) {
                        hasNextCard = true
                        step++
                    }
                })
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