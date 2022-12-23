package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.FOREIGN_LANGUAGE_KEY
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

    val numberOfQuestions = 1
    val foreignLanguage = storage.getString(FOREIGN_LANGUAGE_KEY).collectAsState(initial = "")

    if (numberOfQuestions != null && foreignLanguage != null) {
        AnagramScreen(
            navController = navController,
            wordList = words,
            number = numberOfQuestions,
            language = foreignLanguage.value
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnagramScreen(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel(),
    wordList: List<WordPair>,
    number: Int,
    language: String
) {

    // test score displayed after the test is finished
    var resultScore by rememberSaveable { mutableStateOf(0) }

    var step by rememberSaveable { mutableStateOf(1) }
    // change
    val stepsQuantity = 5

    var userAnswer by rememberSaveable { mutableStateOf("") }
    var correctFirstValue by rememberSaveable { mutableStateOf("") }
    var correctAnswer by rememberSaveable { mutableStateOf("") }


    val storeUsedIndex = rememberSaveable { mutableListOf<Int>() }

    var hasNext by rememberSaveable { mutableStateOf(true) }


    if (wordList.isNotEmpty() && hasNext) {
        val randomId = randomIndexGenerator(wordList.size - 1, storeUsedIndex)
        storeUsedIndex.add(randomId)

        val nextWordPair = wordList[randomId]

        correctFirstValue = nextWordPair.entryWord
        correctAnswer = nextWordPair.translatedWord

        hasNext = false

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val progressValue = step / stepsQuantity.toFloat()

        Text(
            text = stringResource(id = R.string.anagram),
            modifier = Modifier.padding(start = 8.dp, top = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        LinearProgressIndicator(progressValue, modifier = Modifier.fillMaxSize(1f))

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.the_anagram_text),
            fontSize = 20.sp
        )

        //CardWithBorder(nativeWord =)

        Button(modifier = Modifier
            .height(60.dp)
            .width(200.dp),
            enabled = userAnswer.isNotEmpty(),
            onClick = {
                if (userAnswer.lowercase().trim() == correctAnswer.lowercase().trim()) {
                    resultScore++
                }
                if (step >= stepsQuantity) {
                    val finalScore = (resultScore * 100) / stepsQuantity
                    //store it by viewmodel


                    navController.navigate(Screen.TestScore.route)

                } else if (wordList.isNotEmpty() && (step < stepsQuantity)) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardWithBorder(nativeWord: String) {

    Card(
        //  elevation = 10.dp,
        border = BorderStroke(1.dp, Color.Blue),
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(text = nativeWord, modifier = Modifier)
    }
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