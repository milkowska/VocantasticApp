package uk.ac.aber.dcs.cs31620.vocantastic.ui.words

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.FOREIGN_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.NATIVE_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.Storage
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.VocantasticTheme

@Composable
fun AddWordScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel()
) {
    val wordList by wordPairViewModel.wordList.observeAsState(listOf())

    AddWordScreen(navController = navController,
        wordList =  wordList,
        insertWordPair = { wordPair ->
            wordPairViewModel.insertWordPair(wordPair)
        }
    )
}

@Composable
fun AddWordScreen(
    navController: NavHostController,
    wordList: List<WordPair>,
    insertWordPair: (WordPair) -> Unit = {},
) {
    TopLevelScaffold(
        navController = navController,
    )
    { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val context = LocalContext.current
            val dataStore = Storage(context)

            val nativeLanguage = dataStore.getString(NATIVE_LANGUAGE_KEY).collectAsState(initial = "")
            val foreignLanguage = dataStore.getString(FOREIGN_LANGUAGE_KEY).collectAsState(initial = "")

            Spacer(modifier = Modifier.height(20.dp))

            AddWordScreenContent(
                modifier = Modifier.padding(10.dp),
                wordList = wordList,
                doInsert = { wordPair ->
                    insertWordPair(wordPair)
                }
            )
        }
    }
}

@Composable
private fun AddWordScreenContent(
    modifier: Modifier = Modifier,
    wordList: List<WordPair>,
    doInsert: (WordPair) -> Unit = {}
) {

    var id by rememberSaveable { mutableStateOf(0) }
    var textValueNative by  rememberSaveable { mutableStateOf("") }
    var textValueForeign by  rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = stringResource(id = R.string.add_word_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.add_word_headerOne),
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.height(2.dp))

        FirstWordTextField(
            modifier = Modifier,
            textValue = textValueNative,
            onValueChange = {
               textValueNative = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.add_word_headerTwo),
            fontSize = 16.sp,
        )

        TranslatedWordTextField(
            modifier = Modifier,
            textValue = textValueForeign,
            onValueChange = {
                textValueForeign = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
              if( textValueNative.trim() == "" || textValueForeign.trim() == "") {
                  Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show()
              }else {
                  doInsert(
                      WordPair(
                          entryWord = textValueNative.lowercase().trim(),
                          translatedWord = textValueForeign.lowercase().trim()
                      )
                  )
                  Toast.makeText(context, "New word pair has been added!", Toast.LENGTH_SHORT).show()
                  textValueNative = ""
                  textValueForeign = ""
              }
            },
            modifier = modifier
                .width(220.dp)
        ) {
            Text(stringResource(id = R.string.add_to_vocabulary_list))
        }

        Spacer(modifier = Modifier.height(2.dp))

        Image(
            modifier = Modifier
                .size(220.dp),
            painter = painterResource(id = R.drawable.add_word),
            contentDescription = stringResource(id = R.string.add_word_image),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun FirstWordTextField(
    modifier: Modifier = Modifier,
    textValue: String = "",
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = textValue,
        label = {
            Text(text = stringResource(R.string.your_language))
        },
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier,
    )
}

@Composable
fun TranslatedWordTextField(
    modifier: Modifier = Modifier,
    textValue: String = "",
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = textValue,
        label = {
            Text(text = stringResource(R.string.foreign_language))
        },
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
    )
}

@Composable
@Preview
fun FirstWordTxtFieldPreview() {
    VocantasticTheme(dynamicColor = false) {
        FirstWordTextField()
    }
}

@Composable
@Preview
fun TranslatedWordTxtFieldPreview() {
    VocantasticTheme(dynamicColor = false) {
        TranslatedWordTextField()
    }
}
