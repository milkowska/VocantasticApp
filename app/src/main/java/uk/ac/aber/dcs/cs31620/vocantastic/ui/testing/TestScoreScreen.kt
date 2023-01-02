package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.PreferencesViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.storage.TEST_SCORE
// import uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage.TEST_SCORE
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.Railway


@Composable
fun TestScoreScreenTopLevel(
    navController: NavHostController,

    dataViewModel: PreferencesViewModel = hiltViewModel()
) {
    val score = dataViewModel.getInt(TEST_SCORE)
    TestScoreScreen(navController, modifier = Modifier, score, dataViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScoreScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    score: Int?,
    dataViewModel: PreferencesViewModel = hiltViewModel()
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.test_finished),
            modifier = Modifier.padding(top = 20.dp),
            fontFamily = Railway,
            fontSize = 25.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(id = R.string.your_score),
            fontFamily = Railway,
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .width(210.dp)
                .height(105.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "$score%",
                    fontFamily = Railway,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        if (score != null) {
            if (score < 40) {
                Text(
                    text = stringResource(id = R.string.score_low),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            } else if (score in 40..84) {
                Text(
                    text = stringResource(id = R.string.score_mid),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = stringResource(id = R.string.score_high),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        if (score != null) {
            if (score < 40) {
                Image(
                    modifier = Modifier
                        .size(250.dp),
                    painter = painterResource(id = R.drawable.nice_try),
                    contentDescription = stringResource(id = R.string.score_image_description),
                    contentScale = ContentScale.Crop
                )
            } else {

                Image(
                    modifier = Modifier
                        .size(290.dp),
                    painter = painterResource(id = R.drawable.score),
                    contentDescription = stringResource(id = R.string.score_image_description),
                    contentScale = ContentScale.Crop
                )
            }

            ElevatedButton(
                onClick = {
                    navController.navigate(Screen.Test.route)
                    dataViewModel.saveInt(0, TEST_SCORE)

                }, modifier = modifier
                    .width(220.dp)
                    .height(50.dp)

            ) {
                Text(
                    stringResource(id = R.string.continue_to_next_screen),
                    fontFamily = Railway
                )

            }
        }
    }
}