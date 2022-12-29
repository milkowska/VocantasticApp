package uk.ac.aber.dcs.cs31620.vocantastic.ui.home

import android.text.method.TextKeyListener.clear
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.FOREIGN_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.NATIVE_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.PreferencesViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.welcome.WelcomeScreen

@Composable
fun SettingsScreenTopLevel(
    navController: NavHostController,
    dataViewModel : PreferencesViewModel = hiltViewModel(),
    wordPairViewModel: WordPairViewModel = viewModel()

) {
    SettingsScreen(navController = navController, dataViewModel, wordPairViewModel)
}

@Composable
fun SettingsScreen(
    navController: NavHostController,
    dataViewModel : PreferencesViewModel = hiltViewModel(),
    wordPairViewModel: WordPairViewModel = viewModel()

) {
    val context = LocalContext.current
    val openAlert = remember { mutableStateOf(false) }
    var nativeLanguage by rememberSaveable { mutableStateOf("") }
    var secondLanguage by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopAppBar(
            elevation = 5.dp,
            title = {
                Text(
                    text = stringResource(id = R.string.settings),
                    fontSize = 17.sp,
                )
            },
            backgroundColor = MaterialTheme.colorScheme.background,
            navigationIcon = {
                IconButton(onClick = { navController.navigate(route = Screen.Home.route) }) {
                    Icon(Icons.Filled.ArrowBack, "go back")
                }
            },
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = stringResource(id = R.string.settings_title),
            fontSize = 19.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            modifier = Modifier
                .width(150.dp),
            painter = painterResource(id = R.drawable.thinking_pose),
            contentDescription = stringResource(id = R.string.thinking_image),
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = nativeLanguage,
            label = {
                Text(text = stringResource(R.string.your_language))
            },
            onValueChange = { nativeLanguage = it },
            singleLine = true,
        )

        Text(
            text = stringResource(id = R.string.your_own_language),
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(end = 60.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = secondLanguage,
            label = {
                Text(text = stringResource(R.string.foreign_language))
            },
            onValueChange = { secondLanguage = it },
            singleLine = true,
        )

        Text(
            text = stringResource(id = R.string.foreign_language_to_learn),
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(end = 50.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            enabled = nativeLanguage.isNotEmpty() || secondLanguage.isNotEmpty(),
            onClick = {
                if (nativeLanguage.trim() == "" || secondLanguage.trim() == "") {
                    Toast.makeText(context, "Invalid input", Toast.LENGTH_LONG).show()
                } else {

                    dataViewModel.saveString(nativeLanguage.trim().toLowerCase(), NATIVE_LANGUAGE_KEY)
                    dataViewModel.saveString(secondLanguage.trim().toLowerCase(), FOREIGN_LANGUAGE_KEY)
                    wordPairViewModel.clearWordList()

                    openAlert.value = true
                }
            },
            modifier = Modifier
                .height(45.dp)
                .width(182.dp)
        ) {
            Text(stringResource(id = R.string.save))
        }

        if (openAlert.value) {

            AlertDialog(
                onDismissRequest = {
                    openAlert.value = false
                },
                text = {
                    Text(stringResource(R.string.new_config_text))
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openAlert.value = false
                            navController.navigate(route = Screen.Home.route)
                        },
                    ) {
                        Text(stringResource(R.string.ok))
                    }
                },
                )
        }
    }
}

