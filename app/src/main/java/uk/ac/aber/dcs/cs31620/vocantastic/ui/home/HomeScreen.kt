package uk.ac.aber.dcs.cs31620.vocantastic.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.vocantastic.R
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.vocantastic.ui.theme.VocantasticTheme


@Composable
fun HomeScreenTopLevel(
    navController: NavHostController,
    wordPairViewModel: WordPairViewModel = viewModel()
)
{
    HomeScreen(
        navController = navController
    )
}
@Composable
fun HomeScreen(navController: NavHostController,

) {
    val coroutineScope = rememberCoroutineScope()

    TopLevelScaffold(
        navController = navController,
      //  coroutineScope = coroutineScope,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HomeScreenContent(
                modifier = Modifier.padding(10.dp),

            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,

) {
    val textValueNative = rememberSaveable { mutableStateOf("") }
    val textValueForeign = rememberSaveable { mutableStateOf("") }
    val updatedValueNative = rememberSaveable { mutableStateOf("") }
    val updatedValueForeign = rememberSaveable { mutableStateOf("") }

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

        CurrentFirstLanguageText(
            firstLanguage = updatedValueNative.value,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 0.dp, 10.dp, 5.dp)

        )

        Spacer(modifier = Modifier.height(15.dp))

        CurrentForeignLanguageText(
            secondLanguage = updatedValueForeign.value,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 0.dp, 10.dp, 5.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        SettingsButton(
            modifier = Modifier
                .padding(15.dp),
            onClick = {
            }
        )
    }
}

@Composable
fun CurrentFirstLanguageText(
    firstLanguage: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "I speak: $firstLanguage",
        fontSize = 20.sp,
        modifier = modifier
    )
}

@Composable
fun CurrentForeignLanguageText(
    secondLanguage: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "I want to learn: $secondLanguage",
        fontSize = 20.sp,
        modifier = modifier
    )
}

@Composable
fun YourLanguageTextField(
    modifier: Modifier = Modifier,
    textValue: String = "",
    onValueChange: (String) -> Unit = {}
) {

    //TODO change UI so that current language selection is visible!!! eg. your first language choice is "english" and the one you want to learn is "polish"

    //var text by remember { mutableStateOf(TextFieldValue("")) }
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
        Text(stringResource(R.string.settings))
    }
}


@Preview
@Composable
fun SettingsBtnPreview() {
    VocantasticTheme(dynamicColor = false) {
        SettingsButton()
    }
}

@Preview
@Composable
fun YourLanguageTextFieldPreview() {
    VocantasticTheme(dynamicColor = false) {
        YourLanguageTextField()
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    VocantasticTheme(dynamicColor = false) {
        HomeScreen(navController )
    }
}