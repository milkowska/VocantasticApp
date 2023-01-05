package uk.ac.aber.dcs.cs31620.vocantastic.flashcard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.randomIndexGenerator
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.Railway

/**
 * Flashcards are a way for the user to revise the vocabulary. It uses CardFace enum and a FlipCard file which is responsible for flipping the
 * word, where a flipped word of the original word is its translation (word pair).
 */
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
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val progressValue = step / number.toFloat()

        val numberOfFlipped = step - 1

        androidx.compose.material.TopAppBar(
            elevation = 5.dp,
            title = {
                Text(
                    text = stringResource(id = R.string.flashcard),
                    fontSize = 18.sp,
                )
            },
            backgroundColor = MaterialTheme.colorScheme.background,
            navigationIcon = {
                IconButton(onClick = { navController.navigate(route = Screen.Test.route) }) {
                    Icon(Icons.Filled.ArrowBack, "go back")
                }
            },
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.flashcard_info),
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(60.dp))

        LinearProgressIndicator(progressValue)

        Spacer(modifier = Modifier.height(60.dp))

        FlipCard(
            cardFace = cardFace,
            onClick = { cardFace = cardFace.next },
            modifier = Modifier
                .fillMaxWidth(.5f)
                .aspectRatio(1f),
            front = {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(all =15.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = wordA,
                        fontFamily = Railway,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )
                }
            },
            back = {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(all = 15.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = wordB,
                        fontFamily = Railway,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(50.dp))

        if (numberOfFlipped == 1) {
            Text(
                text = "You have flipped $numberOfFlipped flashcard so far!",
                fontSize = 20.sp
            )

        } else {
            Text(text = "You have flipped $numberOfFlipped flashcards so far!",
                fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(50.dp))

        ElevatedButton(modifier = Modifier
            .height(60.dp)
            .width(140.dp),
            onClick = {
                if (step >= number) {
                    navController.navigate(Screen.Test.route)
                } else if (wordList.isNotEmpty() && (step < number)) {
                    hasNextCard = true
                    step++
                }
            })
        {
            Text(
                text = stringResource(id = R.string.next)
            )
        }
    }
}