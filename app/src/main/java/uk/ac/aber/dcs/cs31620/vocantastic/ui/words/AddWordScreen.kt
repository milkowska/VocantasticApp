package uk.ac.aber.dcs.cs31620.vocantastic.ui.words

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.Railway

@Composable
fun AddWordScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel()
) {
    AddWordScreen(
        navController = navController,
        insertWordPair = { wordPair ->
            wordPairViewModel.insertWordPair(wordPair)
        }
    )
}

@Composable
fun AddWordScreen(
    navController: NavHostController,
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
            Spacer(modifier = Modifier.height(20.dp))

            AddWordScreenContent(
                modifier = Modifier.padding(10.dp),
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
    doInsert: (WordPair) -> Unit = {}
) {

    var textValueNative by rememberSaveable { mutableStateOf("") }
    var textValueForeign by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    var isErrorInNativeTextField by remember {
        mutableStateOf(false)
    }
    var isErrorInForeignTextField by remember {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
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

        OutlinedTextField(
            value = textValueNative,
            label = {
                Text(text = stringResource(R.string.your_language))
            },
            onValueChange = {
                textValueNative = it
                isErrorInNativeTextField = textValueNative.isEmpty()
            },
            singleLine = true,
            isError = isErrorInNativeTextField,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.add_word_headerTwo),
            fontSize = 16.sp,
        )

        OutlinedTextField(
            value = textValueForeign,
            label = {
                Text(text = stringResource(R.string.foreign_language))
            },
            onValueChange = {
                textValueForeign = it
                isErrorInForeignTextField = textValueForeign.isEmpty()
            },
            singleLine = true,
            isError = isErrorInForeignTextField,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            enabled = textValueNative.isNotEmpty() || textValueForeign.isNotEmpty(),
            onClick = {
                if (textValueNative.trim() == "" || textValueForeign.trim() == "") {
                    Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show()
                    if (textValueNative.trim() == "") {
                        isErrorInNativeTextField = true
                    } else if (textValueForeign.trim() == "") {
                        isErrorInForeignTextField = true
                    }
                } else {
                    doInsert(
                        WordPair(
                            entryWord = textValueNative.lowercase().trim(),
                            translatedWord = textValueForeign.lowercase().trim()
                        )
                    )
                    Toast.makeText(context, "New word pair has been added!", Toast.LENGTH_SHORT)
                        .show()
                    textValueNative = ""
                    textValueForeign = ""
                }
            },
            modifier = modifier
                .width(220.dp)
                .height(50.dp)
        ) {
            Text(
                stringResource(id = R.string.add_to_vocabulary_list),
                fontFamily = Railway
            )
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

