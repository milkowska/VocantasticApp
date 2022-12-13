package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold


@Composable
fun TestScreen(
    navController: NavHostController
) {
    TopLevelScaffold(
        navController = navController,
    )
    { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TestScreenContent(
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}
@Composable
fun TestScreenContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = stringResource(id = R.string.test_progress_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(18.dp))


        Image(
            modifier = Modifier
                .size(270.dp),
            painter = painterResource(id = R.drawable.transparent_brain),
            contentDescription = stringResource(id = R.string.brain_image),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = stringResource(id = R.string.test_choice),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(12.dp))

        AnagramTestButton(
            modifier = Modifier
                .width(250.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        CorrectAnswerButton(
            modifier = Modifier
                .width(250.dp)
        )


    }
}
@Composable
fun AnagramTestButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(220.dp)
    ) {
        Text(stringResource(id = R.string.anagram))
    }
}
@Composable
fun CorrectAnswerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(220.dp)
    ) {
        Text(stringResource(id = R.string.find_correct_answer))
    }

}