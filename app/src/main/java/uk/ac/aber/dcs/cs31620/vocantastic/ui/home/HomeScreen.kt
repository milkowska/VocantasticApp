package uk.ac.aber.dcs.cs31620.vocantastic.ui.home

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
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.VocantasticTheme


@Composable
fun HomeScreen(navController: NavHostController) {
    TopLevelScaffold(
        navController = navController,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HomeScreenContent(
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            modifier = Modifier
                .size(310.dp),

            painter = painterResource(id = R.drawable.home_screen_welcome_image),
            contentDescription = stringResource(id = R.string.welcome_image),
            contentScale = ContentScale.Crop
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        YourLanguageButton()

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stringResource(id = R.string.your_language_supporter),
            fontSize = 12.sp,
            modifier = Modifier
                .absolutePadding(0.dp,0.dp,90.dp,0.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        ForeignLanguageButton()

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stringResource(id = R.string.foreign_language_supporter),
            fontSize = 12.sp,
            modifier = Modifier
                .absolutePadding(0.dp,0.dp,90.dp,0.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        SaveConfigurationButton()
    }
}

@Composable
fun YourLanguageButton(
    modifier: Modifier = Modifier,
    textValue: String = "",
    onValueChange: (String) -> Unit = {}
) {
    //var text by remember { mutableStateOf(TextFieldValue(""))
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
fun ForeignLanguageButton(
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

@Composable
fun SaveConfigurationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(stringResource(id = R.string.save_configuration))
    }
}

@Preview
@Composable
fun SaveConfigBtnPreview() {
    VocantasticTheme(dynamicColor = false) {
        SaveConfigurationButton()
    }
}

@Preview
@Composable
fun YourLanguageBtnPreview() {
    VocantasticTheme(dynamicColor = false) {
        YourLanguageButton()
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    VocantasticTheme(dynamicColor = false) {
        HomeScreen(navController)
    }
}