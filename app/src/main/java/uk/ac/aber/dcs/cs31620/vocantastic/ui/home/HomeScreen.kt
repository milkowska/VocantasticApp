package uk.ac.aber.dcs.cs31620.vocantastic.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
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
import java.util.*


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

                Text(
                    text = stringResource(id = R.string.home_title),
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Image(
                    modifier = Modifier
                        .size(380.dp),
                    painter = painterResource(id = R.drawable.transparent_home_screen_image),
                    contentDescription = stringResource(id = R.string.welcome_image),
                    contentScale = ContentScale.Crop
                )

                if (nativeLanguage != null) {
                    Text(
                        text = "I speak ${nativeLanguage.capitalize(Locale.ROOT)}, and",
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
                if (foreignLanguage != null) {
                    Text(
                        text = "I want to learn ${foreignLanguage.capitalize(Locale.ROOT)}!",
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

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
    FilledTonalButton(
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
