package uk.ac.aber.dcs.cs31620.vocantastic.ui.words

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

   /* wordPairViewModel.insertWordPair(WordPair(entryWord = "Dupa", translatedWord = "Ass"))
    wordPairViewModel.insertWordPair(WordPair(entryWord = "Dupaaaa", translatedWord = "Assaaa"))
    wordPairViewModel.insertWordPair(WordPair(entryWord = "Dupa3", translatedWord = "Ass3"))*/

    AddWordScreen(navController = navController,
        wordList =  wordList,
        insertWordPair = { wordPair ->
            wordPairViewModel.insertWordPair(wordPair)
        },
        deleteWordPair = { wordPair ->
            wordPairViewModel.deleteWordPair(wordPair)
        })


}
@Composable
fun AddWordScreen(
    navController: NavHostController,
    wordList: List<WordPair>,
    insertWordPair: (WordPair) -> Unit = {},
    deleteWordPair: (WordPair) -> Unit = {}
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
           /* Text (
                text = nativeLanguage.value,
            )*/
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
    val maxChar = 30

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
        if(wordList.isNotEmpty()) {
            Text(
                "${wordList[0]}"
            )
        }
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
                if (it.length <= maxChar) textValueNative = it
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
                if (it.length <= maxChar) textValueForeign = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        /*AddButton() */
        Button(
            onClick = {
              if( textValueNative.trim() == "" || textValueForeign.trim() == "") {

              }else {
                  doInsert(
                      WordPair(
                          entryWord = textValueNative.uppercase().trim(),
                          translatedWord = textValueForeign.toUpperCase().trim()
                      )

                  )
                  //insertWordPair() do listy??????????
              }
                textValueNative = ""
                textValueForeign = ""  // to clear after adding?
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

private fun insertWordPair(
    entryWord: String,
    translatedWord: String,
    doInsert: (WordPair) -> Unit = {}
) {
    if(entryWord.isNotEmpty() && translatedWord.isNotEmpty()){
        val wordPair = WordPair(
            id = 0,
            entryWord = entryWord,
            translatedWord = translatedWord

        )
        doInsert(wordPair)
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
fun AddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(220.dp)
    ) {
        Text(stringResource(id = R.string.add_to_vocabulary_list))
    }
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


@Composable
@Preview
fun AddButtonPreview() {
    VocantasticTheme(dynamicColor = false) {
        AddButton()
    }
}
