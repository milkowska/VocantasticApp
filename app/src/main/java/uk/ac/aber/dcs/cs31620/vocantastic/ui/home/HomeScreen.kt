package uk.ac.aber.dcs.cs31620.vocantastic.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val textValueNative = rememberSaveable { mutableStateOf("") }
    val textValueForeign = rememberSaveable { mutableStateOf("") }
    val updatedValueNative = rememberSaveable { mutableStateOf("") }
    val updatedValueForeign = rememberSaveable { mutableStateOf("") }
    val maxChar = 20

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        //TODO center that!!!!

        CurrentFirstLanguageText(
            firstLanguage = updatedValueNative.value,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp,0.dp,10.dp,5.dp)

        )

        Spacer(modifier = Modifier.height(6.dp))
        //TODO center that

        CurrentForeignLanguageText(
            secondLanguage = updatedValueForeign.value,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp,0.dp,10.dp,5.dp)

        )
        Spacer(modifier = Modifier.height(6.dp))

        Image(
            modifier = Modifier
                .size(265.dp),
            painter = painterResource(id = R.drawable.transparent_home_screen_image),
            contentDescription = stringResource(id = R.string.welcome_image),
            contentScale = ContentScale.Crop
        )

        YourLanguageTextField(
            modifier = Modifier,
            textValue = textValueNative.value,
            onValueChange = {
                if (it.length <= maxChar) textValueNative.value = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ForeignLanguageTextField(
            textValue = textValueForeign.value,
            onValueChange = {
                if (it.length <= maxChar) textValueForeign.value = it
            }
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row ( horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
            .padding(top = 10.dp),

        ) {
            SaveConfigurationButton(
                modifier = Modifier
                    .weight(0.5f)
                .padding(15.dp),
                onClick = {
                    updatedValueNative.value = textValueNative.value
                    updatedValueForeign.value = textValueForeign.value
                }
            )
//TODO one button has to be disabled the other enabled
            DeleteConfigurationButton(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(15.dp),
                onClick = {
                    updatedValueNative.value = ""
                    updatedValueForeign.value = ""
                }
            )
        }
    }
}

@Composable
fun CurrentFirstLanguageText(
    firstLanguage: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Your current first language selection is: $firstLanguage",
        fontSize = 17.sp,
        modifier = modifier
    )
}

@Composable
fun CurrentForeignLanguageText(
    secondLanguage: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Your current foreign language selection is: $secondLanguage",
        fontSize = 17.sp,
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
fun SaveConfigurationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(48.dp)
    ) {
        Text(stringResource(id = R.string.save_configuration))
    }
}
@Composable
fun DeleteConfigurationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(48.dp)
    ) {
        Text(stringResource(id = R.string.delete_configuration))
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
        HomeScreen(navController)
    }
}