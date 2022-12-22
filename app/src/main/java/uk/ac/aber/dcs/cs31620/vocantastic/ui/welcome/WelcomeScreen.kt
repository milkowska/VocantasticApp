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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.FOREIGN_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.NATIVE_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.Storage
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold

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
            WelcomeScreenContent(
                modifier = Modifier.padding(10.dp)
            )

        }
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
                .size(265.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.logo_image),
            contentScale = ContentScale.Crop
        )

        YourLanguageTextField(
            modifier = Modifier,
            textValue = nativeLanguage,
            onValueChange = {
                if (it.length <= maxChar) nativeLanguage = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ForeignLanguageTextField(
            textValue = secondLanguage,
            onValueChange = {
                if (it.length <= maxChar) secondLanguage = it
            }
        )

        Spacer(modifier = Modifier.height(6.dp))

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
fun YourLanguageTextField(
    modifier: Modifier = Modifier,
    textValue: String = "",
    onValueChange: (String) -> Unit = {}
) {

    //TODO change UI so that current language selection is visible!!! eg. your first language choice is "english" and the one you want to learn is "polish"

    //var text by remember { mutableStateOf(TextFieldValue("")) }
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
