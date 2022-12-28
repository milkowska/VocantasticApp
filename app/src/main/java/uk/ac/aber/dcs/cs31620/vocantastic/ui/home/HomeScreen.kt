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
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.FOREIGN_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.NATIVE_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.Storage
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.VocantasticTheme
import uk.ac.aber.dcs.cs31620.vocantastic.ui.welcome.WelcomeScreen

var welcomeDone: Boolean = false

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreenTopLevel(
    navController: NavHostController,
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
                    text = "I speak ${nativeLanguage.value}" ,
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "I want to learn ${foreignLanguage.value}",
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(35.dp))

                SettingsButton(
                    modifier = Modifier
                        .height(50.dp),
                    onClick = {
                        navController.navigate(Screen.Settings.route)
                    }
                )
            }
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
        Text(
            stringResource(R.string.settings),
            fontSize = 16.sp
        )
    }
}
