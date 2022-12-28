package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen

@Composable
fun FindAnswerScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel()
) {
    val wordList by wordPairViewModel.wordList.observeAsState(listOf())

    /* sets the number of steps in the test. If the list is large, the number of
    * questions is set to 15 by default
    */
    val numberOfQuestions: Int = if (wordList.size >= 15) {
        15
    } else {
        wordList.size
    }

    FindAnswerScreen(
        navController = navController,
        wordList = wordList,
        number = numberOfQuestions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindAnswerScreen(
    navController: NavHostController,
    wordList: List<WordPair>,
    number: Int
) {

    var step by rememberSaveable { mutableStateOf(1) }
    // test score displayed after the test is finished
    var resultScore by rememberSaveable { mutableStateOf(0) }


    // this is a word pair set, eg. A window = okno
    var correctFirstValue by rememberSaveable { mutableStateOf("") } // a window
    var correctAnswer by rememberSaveable { mutableStateOf("") } // okno

    var userAnswer by rememberSaveable { mutableStateOf("") }

    //val storeUsedIndex = rememberSaveable { mutableListOf<Int>() }

    var hasNext by rememberSaveable { mutableStateOf(true) }

    //uniqueness of other questions and answers
    val entryWordsList = rememberSaveable { mutableListOf<Int>() }
    val answersList = rememberSaveable { mutableListOf<Int>() }

    // a list of possible answers
    val multipleChoiceList = rememberSaveable { mutableListOf<WordPair>() }


    if (wordList.isNotEmpty() && hasNext) {
        // generate next word pair
        val nextWordPair = randomIndexGenerator(wordList.size - 1, entryWordsList)

        entryWordsList.add(nextWordPair)
        answersList.add(nextWordPair)

        //randomly generated word pair
        val thisWordPair = wordList[nextWordPair]

        correctFirstValue = thisWordPair.entryWord
        correctAnswer = thisWordPair.translatedWord

        //the word pair is added to a multiple choices list
        multipleChoiceList.add(thisWordPair)

        for (index in 1..3) {
            val wrongAnswer = randomIndexGenerator(wordList.size - 1, answersList)
            multipleChoiceList.add(wordList[wrongAnswer])
            answersList.add(wrongAnswer)
        }

        multipleChoiceList.shuffle()
        hasNext = false
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val progressValue = step / number.toFloat()

        Text(
            text = stringResource(id = R.string.find_correct_answer),
            modifier = Modifier.padding(start = 8.dp, top = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(50.dp))

        LinearProgressIndicator(progressValue)

        Spacer(modifier = Modifier.height(50.dp))

        Card(
            modifier = Modifier
                .width(240.dp)
                .height(100.dp)
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
            ) {
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

            Button(modifier = Modifier
                .height(60.dp)
                .width(200.dp)
                .weight(0.5f),
                onClick = {
                    navController.navigate(Screen.Test.route)
                }
            ) {
                Text(text = "Quit")
            }

            Spacer(modifier = Modifier.width(30.dp))

            Button(modifier = Modifier
                .height(60.dp)
                .width(200.dp)
                .weight(0.5f),
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
                        multipleChoiceList.clear()
                        answersList.clear()
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