package uk.ac.aber.dcs.cs31620.vocantastic.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.PreferencesViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.storage.*
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.Railway


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreenTopLevel(
    navController: NavHostController,
    dataViewModel: PreferencesViewModel = hiltViewModel()
) {
    HomeScreen(navController, modifier = Modifier, dataViewModel = dataViewModel)
}

@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier,
    dataViewModel: PreferencesViewModel = hiltViewModel()
) {
    TopLevelScaffold(
        navController = navController,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val nativeLanguage = dataViewModel.getString(NATIVE_LANGUAGE_KEY)
            val foreignLanguage = dataViewModel.getString(FOREIGN_LANGUAGE_KEY)

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
                    text = "I speak $nativeLanguage",
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "I want to learn $foreignLanguage",
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
            fontSize = 17.sp,
            fontFamily = Railway
        )
    }
}
