package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation.Screen


@Composable
fun TestScoreScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel()
) {
    TestScoreScreen(navController, wordPairViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScoreScreen(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel,
    modifier: Modifier = Modifier,
) {
    val score = 50

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.test_finished),
            modifier = Modifier.padding(top = 20.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.your_score),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(40.dp))

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
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (score < 40) {
            Image(
                modifier = Modifier
                    .size(350.dp),
                painter = painterResource(id = R.drawable.nice_try),
                contentDescription = stringResource(id = R.string.score_image_description),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                modifier = Modifier
                    .size(350.dp),
                painter = painterResource(id = R.drawable.score),
                contentDescription = stringResource(id = R.string.score_image_description),
                contentScale = ContentScale.Crop
            )
        }

        Button(
            onClick = {
                navController.navigate(Screen.Test.route)
                //TODO clear score
            }, modifier = modifier
                .width(220.dp)
                .height(50.dp)
        ) {
            Text(stringResource(id = R.string.continue_to_next_screen))
        }
    }
}