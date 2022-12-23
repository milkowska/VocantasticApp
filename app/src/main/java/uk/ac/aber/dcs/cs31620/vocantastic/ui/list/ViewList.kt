package uk.ac.aber.dcs.cs31620.vocantastic.ui.list

import android.preference.PreferenceActivity
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ViewListScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel()
) {
    val wordsList by wordPairViewModel.wordList.observeAsState(listOf())
    /*wordPairViewModel.clearWordList()*/
    ViewListScreen(
        navController = navController,
        wordList = wordsList,
        doDelete = { wordPair ->
            wordPairViewModel.deleteWordPair(wordPair)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewListScreen(
    navController: NavHostController,
    wordList: List<WordPair> = listOf(),
    doDelete: (WordPair) -> Unit = {}
) {
    TopLevelScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val context = LocalContext.current
            // the empty screen is displayed if the word list is empty.
            if (wordList.isEmpty()) {
                EmptyScreenContent()

            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 18.dp)
                ) {

                    items(wordList) { word ->

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column() {
                                Text(
                                    text = word.entryWord,
                                    modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                )

                                Text(
                                    text = word.translatedWord,
                                    modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                                    fontSize = 16.sp,
                                )
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.Top,
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                    Button(
                                        modifier =
                                        Modifier.padding( end = 20.dp),
                                        onClick = {
                                            doDelete(
                                                WordPair(
                                                    entryWord = word.entryWord,
                                                    translatedWord = word.translatedWord,
                                                    id = word.id
                                                )
                                            )
                                            Toast.makeText(context, "Word pair has been removed.", Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                    {
                                        Text(text = "Delete")
                                    }
                                }

                            }
                        }
                        Divider(startIndent = 0.dp, thickness = 1.dp)
                    }
                }
            }
        }
    }
}


@Composable
fun EmptyScreenContent(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(6.dp))

        Image(
            modifier = Modifier
                .size(340.dp),
            painter = painterResource(id = R.drawable.list_empty),
            contentDescription = stringResource(id = R.string.list_empty),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Your list is empty! Go to Add\n" +
                    " tab to note a new word. ",
            lineHeight = 1.2.em,
            fontSize = 27.sp,
            modifier = modifier
        )
    }
}

