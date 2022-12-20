package uk.ac.aber.dcs.cs31620.vocantastic.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold


@Composable
fun EmptyListScreen(
    navController: NavHostController
) {
    TopLevelScaffold(
        navController = navController,
    ){ innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            EmptyScreenContent(
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
private fun EmptyScreenContent(
    modifier: Modifier = Modifier
) {

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
            painter = painterResource(id = R.drawable.list_empty),
            contentDescription = stringResource(id = R.string.list_empty),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Your list is empty! Go to Add\n" +
                    " tab to note a new word. ",
            lineHeight = 1.2.em,
            fontSize = 27.sp,
            modifier = modifier
        )
    }
}

