package uk.ac.aber.dcs.cs31620.vocantastic.ui.home

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.FOREIGN_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.NATIVE_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.Storage
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.WELCOME_SCREEN
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.vocantastic.ui.list.EmptyScreenContent
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.VocantasticTheme
import uk.ac.aber.dcs.cs31620.vocantastic.ui.welcome.WelcomeScreen


@Composable
fun HomeScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel(),
    wordList: List<WordPair> = listOf()
) {
    if (wordList.isEmpty() ) {

        WelcomeScreenContent()

    } else {

        HomeScreen(navController, wordList)
    }
}

@Composable
fun HomeScreen(
    navController: NavHostController,
    wordList: List<WordPair> = listOf()

) {
    val coroutineScope = rememberCoroutineScope()

    TopLevelScaffold(
        navController = navController,
        //  coroutineScope = coroutineScope,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
          /*  if (wordList.isEmpty() ) {

                WelcomeScreenContent()

            } else {

                HomeScreenContent(
                    modifier = Modifier.padding(10.dp),
                    )
            }*/
            HomeScreenContent(
                modifier = Modifier.padding(10.dp),
            )
        }
    }
}


@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier

) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Storage(context)

    val textValueNative = rememberSaveable { mutableStateOf("") }
    val textValueForeign = rememberSaveable { mutableStateOf("") }
    val updatedValueNative = rememberSaveable { mutableStateOf("") }
    val updatedValueForeign = rememberSaveable { mutableStateOf("") }

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
            painter = painterResource(id = R.drawable.transparent_home_screen_image),
            contentDescription = stringResource(id = R.string.welcome_image),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(15.dp))

        CurrentFirstLanguageText(
            firstLanguage = updatedValueNative.value,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 0.dp, 10.dp, 5.dp)

        )

        Spacer(modifier = Modifier.height(15.dp))

        CurrentForeignLanguageText(

            secondLanguage = updatedValueForeign.value,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 0.dp, 10.dp, 5.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        SettingsButton(
            modifier = Modifier
                .padding(15.dp),
            onClick = {
            }
        )

       // scope.launch {
      //      val saved = dataStore.getString(NATIVE_LANGUAGE_KEY)
       //     Toast.makeText(context, "$saved", Toast.LENGTH_LONG).show()

       // }


    }
}


@Composable
private fun WelcomeScreenContent(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Storage(context)

    var nativeLanguage by rememberSaveable { mutableStateOf("") }
    var secondLanguage by rememberSaveable { mutableStateOf("") }
    val maxChar = 20
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {

        Image(
            modifier = Modifier
                .size(220.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.logo_image),
            contentScale = ContentScale.Fit
        )



        Text(
            text = stringResource(id = R.string.learning_journey_text),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(360.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        YourLanguageTextField(
            modifier = Modifier,
            textValue = nativeLanguage,
            onValueChange = {
                if (it.length <= maxChar) nativeLanguage = it
            }
        )

        Spacer(modifier = Modifier.height(36.dp))

        ForeignLanguageTextField(
            textValue = secondLanguage,
            onValueChange = {
                if (it.length <= maxChar) secondLanguage = it
            }
        )

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = {
                if (nativeLanguage.trim() == "" || secondLanguage.trim() == "") {
                    Toast.makeText(context, "Invalid input", Toast.LENGTH_LONG).show()
                } else {
                    scope.launch {
                        dataStore.saveString(nativeLanguage, NATIVE_LANGUAGE_KEY)
                        dataStore.saveString(secondLanguage, FOREIGN_LANGUAGE_KEY)

                    }
                    Toast.makeText(context, "Languages are set", Toast.LENGTH_LONG).show()

                }
            },
            modifier = modifier
                .width(220.dp)
        ) {
            Text(stringResource(id = R.string.continueToNextScreen))
        }


    }
}




@Composable
fun CurrentFirstLanguageText(
    firstLanguage: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "I speak: $firstLanguage",
        fontSize = 20.sp,
        modifier = modifier
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CurrentForeignLanguageText(
    secondLanguage: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dataStore = Storage(context)
    val scope = rememberCoroutineScope()

    scope.launch {
        val ye = dataStore.getString(FOREIGN_LANGUAGE_KEY)
    }

    Text(
        text = "I want to learn: $secondLanguage",
        fontSize = 20.sp,
        modifier = modifier,

        )
}

@Composable
fun YourLanguageTextField(
    modifier: Modifier = Modifier,
    textValue: String = "",
    onValueChange: (String) -> Unit = {}
) {

    OutlinedTextField(
        value = textValue,
        label = {
            Text(text = stringResource(id = R.string.your_language))
        },
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
    )
}

@Composable
fun ForeignLanguageTextField(
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
        modifier = modifier,

        )
}

@Composable
fun SettingsButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(182.dp)
    ) {
        Text(stringResource(R.string.settings))
    }
}


@Preview
@Composable
fun SettingsBtnPreview() {
    VocantasticTheme(dynamicColor = false) {
        SettingsButton()
    }
}

@Preview
@Composable
fun YourLanguageTextFieldPreview() {
    VocantasticTheme(dynamicColor = false) {
        YourLanguageTextField()
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    VocantasticTheme(dynamicColor = false) {
        HomeScreen(navController)
    }
}