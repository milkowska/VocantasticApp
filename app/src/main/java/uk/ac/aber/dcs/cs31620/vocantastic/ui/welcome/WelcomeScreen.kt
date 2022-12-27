package uk.ac.aber.dcs.cs31620.vocantastic.ui.welcome

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.FOREIGN_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.NATIVE_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.Storage
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.vocantastic.ui.home.welcomeDone
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen

//TODO this file is not finished
@Composable
fun WelcomeScreen(
    navController: NavHostController
) {
    TopLevelScaffold(
        navController = navController,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val context = LocalContext.current
            val dataStore = Storage(context)

            val nativeLanguage =
                dataStore.getString(NATIVE_LANGUAGE_KEY).collectAsState(initial = "")
            val foreignLanguage =
                dataStore.getString(FOREIGN_LANGUAGE_KEY).collectAsState(initial = "")
            WelcomeScreenContent(
                modifier = Modifier.padding(10.dp),
                navController
            )

        }
    }
}

@Composable
private fun WelcomeScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = Storage(context)

    var nativeLanguage by rememberSaveable { mutableStateOf("") }
    var secondLanguage by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {


        Image(
            modifier = Modifier

                .width(340.dp)
                .height(190.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.logo_image),
            contentScale = ContentScale.FillWidth,

            )
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(id = R.string.learning_journey_text),
            fontSize = 19.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(360.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        YourLanguageTextField(
            modifier = Modifier,
            textValue = nativeLanguage,
            onValueChange = {
                nativeLanguage = it
            }
        )
        Text(
            text = "This is your own language",
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(end = 60.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ForeignLanguageTextField(
            modifier = Modifier,
            textValue = secondLanguage,
            onValueChange = {
                secondLanguage = it
            }
        )

        Text(
            text = "The language you will learn",
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(end = 50.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (nativeLanguage.trim() == "" || secondLanguage.trim() == "") {
                    Toast.makeText(context, "Invalid input", Toast.LENGTH_LONG).show()
                } else {
                    scope.launch {
                        dataStore.save(nativeLanguage, NATIVE_LANGUAGE_KEY)
                        dataStore.save(secondLanguage, FOREIGN_LANGUAGE_KEY)
                        /*dataStore.saveString(nativeLanguage, NATIVE_LANGUAGE_KEY)
                        dataStore.saveString(secondLanguage, FOREIGN_LANGUAGE_KEY)*/
                        /* dataStore.setBoolean(true, WELCOME_SCREEN)*/
                    }
                    Toast.makeText(context, "Languages are set", Toast.LENGTH_LONG).show()

                    welcomeDone = true
                    navController.navigate(route = Screen.Home.route)
                }

                // after the first language initialization it goes to Home screen

            },
            modifier = modifier
                .width(220.dp)
                .height(50.dp)
        ) {
            Text(stringResource(id = R.string.continue_to_next_screen))
        }

    }
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
