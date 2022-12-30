package uk.ac.aber.dcs.cs31620.vocantastic.ui.welcome

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.*

import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen

//TODO this file is not finished
@Composable
fun WelcomeScreen(
    navController: NavHostController,
    dataViewModel : PreferencesViewModel = hiltViewModel()
) {
    WelcomeScreenContent(
        modifier = Modifier.padding(10.dp),
        navController,
        dataViewModel = dataViewModel
    )
}

@Composable
private fun WelcomeScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    dataViewModel : PreferencesViewModel = hiltViewModel()
) {
    val context = LocalContext.current

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
            text = stringResource(id = R.string.your_own_language),
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
            text = stringResource(id = R.string.foreign_language_to_learn),
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(end = 50.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(

            enabled = nativeLanguage.isNotEmpty() || secondLanguage.isNotEmpty(),
            onClick = {

                if (nativeLanguage.trim() == "" || secondLanguage.trim() == "") {
                    Toast.makeText(context, "Invalid input", Toast.LENGTH_LONG).show()
                } else {

                    dataViewModel.saveString(nativeLanguage.trim().toLowerCase(), NATIVE_LANGUAGE_KEY)
                    dataViewModel.saveString(secondLanguage.trim().toLowerCase(), FOREIGN_LANGUAGE_KEY)

                    dataViewModel.saveBoolean(true, WELCOME_SCREEN)
                    Toast.makeText(context, "Languages are set", Toast.LENGTH_LONG).show()
/*
                    welcomeDone = true*/
                    navController.navigate(route = Screen.Home.route)
                }

            },
            modifier = modifier
                .width(220.dp)
                .height(50.dp)
        ) {
            Text(
                stringResource(id = R.string.continue_to_next_screen),
                fontSize = 16.sp
            )
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
