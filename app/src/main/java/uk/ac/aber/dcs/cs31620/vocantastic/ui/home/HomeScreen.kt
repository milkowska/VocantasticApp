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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.FOREIGN_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.NATIVE_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.Storage
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.VocantasticTheme
import uk.ac.aber.dcs.cs31620.vocantastic.ui.welcome.WelcomeScreen

var welcomeDone: Boolean = false

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel()
    ) {

//TODO welcome key here
    if (!welcomeDone) {
        WelcomeScreen(navController)
    } else {
        HomeScreen(navController, modifier = Modifier)
    }
}

@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier
) {
    TopLevelScaffold(
        navController = navController,
        //  coroutineScope = coroutineScope,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            val context = LocalContext.current
            val dataStore = Storage(context)
            // val scope = rememberCoroutineScope()


            val nativeLanguage =
                dataStore.getString(NATIVE_LANGUAGE_KEY).collectAsState(initial = "")
            val foreignLanguage =
                dataStore.getString(FOREIGN_LANGUAGE_KEY).collectAsState(initial = "")



            /*   HomeScreenContent(
                modifier = Modifier.padding(10.dp),
                nativeLanguage.value,
                foreignLanguage.value
            )*/

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


                Text(
                    text = "I speak ${nativeLanguage.value}"
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "I want to learn ${foreignLanguage.value}"
                )


                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = {

                        Toast.makeText(context, "settingsss", Toast.LENGTH_LONG).show()
                    },
                    modifier = modifier
                        .width(182.dp)
                ) {
                    Text(stringResource(R.string.settings))
                }
                /* SettingsButton(
                    modifier = Modifier
                        .padding(15.dp),
                    onClick = {
                    }
                )*/

            }
        }
    }


    @Composable
    fun HomeScreenContent(
        modifier: Modifier = Modifier,
        nativeLanguage: String,
        foreignLanguage: String
    ) {

        val context = LocalContext.current

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


            Text(
                text = "I speak $nativeLanguage"
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "I want to learn $foreignLanguage"
            )


            Spacer(modifier = Modifier.height(25.dp))
/*
        SettingsButton(
            modifier = Modifier
                .padding(15.dp),
            onClick = {
            }
        )*/


        }
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
        var seclang by rememberSaveable { mutableStateOf("") }


        Text(
            text = "I want to learn: $seclang",
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
}

/*
@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    VocantasticTheme(dynamicColor = false) {
        HomeScreen(navController)
    }
}*/
